package application;

/**
 * This class is the controller class for the quiz attempt screen
 * Allows user to play word, adjust speed of synthesis, and enter spelling attempt
 * Uses wordProgress, wordAttempt, currentScore from parent QuizController.java class
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
	double speed;
	
	/**
	 * This function sets the word attempt and progress labels in the scene each time it is loaded
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		speed=1;
	
		setWordAttempt((getWordAttempt()+1));
		attemptNum.setText("attempt "+Integer.toString(getWordAttempt())+" of 2");
		wordProgress.setText("word "+Integer.toString(getWordProgress())+" of "+Integer.toString(getMaxNumWords()));
		score.setText("current score: "+Integer.toString(getCurrentScore())); // TO UPDATE!
		
		timer.setText("timer"); // TO DO!
		
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
	
	
	public String getDashed(){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getTestWord", Integer.toString(getWordProgress())};
		String testWord = getScriptStdOut(command);
		String dashedWord = testWord.replaceAll("[a-zA-Zāēīōū]", "-"); // replace each letter with an "_"
		return dashedWord;
	}
	
	/**
	 * This function plays the given quiz word
	 * Will play once first time, and twice second time
	 * @param event - button click on speaker
	 */
	public void playWord(ActionEvent event) throws IOException{
		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), Double.toString(speed)};
		callScriptCase(command);
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
	
	
	// Chnage scoring!!
	public void determineOutcomeScreen(ActionEvent event, String correctStatus) throws IOException {
		if(correctStatus.equals("1") || correctStatus.equals("3") ) {
			setCurrentScore((getCurrentScore()+1));
			toCorrect(event); // Correct on first or second attempt
		} else if(correctStatus.equals("2")) {
			toFirstIncorrect(event); // Incorrect first attempt	
		} else if(correctStatus.equals("4")) {
			toSecondIncorrect(event); // Incorrect second attempt
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
	
	/**
	 * This function gets the second letter of the word
	 * @return character - String containing second letter
	 */
	public String hintGetter(){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "hint", Integer.toString(getWordProgress())};
		String character = getScriptStdOut(command);
		
		return character;
	}
}
