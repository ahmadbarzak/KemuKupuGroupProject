package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LeaderboardController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private TextArea placingArea,nameArea,scoreArea,topicArea;
	private List<String> leaderboard;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getScores();
		orderScores();
		populateLeaderboard();
	}	
	
	public void getScores() {
		leaderboard= new ArrayList<String>();
		try {
			Scanner s;
			s = new Scanner(new File("src/script/leaderboard"));
			while (s.hasNextLine()){
			    leaderboard.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void orderScores() {
		Collections.sort(leaderboard, new ScoreComparator().reversed());
	}
	
	public void populateLeaderboard() {
		for(int i = 0; i < leaderboard.size() && i <15 ; i++) { 
			String[] leaderboardData = leaderboard.get(i).split(" ");   
			Double score = (Double.parseDouble(leaderboardData[0])/10.0);  
			String name = leaderboardData[1];
			String topic = leaderboardData[2];
			
			placingArea.appendText(Integer.toString(i+1)+"\n");
			nameArea.appendText(name+"\n");
			scoreArea.appendText(score+"\n");
			topicArea.appendText(topic+"\n");
	    }
	}
	
	public void clearLeaderboard(ActionEvent event) {
		
		Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Clear Leaderboard");
		alert.setHeaderText("Are you sure you want to clear the leaderboard?");
		alert.setContentText("You will not be able to get them back!");
		
		if(alert.showAndWait().get()== ButtonType.OK) {
			try {
				String[] command = new String[] {"src/script/quizFunctionality.sh", "clearScores"};
				ProcessBuilder pb = new ProcessBuilder();
				pb.command(command);
				Process process = pb.start();
				process.waitFor();
				
				// Reloads scene
				root= FXMLLoader.load(getClass().getResource("/scenes/Leaderboard.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
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
