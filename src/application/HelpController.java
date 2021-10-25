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
	@FXML private Button backButton;
	
	
	/**
	 * This function initializes buttons and allows them to have a hover effect and sets a help message
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setHelpMessage();
		styleHelpController();
	}
	
	
	/**
	 * This function adds an on hover effect to the buttons
	 */
	public void styleHelpController() {
		HoverEffects.addHoverEffects(backButton, "Red", "Black");
	}
	
	
	
	/**
	 * This function sets a help message in the text area box
	 */
	public void setHelpMessage() {
		String help ="Welcome to Kēmu Kupu!\n\n\n"
				+ "Test Module:\n"
				+ "-Test yourself on 5 words\n\n"
				+ "-When you begin the quiz, a word will automatically play\n\n"
				+ "-You can adjust the speed using the slider and replay the word as many times as you want by pressing the speaker\n\n"
				+ "-Enter the word into the text box, and press submit (or hit enter) to check your spelling\n\n"
				+ "-If correct, you will gain 20 points  plus the full time bonus\n\n"
				+ "-If incorrect, you will get to have a second attempt at the word (and will recieve 2nd letter hint)\n\n"
				+ "-If correct on your second attempt, you will gain 20 points plus 1/2 the time bonus\n\n"
				+ "-If you do not enter a word within th 20 seconds, you will get no time bonus (but will still get the correct bonus if you're right!)\n\n"
				+ "-Your final score will be the sum of your scores of each word\n\n"
				+ "-At the end of the test, you can save your score to a leaderboard and compete with your friends!\n\n\n"
				+ "Practice Module:\n\n"
				+ "-You will get the opportunity to practice all the words in the topic\n\n"
				+ "-When you begin the quiz, a word will automatically play\n\n"
				+ "-You can adjust the speed using the slider and replay the word as many times as you want by pressing the speaker\n\n"
				+ "-Enter the word into the text box, and press submit (or hit enter) to check your spelling\n\n"
				+ "-You can exit the practice module whenever you want, or practice all the words until completion\n\n\n"
				+ "Leaderboard:\n\n"
				+ "-You can view the top 15 saved scores on the leaderboard\n\n"
				+ "-You can clear these scores by pressing the clear scores button\n\n"
				+ "Good Luck, Kia ora!";
		
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
