package application;

/**
 * This class is the controller class for the Opening Menu GUI
 * Allows user to navigate to game modules menu or to quit the application
 * Controls Opening.fxml
 */

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class OpeningMenuController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private Button quit;
	
	public void getQuizType(ActionEvent event) throws IOException{
		Button topicButton = (Button) event.getSource();
		String quizType = topicButton.getId();
		QuizController.setQuizType(quizType);
		
		toTopics(event);
	}
	
	/**
	 * This function switches to the topic selection menu
	 * @param event - button click on modules button
	 */
	public void toTopics(ActionEvent event) throws IOException{				
		root= FXMLLoader.load(getClass().getResource("/scenes/TopicSelection.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: Topic Selection");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This function quits the application
	 * @param event - button click on quit button
	 */
	public void toQuit(ActionEvent event) throws IOException{
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
