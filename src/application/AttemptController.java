package application;

/**
 * This class is the controller class for the quiz attempt screen
 * Allows user to play word, adjust speed of synthesis, and enter spelling attempt
 * Calculates score bonus using timer
 * Uses wordProgress, wordAttempt, currentScore from parent QuizController.java class to keep track of progress
 * Controls WordAttempt.fxml
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class AttemptController extends QuizController implements Initializable{
	@FXML private Label wordProgress, attemptNum, timer, score, dashedWord; 
	@FXML TextField wordAttempt;
	@FXML Slider playbackSpeed;
	@FXML Button submitButton;
	
	private int SCORE_BONUS = 20;
	private double speed;
	
	
	/**
	 * This function initializes the progress labels when reloaded and updates playback speed when slider is changed
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		speed=1;
		setWordAttempt((getWordAttempt()+1));
		
		setProgressLabels();		
		score.setText("current score: "+Integer.toString(getCurrentScore()));
		showDashed(getDashed());
		runTimer();
		
		playbackSpeed.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				speed = 2.25-(playbackSpeed.getValue())/50;
			}
		});
		
	}
	
	
	/**
	 * This function sets the word attempt and progress labels in the scene
	 */
	public void setProgressLabels() {
		attemptNum.setText("attempt "+Integer.toString(getWordAttempt())+" of 2");
		wordProgress.setText("play word "+Integer.toString(getWordProgress())+" of "+Integer.toString(getMaxNumWords()));
	}
	
	
	/**
	 * This function begins the timer task and updates the timer label on the scene
	 */
	public void runTimer() {
		TimerBackgroundTask timerTask = new TimerBackgroundTask();
		timerTask.messageProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				String time = timerTask.getMessage();
				timer.setText("Time: "+ time);
			}
			
		});
		
		timerTask.setOnSucceeded(event
	            -> timer.setText(timerTask.getMessage()));
		
		Thread timerThread = new Thread(timerTask);
		timerThread.start();
	}
	
	
	/**
	 * This function converts the current test word to dashes
	 * @return dashedWord - string of dashes
	 */
	public String getDashed(){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getTestWord", Integer.toString(getWordProgress())};
		String testWord = getScriptStdOut(command);
		String dashedWord = testWord.replaceAll("[a-zA-Zāēīōū]", "-"); // replace each letter with an "_"
		return dashedWord;
	}
	
	
	/**
	 * This function shows the dashed word (with 2nd letter if second attempt) on label
	 */
	public void showDashed(String dashedCurrentWord) {
		if(getWordAttempt()==2) {
			StringBuilder dashedSecondLetterHint = new StringBuilder(dashedCurrentWord);
			dashedSecondLetterHint.replace(1, 2, hintGetter());
			dashedWord.setText(dashedSecondLetterHint.toString());
		} else {
			dashedWord.setText(dashedCurrentWord);
		}
	}
	
	
	/**
	 * This function gets the second letter of the word
	 * @return character - String containing second letter
	 */
	public String hintGetter(){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "hint", Integer.toString(getWordProgress())};
		String character = getScriptStdOut(command);
		
		return character;
	}
	
	
	/**
	 * This function plays the given quiz word at selected speed
	 * @param event - button click on speaker
	 */
	public void playWord(ActionEvent event){
		PlayWordBackgroundTask playWordTask = new PlayWordBackgroundTask(speed);
		Thread playThread = new Thread(playWordTask);
		playThread.start();
	}
	
	
	/**
	 * This function does don't know (skip) functionality when button is clicked
	 * @param event - button click
	 */
	public void dontKnow(ActionEvent event) throws IOException{	
		String[] command = new String[] {"src/script/quizFunctionality.sh", "writeSkipped",Integer.toString(getWordProgress()),Integer.toString(getWordAttempt())};
		callScriptCase(command);
		toSecondIncorrect(event);	
	}
	
	
	/**
	 * This function allows user to enter a macronned letter by button press
	 * @param event - button click
	 */
	public void insertMacron(ActionEvent event){
		String attempt = wordAttempt.getText();
		Button macronValue = (Button)event.getSource();
		String macronCharacter = macronValue.getText();
		wordAttempt.setText(attempt+macronCharacter+"");
	}	
	
	
	/**
	 * This function submits the spelling from the text field
	 * @param event - button click
	 */
	public void submitWord(ActionEvent event) throws IOException{
		String attempt = wordAttempt.getText();
		
		String[] command = new String[] {"src/script/quizFunctionality.sh", "wordCheck", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), attempt};
		String correctStatus=getScriptStdOut(command);		
		
		toOutcome(event,correctStatus);
	}	
	
	
	/**
	 *  This function performs submit functionality when enter key is pressed
	 * @param event - enter key press
	 * **/
	public void submitOnEnter(KeyEvent key) {
		if(key.getCode().toString().equals("ENTER")){
		        submitButton.fire();
		}
	}
	
	
	/**
	 * This function determines the score bonus and then switches to appropriate screen
	 * @param event - button click
	 */
	public void toOutcome(ActionEvent event, String correctStatus) throws IOException {
		if(getQuizType().equals("test")) {
			updateScore(correctStatus, getTimeBonus());
		} 

		if(correctStatus.equals("1")) {
			toCorrect(event); // Correct on first attempt
		} else if(correctStatus.equals("2")) {
			toFirstIncorrect(event); // Incorrect first attempt	
		} else if (correctStatus.equals("3")) {
			toCorrect(event); // Correct on second attempt
		} else if(correctStatus.equals("4")) {
			toSecondIncorrect(event);
		}
	}
	
	
	/**
	 * This function gets the last time shown on screen to calculate time bonus
	 * @returns timeScoreFactor - time bonus
	 */
	public int getTimeBonus() {
		int timeScoreFactor=1;
		
		String[] timerStringSplitted = timer.getText().split(" ");
		try {
			timeScoreFactor = Integer.parseInt(timerStringSplitted[1]);
		} catch(Exception NumberFormatException) {
			timeScoreFactor = 1;
		}
		
		return timeScoreFactor;
	}
	
	
	/**
	 * This function updates the users score with correct bonus and time bonus
	 * @param correctStatus - string "1" or "3" (correct 1st go, correct 2nd go respectively)
	 * @param timeBonus - int of extra time points
	 */
	public void updateScore(String correctStatus, int timeBonus) {
		if(correctStatus.equals("1")) {
			setCurrentScore((getCurrentScore()+SCORE_BONUS+(timeBonus)));
		} else if (correctStatus.equals("3")) {
			setCurrentScore((getCurrentScore()+(SCORE_BONUS/2)+(timeBonus)));
		}
	}
}
