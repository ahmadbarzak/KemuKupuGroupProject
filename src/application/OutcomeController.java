package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OutcomeController extends QuizController{
	
	@FXML private Label correctSpelling;
	
	/**
	 * This function switches screen from outcome to next word or reward screen depending on progress
	 * @param event - button click
	 */
	public void toNext(ActionEvent event) throws IOException{
		setWordProgress(getWordProgress()+1);
		setWordAttempt(0);
		
		if (getWordProgress() > getMaxNumWords()) {
			toReward(event);
		} else if (getWordProgress() <=getMaxNumWords()) {
			toWordAttempt(event);
		}
	}
	
	public void tryAgain(ActionEvent event) throws IOException {
		toWordAttempt(event);
	}
	
	public void pronounciation(ActionEvent event) throws IOException, InterruptedException {
		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), Integer.toString(1)};
		callScriptCase(command);
	}
	
	public void displayCorrectSpelling(String word) {
		correctSpelling.setText(word);
	}
	

}
