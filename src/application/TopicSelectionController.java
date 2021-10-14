package application;

/**
 * This class is the controller class for the topic selection page
 * Allows user to select a topic and then generates the quiz words from that topic's word list
 * Controls TopicSelection.fxml
 */

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class TopicSelectionController {		
	private String topic;
	
	/**
	 * This function gets the topic name from the respective button click, and then switches to begin quiz scene
	 * Essential for fx button id to be exactly the same as the filename for the corresponding word list
	 * @param event - button click on topic button
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
		switchToBeginAgain.SetTitle("Kēmu Kupu: New Quiz");
		switchToBeginAgain.switchTo();
	}
	
	/**
	 * This function switches to main menu scene
	 * @param event - button click on any topic
	 */
	public void toOpeningMenu(ActionEvent event) throws IOException{		
		SwitchScene switchToMenu = new SwitchScene("/scenes/Opening.fxml",event);
		switchToMenu.SetTitle("Kēmu Kupu: Menu");
		switchToMenu.switchTo();
	}
	
}	