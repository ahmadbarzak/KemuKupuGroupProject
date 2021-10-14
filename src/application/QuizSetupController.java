package application;

/**
 * This class sets up a new quiz (test or practice) - gets words and resets game variables
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class QuizSetupController extends QuizController implements Initializable{
	@FXML Button beginButton, returnButton;
	
	/**
	 * This function initializes the quiz's progress tracker variables (word number, attempt number, current score) and gets words
	 * @param event - button click on begin quiz
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		AttemptController.addHoverEffects(beginButton, "DarkOrange", "Black");
		AttemptController.addHoverEffects(returnButton, "Red", "Black");
	}
	
	
	public void quizSetUp(ActionEvent event) throws IOException{
		setWordProgress(1);
		setWordAttempt(0);
		setCurrentScore(0);
		getWords("src/words/"+getTopic());
		setMaxNumWords(getMaxWordNum());
		toWordAttempt(event);
	}
	
	/**
	 * This function creates a list of the words to be tested and stores them in src/script/tempWords
	 * @param topicFilename - name of the filename containing topic's word list
	 */
	public void getWords(String topicFileName){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getWords",topicFileName,getQuizType()};
		callScriptCase(command);
	}
	
	/**
	 * This function gets the number of test words
	 * @return maxwords - int of number of test words
	 */
	public int getMaxWordNum() {
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getMaxWords"};
		int maxWords = Integer.parseInt(getScriptStdOut(command));
		return maxWords;
	}

}
