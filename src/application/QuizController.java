package application;

/**
 * This class is the controller class for the quiz set up and outcome screen functionality
 * Mainly sets up game variables (progress, score..) and scene switching functions.
 * Is parent class to AttemptController.java and RewardController.java
 * Controls BeginQuiz.fxml, Correct.fxml, FirstIncorrect.fxml, SecondIncorrect.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuizController {	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private static int maxNumWords; 	// Number of words to be tested
	private static int wordProgress; 	// Current word number
	private static int wordAttempt; 	// Current attempt number
	private static int currentScore; 	// Current score
	
	
	
	/**
	 * This function creates a list of the words to be tested and stores them in src/script/tempWords
	 * @param topicFilename - name of the filename containing topic's word list
	 */
	public void getWords(String topicFileName){
		try {
			// Calling getWords case in script file to create and populate a text file with quiz words
			String[] command = new String[] {"src/script/quizFunctionality.sh", "getWords",topicFileName};
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This function initializes the quiz's progress tracker variables (word number, attempt number, current score)
	 * @param event - button click on begin quiz
	 */
	public void quizSetUp(ActionEvent event) throws IOException{
		wordProgress = 1;
		wordAttempt = 0;
		currentScore = 0;
		
		// Getting number of words being tested, not always 5 as not all files have 5 words
		try {
			String command = "cat src/script/tempWords | wc -l | sed 's/ //g'";
			ProcessBuilder pb = new ProcessBuilder("/bin/bash","-c", command);
			Process process = pb.start();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			process.waitFor();
			maxNumWords = Integer.parseInt(stdout.readLine());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		toWordAttempt(event);
	}
	
	/**
	 * This function switches screen from outcome to next word or reward screen depending on progress
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
	
	
	
	// Functions to switch to other quiz GUI screens
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
	
	
	// Getters & Setters for use in child class (AttemptController.java RewardController.java)
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
	
	public static void setWordProgress(int wordProgress) {
		QuizController.wordProgress = wordProgress;
	}
	
	public static void setCurrentScore(int currentScore) {
		QuizController.currentScore = currentScore;
	}
	
}	