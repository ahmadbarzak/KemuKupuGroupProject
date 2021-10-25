package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class AttemptController extends QuizController implements Initializable{
/**
 * This class is the controller class for the quiz attempt screen
 * Allows user to play word, adjust speed of synthesis, and enter spelling attempt
 * Calculates score bonus using timer
 * Uses wordProgress, wordAttempt, currentScore from parent QuizController.java class to keep track of progress
 * Controls WordAttempt.fxml
 */
	
	@FXML private Label wordProgress, attemptNum, timer, score, dashedWord;
	@FXML private TextField wordAttempt;
	@FXML private Slider playbackSpeed;
	@FXML private Button submitButton, wordPlayer, dontKnow, exitButton;
	@FXML private Button ā, ē, ī, ō, ū, Ā, Ē, Ī, Ō, Ū;
	
	private int SCORE_BONUS = 20;
	private double speed;
	
	
	/**
	 * This function initializes the progress labels when scene is reloaded and updates playback speed when slider is changed
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		speed=1;
		setWordAttempt((getWordAttempt()+1));

		// FXML initialization
		styleButtons();
		setProgressLabels();
		score.setText("current score: "+Integer.toString(getCurrentScore()));
		showDashed(getDashed());
		
		wordPlayer.fire(); // Plays word on immediately on entry to scene 
		runTimer();
		
		// Slider for speed of synthesis
		playbackSpeed.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				speed = 2.25-(playbackSpeed.getValue())/50;
			}
		});

	}
	
	
	/**
	 * This function adds an on-hover effect to the buttons
	 */
	public void styleButtons() {
		HoverEffects.addHoverEffects(submitButton, "LawnGreen", "Black");
		HoverEffects.addHoverEffects(dontKnow, "Red", "Black");
		HoverEffects.addHoverEffects(exitButton, "Red", "Black");
		Button[] macrons = {ā, ē, ī, ō, ū, Ā, Ē, Ī, Ō, Ū};
		
		for (int i = 0; i < 10; i ++) {
			HoverEffects.addHoverEffects(macrons[i], "Black", "White");
		}
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
				timer.setText("time bonus: "+ time);
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
		ScriptCall getTestWord = new ScriptCall(command);
		String testWord = getTestWord.getStdOut();
		
		String dashedWord = testWord.replaceAll("[a-zA-Zāēīōū]", "-"); // replace each letter with an "-"
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
	 * This function gets the second letter of the word for the hint on second attempt
	 * @return character - String containing second letter
	 */
	public String hintGetter(){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "hint", Integer.toString(getWordProgress())};
		ScriptCall hint = new ScriptCall(command);
		String character = hint.getStdOut();

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
		// Makes note that word was skipped for final reward screen
		String[] command = new String[] {"src/script/quizFunctionality.sh", "writeSkipped",Integer.toString(getWordProgress()),Integer.toString(getWordAttempt())};
		ScriptCall writeSkipped = new ScriptCall(command);
		writeSkipped.startProcess();
		
		toSecondIncorrect(event);
	}


	/**
	 * This function allows user to enter a macronned letter by button press on on screen button
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
	 * Error checks for empty or blank spelling attempts
	 * @param event - button click
	 */
	public void submitWord(ActionEvent event) throws IOException{
		if(wordAttempt.getText().isEmpty() || wordAttempt.getText().isBlank()){
			// Alerts if user presses enter or clicks submit with no entry
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty attempt");
			alert.setHeaderText("You didn't enter a spelling attempt.\n\nTo skip this word, press skip, otherwise, give it your best shot!");
			alert.setContentText("Press OK to re-enter an attempt");
			alert.showAndWait();
		} else {
			String attempt = wordAttempt.getText();

			String[] command = new String[] {"src/script/quizFunctionality.sh", "wordCheck", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), attempt};
			ScriptCall checkWord = new ScriptCall(command);
			String correctStatus = checkWord.getStdOut();
			
			determineOutcomeScreen(event,correctStatus);
		}
	}


	/**
	 * This function determines the score bonus and then switches to appropriate screen
	 * @param event - button click
	 * @param correctStatus - string of "1"=(correct first attempt) "2"=(incorrect first attempt) 
	 * "3"=(correct second attempt) "4"=(incorrect second attempt)
	 */
	public void determineOutcomeScreen(ActionEvent event, String correctStatus) throws IOException {
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
	 * This function performs submit functionality when enter key is pressed
	 * @param event - enter key press
	 * **/
	public void submitOnEnter(KeyEvent key) {
		if(key.getCode().toString().equals("ENTER")){
			submitButton.fire();
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
			timeScoreFactor = Integer.parseInt(timerStringSplitted[2]);
		} catch(Exception NumberFormatException) {
			timeScoreFactor = 0;
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
