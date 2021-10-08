package application;

/**
 * This class is the controller class for the quiz set up and outcome screen functionality
 * Mainly sets up game variables (progress, score..) and scene switching functions.
 * Is parent class to AttemptController.java and RewardController.java
 * Controls BeginQuiz.fxml, Correct.fxml, FirstIncorrect.fxml, SecondIncorrect.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	private static String topicFile;
	private static String quizType;
	
	@FXML private Label correctSpelling;
	
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
	
	public void tryAgain(ActionEvent event) throws IOException {
		toWordAttempt(event);
	}
	
	public void pronounciation(ActionEvent event) throws IOException, InterruptedException {
		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), Integer.toString(1)};
		callScriptCase(command);
	}
	
	public void displayCorrectSpelling(String word) {
		correctSpelling.setText(word);
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
		
		if(getQuizType().equals("practice")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/PracticeSecondIncorrect.fxml"));	
			root = loader.load();	
			
			String[] command = new String[] {"src/script/quizFunctionality.sh", "getTestWord", Integer.toString(getWordProgress())};
			String testWord = getScriptStdOut(command);
			
			QuizController secondIncorrectController = loader.getController();
			secondIncorrectController.displayCorrectSpelling(testWord);
		} else if(getQuizType().equals("test")) {
			root= FXMLLoader.load(getClass().getResource("/scenes/SecondIncorrect.fxml"));
		}
		

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toWordAttempt(ActionEvent event) throws IOException{
		if(getQuizType().equals("practice")) {
			root= FXMLLoader.load(getClass().getResource("/scenes/PracticeWordAttempt.fxml"));
		} else if(getQuizType().equals("test")) {
			root= FXMLLoader.load(getClass().getResource("/scenes/WordAttempt.fxml"));
		}
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toReward(ActionEvent event) throws IOException{		
		if(getQuizType().equals("practice")) {
			root= FXMLLoader.load(getClass().getResource("/scenes/PracticeRewardScreen.fxml"));
		} else if(getQuizType().equals("test")) {
			root= FXMLLoader.load(getClass().getResource("/scenes/RewardScreen.fxml"));
		}
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toOpeningMenu(ActionEvent event) throws IOException{		
		root= FXMLLoader.load(getClass().getResource("/scenes/Opening.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu");
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
	
	public static String getTopicFile() {
		return topicFile;
	}
	
	public static String getQuizType() {
		return quizType;
	}

	public static void setWordAttempt(int wordAttempt) {
		QuizController.wordAttempt = wordAttempt;
	}
	
	public static void setWordProgress(int wordProgress) {
		QuizController.wordProgress = wordProgress;
	}
	
	public static void setMaxNumWords(int maxNumWords) {
		QuizController.maxNumWords = maxNumWords;
	}
	
	public static void setCurrentScore(int currentScore) {
		QuizController.currentScore = currentScore;
	}
	
	public static void setTopic(String topicFile) {
		QuizController.topicFile = topicFile;
	}
	
	public static void setQuizType(String quizType) {
		QuizController.quizType = quizType;
	}
	
	
	// Script case call methods
	/**
	 * This function allows the user to call cases from the BASH script
	 * @param command - string[] containing command, case, and parameters
	 */
	public void callScriptCase(String[] command) {
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function allows the user to retrieve the stdout from calling a case from the BASH script
	 * @param command - string[] containing command, case, and parameters
	 */
	public String getScriptStdOut(String[] command) {
		String scriptStdOut="";
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));			
			scriptStdOut=stdout.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return scriptStdOut;
	}
	
}	