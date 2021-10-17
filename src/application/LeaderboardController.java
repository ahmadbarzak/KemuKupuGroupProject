package application;

/**
 * This class is the controller class for the leader board screen
 * Allows user to view top 15 scores and clear leader board on confirmation
 * Controls Leaderboard.fxml
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class LeaderboardController implements Initializable{
	@FXML Button menuButton, clearButton;
	@FXML private TextArea placingArea,nameArea,scoreArea,topicArea;

	private List<String> leaderboard;

	
	/**
	 * This function initializes the leader board on scene entry or on reload (eg when leader board cleared)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		styleButtons();
		leaderboard=orderScores(getScores());
		populateLeaderboard(leaderboard);
	}	
	
	
	/**
	 * This function adds an on-hover effect to the buttons
	 */
	public void styleButtons() {
		HoverEffects.addHoverEffects(menuButton, "Red", "Black");
		HoverEffects.addHoverEffects(clearButton, "Red", "Black");
	}


	/**
	 * This function retrieves all the scores from the text file and converts them into a arraylist
	 * @return scores - list of strings containing score, name, and topic
	 */
	public List<String> getScores(){
		List<String> scores= new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/script/leaderboard"));
			String nextLine;
			while((nextLine = reader.readLine()) != null) {
				scores.add(nextLine);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return scores;
	}


	/**
	 * This function orders an arraylist in descending order
	 * @param scores - list of unsorted scores
	 * @return scores - given list of scores now sorted
	 */
	public List<String> orderScores(List<String> scores) {
		Collections.sort(scores, new ScoreComparator().reversed());

		return scores;
	}


	/**
	 * This function displays the scores on the leaderboard
	 * @param sortedScores - list of scores sorted in descending order
	 */
	public void populateLeaderboard(List<String> sortedScores) {
		for(int i = 0; i < sortedScores.size() && i <15 ; i++) {
			String[] leaderboardData = sortedScores.get(i).split(" ");
			int score = (Integer.parseInt(leaderboardData[0]));
			String name = leaderboardData[1];
			String topic = leaderboardData[2];

			placingArea.appendText(Integer.toString(i+1)+"\n");
			nameArea.appendText(name+"\n");
			scoreArea.appendText(score+"\n");
			topicArea.appendText(topic+"\n");
	    }
	}


	/**
	 * This function allows the user to clear the leaderboard on confirmation
	 * @param event - button click on clear leaderboard
	 */
	public void clearLeaderboard(ActionEvent event) {
		Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Clear Leaderboard");
		alert.setHeaderText("Are you sure you want to clear the leaderboard?");
		alert.setContentText("You will not be able to get them back!");

		if(alert.showAndWait().get()== ButtonType.OK) {
			String[] command = new String[] {"src/script/quizFunctionality.sh", "clearScores"};
			ScriptCall clearScores = new ScriptCall(command);
			clearScores.startProcess();

			SwitchScene reloadLeaderboard = new SwitchScene("/scenes/Leaderboard.fxml",event);
			reloadLeaderboard.switchTo();
		}

	}


	/**
	 * This function allows the user to return to the main menu
	 * @param event - button click on back to menu
	 */
	public void toOpeningMenu(ActionEvent event){
		SwitchScene switchToMenu = new SwitchScene("/scenes/Opening.fxml",event);
		switchToMenu.setWindowTitle("Kēmu Kupu: Menu");
		switchToMenu.switchTo();
	}

}
