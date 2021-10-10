package application;

/**
 * This class is contains game data variables, screen switching functions, and script call methods
 * Functions can be used in child classes
 * Is parent class to AttemptController.java, PracticeAttemptController.java, OutcomeController.java, RewardController.java
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
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
	private static double currentScore; 	// Current score
	private static String topic;
	private static String quizType;
	
	
	public void exitQuiz(ActionEvent event) {
		Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit Game?");
		alert.setHeaderText("Are you sure you want to quit this game, you will lose all your progress?");
		
		if(alert.showAndWait().get()== ButtonType.OK) {
			try {
				toOpeningMenu(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		if(getQuizType().equals("practice")) {
			// Shows the correct spelling
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/PracticeSecondIncorrect.fxml"));	
			root = showCorrectSpelling(loader);
		} else if(getQuizType().equals("test")) {
			root= FXMLLoader.load(getClass().getResource("/scenes/SecondIncorrect.fxml"));
		}
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/** This function retrieves the current test word to display on incorrect screen 
	 * @param loader - scene to show test word on
	 * @return root - root node to load
	 * **/ 
	public Parent showCorrectSpelling(FXMLLoader loader) throws IOException {
		root = loader.load();	
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getTestWord", Integer.toString(getWordProgress())};
		String testWord = getScriptStdOut(command);
		OutcomeController secondIncorrectController = loader.getController();
		secondIncorrectController.displayCorrectSpelling(testWord);
		
		return root;
		
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
		stage.setTitle("Kēmu Kupu: Quiz Complete");
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
	
	public void toGameModules(ActionEvent event) throws IOException{		
		root= FXMLLoader.load(getClass().getResource("/scenes/TopicSelection.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: Topic Selection");
		stage.setScene(scene);
		stage.show();
	}
	
	public void toPlayAgain(ActionEvent event) throws IOException{			
		root= FXMLLoader.load(getClass().getResource("/scenes/BeginQuiz.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: New Quiz");
		stage.setScene(scene);
		stage.show();
	}
	
	
	// Getters & Setters
	public static int getMaxNumWords() {
		return maxNumWords;
	}

	public static int getWordProgress() {
		return wordProgress;
	}

	public static int getWordAttempt() {
		return wordAttempt;
	}
	
	public static double getCurrentScore() {
		return currentScore;
	}
	
	public static String getTopic() {
		return topic;
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
	
	public static void setCurrentScore(double currentScore) {
		QuizController.currentScore = currentScore;
	}
	
	public static void setTopic(String topic) {
		QuizController.topic = topic;
	}
	
	public static void setQuizType(String quizType) {
		QuizController.quizType = quizType;
	}
	
	
	// Script case call methods
	/**
	 * This function allows the user to call cases from the BASH script
	 * @param command - string[] containing command, case, and parameters
	 * @return 
	 */

	public static void callScriptCase(String[] command) {
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