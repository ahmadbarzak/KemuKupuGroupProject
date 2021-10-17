package application;

import javafx.scene.control.Button;

public class HoverEffects {
	
	public static void addHoverEffects(Button button, String backgroundColour, String textColour) {
		button.setOnMouseEntered(e -> button.setStyle("-fx-background-color:" + backgroundColour + "; -fx-text-fill: " + textColour + ";"));
		button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ebe5d9; -fx-text-fill: #5b88bf;"));
	}

}
