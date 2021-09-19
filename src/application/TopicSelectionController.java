package application;

/**
 * This class is the controller class for the topic selection page
 * Controls TopicSelection.fxml
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TopicSelectionController {	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	//@FXML private Button colour, days, days2, months1, months2, babies, weather, compassPoints, feelings, work, engineering, software, uniLife;
	
	
	/**
	 * These functions are for each button/topic
	 * * IMPLEMENT *
	 * @param event - button click on respective button
	 */
	public void toColours(ActionEvent event) throws IOException{
		System.out.println("Colours");
		toAttempt(event);
	}
	
	public void toDays1(ActionEvent event) throws IOException{
		System.out.println("Days1");
		toAttempt(event);
	}
	
	public void toDays2(ActionEvent event) throws IOException{
		System.out.println("Days2");
		toAttempt(event);
	}
	
	public void toMonths1(ActionEvent event) throws IOException{
		System.out.println("Months1");
		toAttempt(event);
	}
	
	public void toMonths2(ActionEvent event) throws IOException{
		System.out.println("Months2");
		toAttempt(event);
	}
	
	public void toBabies(ActionEvent event) throws IOException{
		System.out.println("Babies");
		toAttempt(event);
	}
	
	public void toWeather(ActionEvent event) throws IOException{
		System.out.println("Weather");
		toAttempt(event);
	}
	
	public void toCompassPoints(ActionEvent event) throws IOException{
		System.out.println("Compass Points");
		toAttempt(event);
	}
	
	public void toFeelings(ActionEvent event) throws IOException{
		System.out.println("Feelings");
		toAttempt(event);
	}
	
	public void toWork(ActionEvent event) throws IOException{
		System.out.println("Work");
		toAttempt(event);
	}
	
	public void toEngineering(ActionEvent event) throws IOException{
		System.out.println("Engineering");
		toAttempt(event);
	}
	
	public void toSoftware(ActionEvent event) throws IOException{
		System.out.println("Software");
		toAttempt(event);
	}
	
	public void toUniLife(ActionEvent event) throws IOException{
		System.out.println("UniLife");
		toAttempt(event);
	}
	
	
	/**
	 * This function switches to word attempt GUI
	 * @param event - button click on any topic
	 */
	public void toAttempt(ActionEvent event) throws IOException{
		System.out.println("In");
		root= FXMLLoader.load(getClass().getResource("/scenes/WordAttempt.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Kēmu Kupu: New Quiz");
		stage.setScene(scene);
		stage.show();
	}
	
}	