package application;

/**
 * This class is the controller class for the help screen
 * Allows user to view instructions of the applications
 * Controls Help.fxml
 */

import javafx.event.ActionEvent;

public class HelpController {
	
	
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
