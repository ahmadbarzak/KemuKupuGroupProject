package application;

/**
 * This class is the controller class for the topic selection page
 * Allows user to select a topic and then generates the quiz words from that topic's word list
 * Controls TopicSelection.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TopicSelectionController implements Initializable {	
	private Stage stage;
	private Scene scene;
	private Parent root;	
	private String topic;
	@FXML Button colours, days2, months1, weather, feelings, software;
	@FXML Button days1, babies, months2, compass, work, uni, engineering;
	@FXML Button back;
	
	/**
	 * This function gets the topic name from the respective button click, and then switches to begin quiz scene
	 * Essential for fx button id to be exactly the same as the filename for the corresponding word list
	 * @param event - button click on topic button
	 */
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		Button[] orangeButtons = {colours, days2, months1, weather, feelings, software};
		Button[] greenButtons = {days1, babies, months2, compass, work, uni, engineering};
		for(int i = 0; i < orangeButtons.length; i++) {
			AttemptController.addHoverEffects(orangeButtons[i], "DarkOrange", "Black");
		}
		for(int i = 0; i < greenButtons.length; i++) {
			AttemptController.addHoverEffects(greenButtons[i], "LawnGreen", "Black");
		}
		AttemptController.addHoverEffects(back, "Red", "Black");
	}
	
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
		root= FXMLLoader.load(getClass().getResource("/scenes/BeginQuiz.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: Quiz");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This function switches to main menu scene
	 * @param event - button click on any topic
	 */
	public void toOpeningMenu(ActionEvent event) throws IOException{		
		root= FXMLLoader.load(getClass().getResource("/scenes/Opening.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu");
		stage.setScene(scene);
		stage.show();
	}
	
}	