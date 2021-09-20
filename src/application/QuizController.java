package application;

/**
 * This class is the controller class for some of the quiz functionality
 * Controls what outcome screen to switch to 
 * Controls BeginQuiz.fxml, Correct.fxml, FirstIncorrect.fxml, SecondIncorrect.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class QuizController {	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private static int maxNumWords; 	// Number of words to be tested
	private static int wordProgress; 	// Current word number
	private static int wordAttempt; 	// Current attempt number
	private static int currentScore; 	// Current attempt number
	
	
	/**
	 * This function sets up progress tracker variables
	 * @param event - button click
	 */
	public void quizSetUp(ActionEvent event) throws IOException{
		maxNumWords = 5; //fix
		wordProgress = 1;
		wordAttempt = 0;
		currentScore = 0;
		
		toWordAttempt(event);
	}
	
	/**
	 * This function switches screen from outcome to next word or reward screen
	 * @param event - button click
	 */
	public void toNext(ActionEvent event) throws IOException{
		wordProgress+=1;
		wordAttempt=0;
		
		if (wordProgress > maxNumWords) {
			toReward(event);
		} else if (wordProgress <=maxNumWords) {
			toWordAttempt(event);
		}
	}
	
	
	// Getters & Setters for use in child class (AttemptController.java)
	public static int getMaxNumWords() {
		return maxNumWords;
	}

	public static int getWordProgress() {
		return wordProgress;
	}

	public static int getWordAttempt() {
		return wordAttempt;
	}
	
	public static int getCurrentScore() {
		return currentScore;
	}

	public static void setWordAttempt(int wordAttempt) {
		QuizController.wordAttempt = wordAttempt;
	}
	
	public static void setCurrentScore(int currentScore) {
		QuizController.currentScore = currentScore;
	}
	
	
	/**
	 * Switch Screen Functions
	 * @param event - button click
	 */
	public void toCorrect(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/Correct.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toFirstIncorrect(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/FirstIncorrect.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toSecondIncorrect(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/SecondIncorrect.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toWordAttempt(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/WordAttempt.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toReward(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/RewardScreen.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}	