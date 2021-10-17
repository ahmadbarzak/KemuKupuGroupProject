package application;

/**
 * This class is the controller class for the Opening Menu GUI
 * Allows user to navigate to test, practice, help, leaderboard, or to quit
 * Controls Opening.fxml
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class OpeningMenuController implements Initializable {
	@FXML private Button test, practice, leaderBoard, help, quit;


	public void initialize(URL arg0, ResourceBundle arg1) {
		AttemptController.addHoverEffects(test, "DarkOrange", "Black");
		AttemptController.addHoverEffects(practice, "DarkOrange", "Black");
		AttemptController.addHoverEffects(leaderBoard, "DarkOrange", "Black");
		AttemptController.addHoverEffects(help, "LawnGreen", "Black");
		AttemptController.addHoverEffects(quit, "Red", "Black");
	}


	/**
	 * This function gets the quiz type from the respective button click, and then switches to topic selection scene
	 * @param event - button click on quiz button
	 */
	public void getQuizType(ActionEvent event){
		Button topicButton = (Button) event.getSource();
		String quizType = topicButton.getId();
		QuizController.setQuizType(quizType);

		toTopics(event);
	}


	/**
	 * This function switches to the topic selection menu
	 * @param event - button click on quiz button
	 */
	public void toTopics(ActionEvent event){
		SwitchScene switchToTopics = new SwitchScene("/scenes/TopicSelection.fxml",event);
		switchToTopics.setWindowTitle("Kēmu Kupu: Topic Selection");
		switchToTopics.switchTo();
	}


	/**
	 * This function switches to the leaderboard
	 * @param event - button click on leaderboard button
	 */
	public void toLeaderboard(ActionEvent event){
		SwitchScene switchToLeaderboard = new SwitchScene("/scenes/Leaderboard.fxml",event);
		switchToLeaderboard.setWindowTitle("Kēmu Kupu: Leaderboard");
		switchToLeaderboard.switchTo();
	}


	/**
	 * This function switches to the opening menu
	 * @param event - button click on opening menu button
	 */
	public void toOpeningMenu(ActionEvent event){
		SwitchScene switchToLeaderboard = new SwitchScene("/scenes/OpeningMenu.fxml",event);
		switchToLeaderboard.setWindowTitle("Kēmu Kupu: Menu");
		switchToLeaderboard.switchTo();
	}


	/**
	 * This function switches to a help screen
	 * @param event - button click on help menu
	 */
	public void help(ActionEvent event){
		SwitchScene switchToHelp = new SwitchScene("/scenes/Help.fxml",event);
		switchToHelp.setWindowTitle("Kēmu Kupu: Help");
		switchToHelp.switchTo();
	}


	/**
	 * This function quits the application on confirmation
	 * @param event - button click on quit button
	 */
	public void toQuit(ActionEvent event){
		Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit Kēmu Kupu?");
		alert.setHeaderText("Are you sure you want to quit Kēmu Kupu?");

		if(alert.showAndWait().get()== ButtonType.OK) {
			try {
				Stage stage = (Stage) quit.getScene().getWindow();
			    stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
