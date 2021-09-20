package application;

import java.io.BufferedReader;

/**
 * This class is the controller class for the attempt functionality
 * Allows user to play word, control speed, and submit spelling (or skip word)
 * Controls WordAttempt.fxml
 */

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is the controller class for the quiz word attempt functionality -plays words and checks the spelling
 * Uses variables and methods from parent QuizController
 * Controls WordAttempt.fxml
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class AttemptController extends QuizController implements Initializable{
	@FXML private Label wordNum, wordTotal, attemptNum; 
	@FXML Slider playbackSpeed;
	@FXML TextField wordAttempt;
	
	
	/**
	 * This function sets the word attempt and progress labels in the scene each time it is loaded
	 * Will then play word to be tested by user
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setWordAttempt((getWordAttempt()+1));
		
		// Setting fxml labels
		wordNum.setText(Integer.toString(getWordProgress()));
		wordTotal.setText(Integer.toString(getMaxNumWords()));
		attemptNum.setText(Integer.toString(getWordAttempt()));
	}
	
	/**
	 * This function plays the word
	 * * IMPLEMENT *
	 * @param event - button click on speaker
	 */
	public void playWord(ActionEvent event) throws IOException{
		try {
			String[] command = new String[] {"src/script/quizFunctionality.sh", "play", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt())};
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function does don't know functionality when button is clicked
	 * * IMPLEMENT *
	 * @param event - button click
	 */
	public void dontKnow(ActionEvent event) throws IOException{
		System.out.println("Dont Know");
	}
	
	/**
	 * This function submits the spelling and then switches to appropriate outcome screen
	 * * IMPLEMENT *
	 * @param event - button click
	 */
	public void submitWord(ActionEvent event) throws IOException{
		String attempt = wordAttempt.getText();
		
		try {
			String[] command = new String[] {"src/script/quizFunctionality.sh", "wordCheck", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), attempt};
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			
			// Obtaining users spelling result
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));			
			String correctStatus=stdout.readLine();
			
			if(correctStatus.equals("1") || correctStatus.equals("3") ) {
				setCurrentScore((getCurrentScore()+1));
				toCorrect(event); // Correct on first or second attempt
			} else if(correctStatus.equals("2")) {
				toFirstIncorrect(event); // Incorrect first attempt	
			} else if(correctStatus.equals("4")) {
				toSecondIncorrect(event); // Incorrect second attempt
			}					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
