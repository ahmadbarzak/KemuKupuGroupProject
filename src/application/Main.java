package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
/**
 * The entry point into the application
 * Loads up opening menu as applications opening scene
 */	
	
	@Override
	public void start(Stage primaryStage){
		try{
			Parent root= FXMLLoader.load(getClass().getResource("/scenes/Opening.fxml"));
			Scene scene= new Scene(root); 
			primaryStage.setTitle("Kēmu Kupu: Menu");
			
			primaryStage.setMinWidth(1200);
			primaryStage.setMinHeight(900);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	} 
	

}