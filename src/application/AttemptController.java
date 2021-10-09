package application;

import java.beans.EventHandler;

/**
 * This class is the controller class for the quiz attempt screen
 * Allows user to play word, adjust speed of synthesis, and enter spelling attempt
 * Uses wordProgress, wordAttempt, currentScore from parent QuizController.java class
 * Controls WordAttempt.fxml
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import javafx.event.*;


public class AttemptController extends QuizController implements Initializable{
	@FXML private Label wordProgress, attemptNum, timer, score, dashedWord; 
	@FXML TextField wordAttempt;
	@FXML Slider playbackSpeed;
	@FXML Button submitButton;
	static double speed;
	int Seconds = 10;
	int ScoreBonus = 20;
	/**
	 * This function sets the word attempt and progress labels in the scene each time it is loaded
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		speed=1;
	
		setWordAttempt((getWordAttempt()+1));
		attemptNum.setText("attempt "+Integer.toString(getWordAttempt())+" of 2");
		wordProgress.setText("word "+Integer.toString(getWordProgress())+" of "+Integer.toString(getMaxNumWords()));
		score.setText("current score: "+Double.toString(getCurrentScore())); // TO UPDATE!
		
		
		//
		timer.setText("timer"); // TO DO!
		BackgroundTask bGTask = new BackgroundTask();
		bGTask.messageProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				String text = bGTask.getMessage();
				timer.setText("Time: "+ text);
			}
			
		});
		
		//Is executed when the countdown time is over
		bGTask.setOnSucceeded(event
	            -> timer.setText(bGTask.getMessage()));
		
		Thread thrd = new Thread(bGTask);
		thrd.start();
		
		// Showing number of letters in word (and second letter if second attempt)
		String dashedCurrentWord = getDashed();
		if(getWordAttempt()==2) {
			StringBuilder dashedSecondLetterHint = new StringBuilder(dashedCurrentWord);
			dashedSecondLetterHint.replace(1, 2, hintGetter());
			dashedWord.setText(dashedSecondLetterHint.toString());
		} else {
			dashedWord.setText(dashedCurrentWord);
		}
		
		// Gets the value of the play back speed slider
		playbackSpeed.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				speed = 2.25-(playbackSpeed.getValue())/50;
			}
		});
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
	 * This function gets the second letter of the word
	 * @return character - String containing second letter
	 */
	public String hintGetter(){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "hint", Integer.toString(getWordProgress())};
		String character = getScriptStdOut(command);
		
		return character;
	}
	
	/**
	 * This function plays the given quiz word
	 * @param event - button click on speaker
	 */
	public void playWord(ActionEvent event) throws IOException{
		BackgroundTaskTwo bGTaskTwo = new BackgroundTaskTwo(speed);
		Thread thrdTwo = new Thread(bGTaskTwo);
		thrdTwo.start();
	}
	
	/**
	 * This function does don't know functionality when button is clicked
	 * @param event - button click
	 */
	public void dontKnow(ActionEvent event) throws IOException{
		toSecondIncorrect(event);	
	}
	
	/**
	 * This function submits the spelling and then switches to appropriate outcome screen
	 * @param event - button click
	 */
	public void submitWord(ActionEvent event) throws IOException{
		String attempt = wordAttempt.getText();
		attempt = attempt.replaceAll(" ", "_").toLowerCase(); // To allow for submissions with spaces
		
		String[] command = new String[] {"src/script/quizFunctionality.sh", "wordCheck", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), attempt};
		String correctStatus=getScriptStdOut(command);		
		
		determineOutcomeScreen(event,correctStatus);
	}	
	
	
	// Change scoring!!
	public void determineOutcomeScreen(ActionEvent event, String correctStatus) throws IOException {
		String[] timerStringSplitted = timer.getText().split(" ");
		int timeScoreFactor;
			try {
					timeScoreFactor = Integer.parseInt(timerStringSplitted[1]);
				}
				catch(Exception NumberFormatException) {
					timeScoreFactor = 1;
				}

		if(correctStatus.equals("1")) {
			setCurrentScore((getCurrentScore()+ScoreBonus+(timeScoreFactor)));
			toCorrect(event); // Correct on first attempt
		} else if(correctStatus.equals("2")) {
			toFirstIncorrect(event); // Incorrect first attempt	
		} else if (correctStatus.equals("3")) {
			setCurrentScore((getCurrentScore()+(0.5*ScoreBonus)+(timeScoreFactor)));
			toCorrect(event); // Correct on second attempt
		} else if(correctStatus.equals("4")) {
			toSecondIncorrect(event);
		}
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
}
