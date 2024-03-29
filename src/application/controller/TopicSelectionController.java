package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.HoverEffects;
import application.SwitchScene;

public class TopicSelectionController implements Initializable {	
/**
 * This class is the controller class for the topic selection page
 * Allows user to select a topic and then generates the quiz words from that topic's word list
 * Controls TopicSelection.fxml
 */
	
	@FXML private Button colours, days2, months1, weather, feelings, software;
	@FXML private Button days1, babies, months2, compass, work, uni, engineering;
	@FXML private Button back;
	
	private String topic;

	
	/**
	 * This function sets up JavaFX to allow a hover effect
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		styleTopicSelectionController();
	}
	
	
	/**
	 * This function adds an on-hover effect to the buttons, giving them a different colour when cursor is on them
	 */
	public void styleTopicSelectionController() {
		Button[] orangeButtons = {colours, days2, months1, weather, feelings, software};
		Button[] greenButtons = {days1, babies, months2, compass, work, uni, engineering};
		
		
		// Adding the DarkOrange colour to the list of buttons
		for(int i = 0; i < orangeButtons.length; i++) {
			HoverEffects.addHoverEffects(orangeButtons[i], "DarkOrange", "Black");
		}
		
		// Adding the LawnGreen colour to the list of buttons
		for(int i = 0; i < greenButtons.length; i++) {
			HoverEffects.addHoverEffects(greenButtons[i], "LawnGreen", "Black");
		}
		
		HoverEffects.addHoverEffects(back, "Red", "Black");
	}
	
	
	
	/**
	 * This function gets the topic name from the respective button click, and then switches to begin quiz scene
	 * Essential for fx button id to be exactly the same as the filename for the corresponding word list
	 * @param event - button click on topic button with fxid that is the same as the filename
	 */
	public void getTopic(ActionEvent event) throws IOException{
		Button topicButton = (Button) event.getSource();
		topic = topicButton.getId();
		QuizController.setTopic(topic);

		toAttempt(event);
	}
	

	/**
	 * This function switches to begin quiz scene
	 * @param event - button click on any topic
	 */
	public void toAttempt(ActionEvent event) throws IOException{
		SwitchScene switchToBeginAgain = new SwitchScene("/scenes/BeginQuiz.fxml",event);
		switchToBeginAgain.setWindowTitle("Kēmu Kupu: New Quiz");
		switchToBeginAgain.switchTo();
	}

	/**
	 * This function switches to main menu scene
	 * @param event - button click on any topic
	 */
	public void toOpeningMenu(ActionEvent event) throws IOException{
		SwitchScene switchToMenu = new SwitchScene("/scenes/Opening.fxml",event);
		switchToMenu.setWindowTitle("Kēmu Kupu: Menu");
		switchToMenu.switchTo();
	}

}
