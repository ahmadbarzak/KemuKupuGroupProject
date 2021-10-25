package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;
import application.SwitchScene;
import application.ScriptCall;

public class QuizController{
/**
 * This class is contains game data variables, screen switching functions, and script call methods
 * Functions can be used in child classes
 * Is parent class to AttemptController.java, PracticeAttemptController.java, OutcomeController.java, RewardController.java
 */

	private Stage stage;
	private Scene scene;
	private Parent root;

	private static int maxNumWords, wordProgress, wordAttempt, currentScore;
	private static String topic, quizType;


	/**
	 * This function allows the user to exit their current test on confirmation
	 * @param event - button click on exit
	 */
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
	public void toCorrect(ActionEvent event){
		SwitchScene switchToCorrect = new SwitchScene("/scenes/Correct.fxml",event);
		switchToCorrect.switchTo();
	}

	public void toFirstIncorrect(ActionEvent event){
		SwitchScene switchToFirstIncorrect = new SwitchScene("/scenes/FirstIncorrect.fxml",event);
		switchToFirstIncorrect.switchTo();
	}

	public void toSecondIncorrect(ActionEvent event) throws IOException{

		if(getQuizType().equals("practice")) {
			// Second Incorrect screen for practice includes correct spelling of word
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

	
	/** This function retrieves the current test word to display on incorrect screen in prctice module
	 * @param loader - scene to show test word on
	 * @return root - root node to load
	 * **/
	public Parent showCorrectSpelling(FXMLLoader loader) throws IOException{
		root = loader.load();

		String[] command = new String[] {"src/script/quizFunctionality.sh", "getTestWord", Integer.toString(getWordProgress())};
		ScriptCall getTestWord = new ScriptCall(command);
		String testWord = getTestWord.getStdOut();
		
		OutcomeController secondIncorrectController = loader.getController();
		secondIncorrectController.displayCorrectSpelling(testWord);

		return root;

	}

	public void toWordAttempt(ActionEvent event){
		SwitchScene switchToWordAttempt = null;
		
		// Different attempt screens for test and practice
		if(getQuizType().equals("practice")) {
			switchToWordAttempt = new SwitchScene("/scenes/PracticeWordAttempt.fxml",event);
		} else if(getQuizType().equals("test")) {
			switchToWordAttempt = new SwitchScene("/scenes/WordAttempt.fxml",event);
		}

		switchToWordAttempt.switchTo();
	}

	public void toReward(ActionEvent event){
		SwitchScene switchToReward = null;

		// Different reward screens for practice and test
		if(getQuizType().equals("practice")) {
			switchToReward = new SwitchScene("/scenes/PracticeRewardScreen.fxml",event);
		} else if(getQuizType().equals("test")) {
			switchToReward = new SwitchScene("/scenes/RewardScreen.fxml",event);
		}

		switchToReward.setWindowTitle("Kēmu Kupu: Quiz Complete");
		switchToReward.switchTo();
	}

	public void toOpeningMenu(ActionEvent event){
		SwitchScene switchToMenu = new SwitchScene("/scenes/Opening.fxml",event);
		switchToMenu.setWindowTitle("Kēmu Kupu: Menu");
		switchToMenu.switchTo();
	}

	public void toGameModules(ActionEvent event){
		SwitchScene switchToTopic = new SwitchScene("/scenes/TopicSelection.fxml",event);
		switchToTopic.setWindowTitle("Kēmu Kupu: Topic Selection");
		switchToTopic.switchTo();
	}

	public void toPlayAgain(ActionEvent event){
		SwitchScene switchToBeginAgain = new SwitchScene("/scenes/BeginQuiz.fxml",event);
		switchToBeginAgain.setWindowTitle("Kēmu Kupu: New Quiz");
		switchToBeginAgain.switchTo();
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

	public static int getCurrentScore() {
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

	public static void setCurrentScore(int currentScore) {
		QuizController.currentScore = currentScore;
	}

	public static void setTopic(String topic) {
		QuizController.topic = topic;
	}

	public static void setQuizType(String quizType) {
		QuizController.quizType = quizType;
	}

}
