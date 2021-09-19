package application;

/**
 * This class is the controller class for most of the quiz functionality, CAN SPLIT UP INTO SMALLER ONES IF EASIER
 * Controls WordAttempt.fxml, Correct.fxml, FirstIncorrect.fxml, SecondIncorrect.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class QuizController {	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	//@FXML private Button playWord, dontKnow, submitWord
	@FXML private Label wordNum, wordTotal, attemptNum, attemptTotal; 
	@FXML Slider playbackSpeed;
	@FXML TextField wordAttempt;
	
	
	/**
	 * This function plays the word
	 * * IMPLEMENT *
	 * @param event - button click on speaker
	 */
	public void playWord(ActionEvent event) throws IOException{
		System.out.println("Play word");
	}
	
	/**
	 * This function does don't know functionality when button is clicked
	 * * IMPLEMENT *
	 * @param event - button click
	 */
	public void dontKnow(ActionEvent event) throws IOException{
		System.out.println("Dont Know");
	}
	
	/**
	 * This function submits the spelling and then switches to appropriate outcome screen
	 * * IMPLEMENT *
	 * @param event - button click
	 */
	public void submitWord(ActionEvent event) throws IOException{
		String attempt = wordAttempt.getText();
		System.out.println("Submit Word " + attempt);
		
		toCorrect(event);
		//toFirstIncorrect(event);
		//toSecondIncorrect(event);
	}
	
	
	/**
	 * This function switches screen from outcome to next word or reward screen
	 * @param event - button click
	 */
	public void toNext(ActionEvent event) throws IOException{
		System.out.println("Next Word");
		//toWordAttempt(event);
		toReward(event);
	}
	
	/**
	 * This function switches screen from outcome to try again (first incorrect)
	 * @param event - button click
	 */
	public void toTryAgain(ActionEvent event) throws IOException{
		System.out.println("Try Again");
		toWordAttempt(event);
	}
	
	
	/**
	 * Switch Screen Functions
	 * @param event - button click
	 */
	public void toCorrect(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/Correct.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toFirstIncorrect(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/FirstIncorrect.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toSecondIncorrect(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/SecondIncorrect.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toWordAttempt(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/WordAttempt.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toReward(ActionEvent event) throws IOException{
		root= FXMLLoader.load(getClass().getResource("/scenes/RewardScreen.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}	