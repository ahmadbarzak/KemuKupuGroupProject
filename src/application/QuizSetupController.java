package application;

/**
 * This class sets up a new quiz (test or practice) - gets words and resets game variables
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class QuizSetupController extends QuizController implements Initializable{
	@FXML private Button beginButton, returnButton, helpButton;


	/**
	 * This function initializes buttons and allows them to have a hover effect
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		styleButtons();
	}
	
	
	/**
	 * This function adds an on-hover effect to the buttons
	 */
	public void styleButtons() {
		HoverEffects.addHoverEffects(beginButton, "LawnGreen", "Black");
		HoverEffects.addHoverEffects(returnButton, "Red", "Black");
		HoverEffects.addHoverEffects(helpButton, "DarkOrange", "Black");
	}


	/**
	 * This function initializes the quiz's progress tracker variables (word number, attempt number, current score) and gets words
	 * @param event - button click on begin quiz
	 */
	public void quizSetUp(ActionEvent event){
		setWordProgress(1);
		setWordAttempt(0);
		setCurrentScore(0);
		getWords("src/words/"+getTopic());
		setMaxNumWords(getMaxWordNum());
		toWordAttempt(event);
	}


	/**
	 * This function creates a list of the words to be tested and stores them in src/script/tempWords
	 * @param topicFilename - name of the filename containing topic's word list
	 */
	public void getWords(String topicFileName){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getWords",topicFileName,getQuizType()};
		ScriptCall getWords = new ScriptCall(command);
		getWords.startProcess();
	}


	/**
	 * This function gets the number of test words
	 * @return maxwords - int of number of test words
	 */
	public int getMaxWordNum() {
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getMaxWords"};
		ScriptCall getMaxWords = new ScriptCall(command);
		int maxWords = Integer.parseInt(getMaxWords.getStdOut());

		return maxWords;
	}

	
	/**
	 * This function gives the user a dialog alert with basic instructions on how to play
	 * @param event - button click on help button
	 */
	public void quickHelp(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.getDialogPane().setMinWidth(650);
		
		alert.setTitle("Quick Test Help");
		alert.setHeaderText("Pre-test Help");
		alert.setContentText(
				"To complete a Kēmu Kupu test, press begin quiz to hear the first word.\n"
				+ "Enter the spelling of the played word in the text box, or press on the speaker to replay the word.\n"
				+ "You can adjust the speed of the speech by moving the slider and pressing on the speaker.\n"
				+ "Once you have entered your spelling, press enter or submit to check.\n"
				+ "If correct, you'll move on to the next word, else try again with a second letter hint!"
				+ "Repeat this for the 5 words.\n\n"
				+ "Scoring: 20 points for correct + full time bonus (if correct on first go) or 1/2 x time bonus (correct on second go)\n"
				+ "Your total score will be the score of all 5 words cumulated\n\n"
				+ "Good luck!");

		alert.showAndWait();
	}

}
