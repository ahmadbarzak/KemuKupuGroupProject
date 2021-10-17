package application;

/**
 * This class is the controller class for the Opening Menu GUI
 * Allows user to navigate to game modules menu or to quit the application
 * Controls Opening.fxml
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class OpeningMenuController implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private Button test, practice, leaderBoard, help, quit;
	
	/**
	 * This function gets the quiz type from the respective button click, and then switches to topic selection scene
	 * @param event - button click on quiz button
	 */
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		AttemptController.addHoverEffects(test, "DarkOrange", "Black");
		AttemptController.addHoverEffects(practice, "DarkOrange", "Black");
		AttemptController.addHoverEffects(leaderBoard, "DarkOrange", "Black");
		AttemptController.addHoverEffects(help, "LawnGreen", "Black");
		AttemptController.addHoverEffects(quit, "Red", "Black");
	}

	
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
	
	public void toLeaderboard(ActionEvent event) throws IOException{				
		root= FXMLLoader.load(getClass().getResource("/scenes/Leaderboard.fxml"));
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
	
	public void help(ActionEvent event) throws IOException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Kēmu Kupu Help");
		alert.setHeaderText("Kēmu Kupu Help");
		String s ="Click on test to quiz yourself on 5 māori words\n";
		alert.setContentText(s);
		alert.show();
	}
		
	
}
