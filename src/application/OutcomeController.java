package application;

/**
 * This class is the controller class for the outcome screens
 * Controls Correct.fxml, FirstIncorrect.fxml, SecondIncorrect.fxml, PracticeSecondIncorrect.fxml
 */

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
	public void toNext(ActionEvent event){
		setWordProgress(getWordProgress()+1);
		setWordAttempt(0);
		
		if (getWordProgress() > getMaxNumWords()) {
			toReward(event);
		} else if (getWordProgress() <=getMaxNumWords()) {
			toWordAttempt(event);
		}
	}
	
	
	/**
	 * This function allows user to have second attempt at word
	 * Applies to FirstIncorrect.fxml
	 * @param event - button click
	 */
	public void tryAgain(ActionEvent event){
		toWordAttempt(event);
	}
	
	
	/**
	 * This function allows user to hear pronunciation of word they got wrong
	 * Applies to SecondIncorrect.fxml
	 * @param event - button click
	 */
	public void pronunciation(ActionEvent event){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", Integer.toString(getWordProgress()), Integer.toString(getWordAttempt()), Integer.toString(1)};
		ScriptCall play = new ScriptCall(command);
		play.startProcess();
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
