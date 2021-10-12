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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

public class RewardController extends QuizController implements Initializable{	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private Label gameScore;
	@FXML private TextArea firstAttempt,secondAttempt,actual;
	@FXML private ImageView word1res, word2res, word3res, word4res, word5res;
	
	Image correctImg = new Image("/scenes/fullstar.png");
	Image halfCorrectImg = new Image("/scenes/halfstar.png");
	Image skipImg = new Image("/scenes/skip.png");
	Image wrongImg = new Image("/scenes/wrong.png");
	
	ArrayList<ImageView> results = new ArrayList<>();	
	int scoreSaved = 0;
	/**
	 * This function displays the users score
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameScore.setText(Integer.toString(getCurrentScore())+"/200");
		
		results.add(word1res);
		results.add(word2res);
		results.add(word3res);
		results.add(word4res);
		results.add(word5res);
		
		File file = new File("src/script/results");
		try {
			Scanner input = new Scanner(file);
			
			for(int i=0; i<5 && input.hasNextLine(); i++) {
				String[] data = input.nextLine().split(":");  
				
				String actualW = data[0];
				String attempt1 = data[1];
				String attempt2 = data[2];
				String symbolW = data[3];
				
				firstAttempt.appendText(attempt1+"\n\n");
				secondAttempt.appendText(attempt2+"\n\n");
				actual.appendText(actualW+"\n\n");
				
				if(symbolW.equals("1")) {
					results.get(i).setImage(correctImg);
				} else if(symbolW.equals("2")) {
					results.get(i).setImage(halfCorrectImg);
				} else if(symbolW.equals("3")) {
					results.get(i).setImage(wrongImg);
				} else if(symbolW.equals("4")) {
					results.get(i).setImage(skipImg);
				}
			}
			input.close();	
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveScore(ActionEvent event){
		if (scoreSaved == 1) {
			noDoubleSaves();
			return;
		}
		String name = getUserName();
		scoreSaved = 1;
		int bashScore = (int)getCurrentScore()*10;

		String[] command = new String[] {"src/script/quizFunctionality.sh", "saveScore", name, Integer.toString(bashScore), getTopic()};
		callScriptCase(command);
	}
	
	public String getUserName() {
		TextInputDialog dialog = new TextInputDialog("Enter name");
		dialog.setTitle("Save your test score");
		dialog.setHeaderText("Enter the name you want to save your score under\nPlease only enter 10 characters, and use no spaces!");		
		
		Optional<String> result = dialog.showAndWait();
		 
		while (result.get().length()>11 || result.get().length()==0 || result.get().contains(" ")) {
			result = dialog.showAndWait();
		}
		
		String name=result.get();
		
		return name;
	}	
	
	public void noDoubleSaves() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Save your test score");
		alert.setHeaderText("You have already saved your score :)");
		alert.showAndWait();
		return;
	}
	
	
}	