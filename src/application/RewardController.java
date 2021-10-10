package application;

/**
 * This class is the controller class for the reward screen
 * Allows user to see final score and pick whether to play again, pick a new topic, or go to opening menu
 * Controls RewardSceen.fxml, PracticeRewardScreen.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RewardController extends QuizController implements Initializable{	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private Label gameScore, maxScore;
	
	
	/**
	 * This function displays the users score
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameScore.setText(Double.toString(getCurrentScore())+"/200");
	}
	
	public void saveScore(ActionEvent event){
		String name = getUserName();
		
		int bashScore = (int)getCurrentScore()*10;

		String[] command = new String[] {"src/script/quizFunctionality.sh", "saveScore", name, Integer.toString(bashScore), getTopic()};
		callScriptCase(command);
	}
	
	public String getUserName() {
		TextInputDialog dialog = new TextInputDialog("Enter name");
		dialog.setTitle("Save your test score");
		dialog.setHeaderText("Enter the name you want to save your score under\nPlease only enter 10 characters, and use no spaces!");		
		
		Optional<String> result = dialog.showAndWait();
		 
		while (result.get().length()>11 || !result.isPresent() || result.get().contains(" ")) {
			result = dialog.showAndWait();
		}
		
		String name=result.get();
		
		return name;
	}	
	
	// Functions to switch to other quiz GUI screens
	public void toPlayAgain(ActionEvent event) throws IOException{			
		root= FXMLLoader.load(getClass().getResource("/scenes/BeginQuiz.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: New Quiz");
		stage.setScene(scene);
		stage.show();
	}
	
}	