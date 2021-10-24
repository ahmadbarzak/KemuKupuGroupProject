package application;

import javafx.scene.control.Button;

public class HoverEffects {
/**
 * This class controls the process for adding a hover effect to buttons
 */
	
	/**
	 * This function adds a hover effect to a button 
	 * @param button - button which hover effect is to be added to
	 * @param backgroundColour - colour to change the button background to
	 * @param textColour - colour to change the text to
	 */
	public static void addHoverEffects(Button button, String backgroundColour, String textColour) {
		button.setOnMouseEntered(e -> button.setStyle("-fx-background-color:" + backgroundColour + "; -fx-text-fill: " + textColour + ";"));
		button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ebe5d9; -fx-text-fill: #5b88bf;"));
	}

}
