package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchScene {
/**
 * This class controls the process for switching scenes after an event (button click, word entry etc)
 */
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/**
	 * This is the constructor class for switch scene that sets up an FXML loader ready to be switched to 
	 * @param filename - string containing path to file 
	 * @param event - action event to get previous screen to (loads new scene in same window)
	 */
	public SwitchScene(String filename, ActionEvent event) {
		try {
			root= FXMLLoader.load(getClass().getResource(filename));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This function sets the window title of the next scene
	 * @param title - string containing desired title
	 */
	public void setWindowTitle(String title) {
		stage.setTitle(title);
	}
	
	
	/**
	 * This function switches the screen once all initialisations have occured
	 */
	public void switchTo() {
		stage.setScene(scene);
		stage.show();
	}

}
