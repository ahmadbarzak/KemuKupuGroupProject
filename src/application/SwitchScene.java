package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchScene {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public SwitchScene(String filename, ActionEvent event) {
		try {
			root= FXMLLoader.load(getClass().getResource(filename));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SetTitle(String title) {
		stage.setTitle(title);
		
	}
	
	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}

	public void switchTo() {
		stage.setScene(scene);
		stage.show();
	}

}
