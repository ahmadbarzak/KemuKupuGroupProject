package application;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller class for the help screen
 * Allows user to view instructions of the applications
 * Controls Help.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class HelpController implements Initializable{
/**
 * This function sets up the help text for the user to get instructions
 * Controls Help.fxml	
 */
	
	@FXML private TextArea helpMessage;
	@FXML private Button backButton, menuButton;
	
	
	/**
	 * This function initializes buttons and allows them to have a hover effect and sets a help message
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setHelpMessage();
		styleButtons();
	}
	
	
	/**
	 * This function adds an on hover effect to the buttons
	 */
	public void styleButtons() {
		HoverEffects.addHoverEffects(menuButton, "LawnGreen", "Black");
		HoverEffects.addHoverEffects(backButton, "Red", "Black");
	}
	
	
	
	/**
	 * This function sets a help message in the text area box
	 */
	public void setHelpMessage() {
		String help ="Welcome to Kēmu Kupu!\n\n\n"
				+ "Test Module:\n"
				+ "-test yourself on 5 words\n\n"
				+ "-when you begin the quiz, a word will automatically play\n\n"
				+ "-you can adjust the speed using the slider and replay the word as many times as you want by pressing the speaker\n\n"
				+ "-enter the word into the text box, and press submit (or hit enter) to check your spelling\n\n"
				+ "-if correct, you will gain 20 points + the full time bonus\n\n"
				+ "-if incorrect, you will get to have a second attempt at the word (and will recieve 2nd letter hint)\n\n"
				+ "-if correct on your second attempt, you will gain 20 points + 1/2 the time bonus\n\n"
				+ "-if you do not enter a word within th 20 seconds, you will get no time bonus (but will still get the correct bonus if you're right!\n\n"
				+ "-your final score will be the sum of your scores of each word\n\n"
				+ "-at the end of the test, you can save your score to a leaderboard and compete with your friends!\n\n\n"
				+ "Practice Module:\n"
				+ "-you will get the opportunity to practice all the words in the topic\n\n"
				+ "-when you begin the quiz, a word will automatically play\n\n"
				+ "-you can adjust the speed using the slider and replay the word as many times as you want by pressing the speaker\n\n"
				+ "-enter the word into the text box, and press submit (or hit enter) to check your spelling\n\n"
				+ "-you can exit the practice module whenever you want, or practice all the words until completion\n\n\n"
				+ "Leaderboard:\n"
				+ "-you can view the top 15 saved scores on the leaderboard\n";
		helpMessage.setText(help);
	}
	
	
	/**
	 * This function allows the user to return to the main menu
	 * @param event - button click on back to menu
	 */
	public void toOpeningMenu(ActionEvent event){
		SwitchScene switchToMenu = new SwitchScene("/scenes/Opening.fxml",event);
		switchToMenu.setWindowTitle("Kēmu Kupu: Menu");
		switchToMenu.switchTo();
	}

}
