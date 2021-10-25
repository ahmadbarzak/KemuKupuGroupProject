package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class QuizSetupController extends QuizController implements Initializable{
/**
 * This class sets up a new quiz (test or practice) - gets words and resets game variables
 * Controls BeginQuiz.fxml
 */
	
	@FXML private Button beginButton, returnButton, helpButton;


	/**
	 * This function sets up JavaFX to allow a hover effect
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		styleQuizSetupController();
	}
	
	
	/**
	 * This function adds an on-hover effect to the buttons, giving them a different colour when cursor is on them
	 */
	public void styleQuizSetupController() {
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
		String helpMessage = "";
		alert.setTitle("Quick Test Help");
		alert.setHeaderText("Pre-test Help");
		
		//Check if the mode is practice mode, and set the help message for it
		if(getQuizType().equals("practice")) {
			helpMessage = "To complete a Kēmu Kupu practice test, press begin quiz to hear the first word.\n"
					+ "Enter the spelling of the played word in the text box, or press on the speaker to replay the word.\n"
					+ "You can adjust the speed of the speech by moving the slider and pressing on the speaker.\n"
					+ "Once you have entered your spelling, press enter or submit to check.\n"
					+ "If correct, you'll move on to the next word, otherwise you can try again with a second letter hint!\n"
					+ "If you get both attempts wrong, you will be able to see the actual spelling of the word, so don't\n"
					+ "Worry if you don't get it right in the two attempts.\n"
					+ "Repeat this for all of the words in the topic, to help you practice for the test.\n\n"
					+ "There is no scoring system here, and you can exit at any time by pressing\n"
					+ "the exit button in the top left\n\n"
					+ "Good luck, Kia Ora!";
			alert.getDialogPane().setMinHeight(350);
			alert.setTitle("Quick Practice Test Help");
			alert.setHeaderText("Practice test Help");
		}
		
		//check if the mode is test mode, and set the help message for it
		else if(getQuizType().equals("test")) {
			helpMessage = "To complete a Kēmu Kupu test, press begin quiz to hear the first word.\n"
					+ "Enter the spelling of the played word in the text box, or press on the speaker to replay the word.\n"
					+ "You can adjust the speed of the speech by moving the slider and pressing on the speaker.\n"
					+ "Once you have entered your spelling, press enter or submit to check.\n"
					+ "If correct, you'll move on to the next word, otherwise you can try again with a second letter hint!\n"
					+ "Repeat this for the 5 words.\n\n"
					+ "Scoring:\n"
					+ "If correct on first go, 20 points for correct spelling plus a time bonus.\n"
					+ "If correct on second go, 10 points for correct spelling plus a time bonus.\n"
					+ "Do not worry if you take longer than 20 seconds to spell a word,\n"
					+ "you can still get the 10 or 20 points if you get the word right\n\n"
					+ "Your total score will be the score of all 5 words cumulated\n\n"
					+ "Good luck, Kia Ora!";
			alert.getDialogPane().setMinHeight(450);
			alert.setTitle("Quick Test Help");
			alert.setHeaderText("Pre-test Help");
		}
		
		//display the help message
		alert.setContentText(helpMessage);
		alert.showAndWait();
	}

}
