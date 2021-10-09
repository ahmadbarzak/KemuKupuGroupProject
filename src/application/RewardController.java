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
		gameScore.setText(Integer.toString(getCurrentScore()));
		maxScore.setText(Integer.toString(getMaxNumWords()));
	}
	
	public void saveScore(ActionEvent event){
		String name = getUserName();

		String[] command = new String[] {"src/script/quizFunctionality.sh", "saveScore", name, Integer.toString(getCurrentScore()), getTopic()};
		callScriptCase(command);
	}
	
	public String getUserName() {
		TextInputDialog dialog = new TextInputDialog("Enter name");
		dialog.setTitle("Save your test score");
		dialog.setHeaderText("Enter the name you want to save your score under");
		 
		Optional<String> result = dialog.showAndWait();
		String name = "none.";
		 
		if (result.isPresent()) {
		    name = result.get();
		}
		
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