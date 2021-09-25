package application;

/**
 * This class is the controller class for the quiz attempt screen
 * Allows user to play word, adjust speed of synthesis, and enter spelling attempt
 * Uses wordProgress, wordAttempt, currentScore from parent QuizController.java class
 * Controls WordAttempt.fxml
 */

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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class AttemptController extends QuizController implements Initializable{
	@FXML private Label wordNum, wordTotal, attemptNum; 
	@FXML TextField wordAttempt;
	@FXML Slider playbackSpeed;
	double speed;
	
	/**
	 * This function sets the word attempt and progress labels in the scene each time it is loaded
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setWordAttempt((getWordAttempt()+1));
		
		// Setting fxml labels
		wordNum.setText(Integer.toString(getWordProgress()));
		wordTotal.setText(Integer.toString(getMaxNumWords()));
		attemptNum.setText(Integer.toString(getWordAttempt()));
		playbackSpeed.valueProperty().addListener(new ChangeListener<Number>() {
			
			//Gets the value of the playback speed slider
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				speed = (playbackSpeed.getValue())/50;
				System.out.println(speed);
			}
		});
	}
	
	/**
	 * This function plays the given quiz word
	 * Will play once first time, and twice second time
	 * @param event - button click on speaker
	 */
	public void playWord(ActionEvent event) throws IOException{
		try {
			// Calling play case in script file to execute festival to play word
			String[] command = new String[] {"src/script/quizFunctionality.sh", "play", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), Double.toString(speed)};
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
	 * @param event - button click
	 */
	public void dontKnow(ActionEvent event) throws IOException{
		//toSecondIncorrect(event);	
		try {
			// Calling play case in script file to execute festival to play word
			String[] command = new String[] {"src/script/quizFunctionality.sh", "secondLetter", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt())};
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			process.waitFor();
			// Obtaining users spelling result
			// 1 = correct on first go, 2 = incorrect on first go, 3 = correct on second go, 4 = incorrect on second go
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));			
			String secondLetter=stdout.readLine();
			System.out.println(secondLetter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This function submits the spelling and then switches to appropriate outcome screen
	 * @param event - button click
	 */
	public void submitWord(ActionEvent event) throws IOException{
		String attempt = wordAttempt.getText();
		
		try {
			// Calling wordCheck case in script file to check if entered word = actual word
			String[] command = new String[] {"src/script/quizFunctionality.sh", "wordCheck", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), attempt};
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			
			// Obtaining users spelling result
			// 1 = correct on first go, 2 = incorrect on first go, 3 = correct on second go, 4 = incorrect on second go
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));			
			String correctStatus=stdout.readLine();
			
			// Switching to appropriate outcome screen depending
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
