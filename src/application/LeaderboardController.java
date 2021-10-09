package application;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LeaderboardController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	private List<String> leaderboard;
	
	public void getScores() throws IOException {
		leaderboard= new ArrayList<String>();
		Scanner s = new Scanner(new File("src/script/leaderboard"));
		while (s.hasNextLine()){
		    leaderboard.add(s.nextLine());
		}
		s.close();
		orderScores();
	}
	
	public void orderScores() {
		Collections.sort(leaderboard, new ScoreComparator().reversed());
	    for(int i = 0; i < leaderboard.size(); i++) {   
	        System.out.print(leaderboard.get(i));
	        System.out.println();
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
