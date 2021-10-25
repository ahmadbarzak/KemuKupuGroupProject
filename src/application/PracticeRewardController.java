package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class PracticeRewardController extends QuizController implements Initializable{
/**
 * This function sets up the reward screen for the practice reward screen
 * Controls PracticeRewardScreen.fxml	
 */
	
	@FXML private Button mainMenu, playAgain, gamesModule;
	
	
	/**
	 * This function allows buttons to have a hover effect
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stylePracticeRewardController();
	}
	
	
	/**
	 * This function adds an on hover effect to the buttons
	 */
	public void stylePracticeRewardController() {
		HoverEffects.addHoverEffects(mainMenu, "Red", "Black");
		HoverEffects.addHoverEffects(playAgain, "LawnGreen", "Black");
		HoverEffects.addHoverEffects(gamesModule, "Red", "Black");
	}

}
