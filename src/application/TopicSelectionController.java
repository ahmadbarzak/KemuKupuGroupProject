package application;

/**
 * This class is the controller class for the topic selection page
 * Allows user to select a topic and then generates the quiz words from that topic's word list
 * Controls TopicSelection.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TopicSelectionController {	
	private Stage stage;
	private Scene scene;
	private Parent root;	
	private String topic;
	
	/**
	 * This function gets the topic name from the respective button click, 
	 * generates the quiz words, and then switches to begin quiz scene
	 * Essential for fx button id to be exactly the same as the filename for the corresponding word list
	 * @param event - button click on topic button
	 */
	public void getTopic(ActionEvent event) throws IOException{
		// Getting topic name via java fx button id
		Button topicButton = (Button) event.getSource();
		topic = topicButton.getId();
		QuizController.setTopic("src/words/"+topic);
		
		// Changing to quiz begin quiz scene
		toAttempt(event);
	}	
	
	/**
	 * This function switches to begin quiz scene
	 * @param event - button click on any topic
	 */
	public void toAttempt(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/BeginQuiz.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: New Quiz");
		stage.setScene(scene);
		stage.show();
	}
	
}	