package application;

/**
 * This class is the controller class for the reward screen
 * Controls RewardSceen.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RewardController implements Initializable{	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private Label gameScore, maxScore;
	
	
	//To implement show score
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameScore.setText("3");
		maxScore.setText("5");
	}
	
	
	/**
	 * These functions switch screens
	 * @param event - button click
	 */
	public void toPlayAgain(ActionEvent event) throws IOException{		
		root= FXMLLoader.load(getClass().getResource("/scenes/WordAttempt.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: New Quiz");
		stage.setScene(scene);
		stage.show();
	}
	
	public void toGameModules(ActionEvent event) throws IOException{		
		root= FXMLLoader.load(getClass().getResource("/scenes/TopicSelection.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: Topic Selection");
		stage.setScene(scene);
		stage.show();
	}
	
	public void toOpeningMenu(ActionEvent event) throws IOException{		
		root= FXMLLoader.load(getClass().getResource("/scenes/Opening.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu");
		stage.setScene(scene);
		stage.show();
	}
	
}	