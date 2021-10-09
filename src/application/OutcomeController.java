package application;

/**
 * This class is the controller class for the outcome screens
 * Controls Correct.fxml, FirstIncorrect.fxml, SecondIncorrect.fxml, PracticeSecondIncorrect.fxml
 */

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OutcomeController extends QuizController{
	
	@FXML private Label correctSpelling;
	
	/**
	 * This function switches screen from outcome to next word or reward screen depending on progress
	 * Applies to Correct.fxml, SecondIncorrect.fxml, PracticeSecondIncorrect.fxml
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
	
	/**
	 * This function allows user to enter second attempt of word
	 * Applies to FirstIncorrect.fxml
	 * @param event - button click
	 */
	public void tryAgain(ActionEvent event) throws IOException {
		toWordAttempt(event);
	}
	
	/**
	 * This function allows user to hear pronunciation of word they got wrong
	 * Applies to SecondIncorrect.fxml
	 * @param event - button click
	 */
	public void pronunciation(ActionEvent event) throws IOException, InterruptedException {
		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), Integer.toString(1)};
		callScriptCase(command);
	}
	
	/**
	 * This function allows user to see spelling of word they got wrong
	 * Applies to SecondIncorrect.fxml
	 * @param event - button click
	 */
	public void displayCorrectSpelling(String word) {
		correctSpelling.setText(word);
	}
	

}
