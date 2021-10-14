package application;

/**
 * This class sets up a new quiz (test or practice) - gets words and resets game variables
 */

import java.io.IOException;
import javafx.event.ActionEvent;

public class QuizSetupController extends QuizController{
	
	/**
	 * This function initializes the quiz's progress tracker variables (word number, attempt number, current score) and gets words
	 * @param event - button click on begin quiz
	 */
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
		ScriptCall getWords = new ScriptCall(command);
		getWords.startProcess();
	}
	
	/**
	 * This function gets the number of test words
	 * @return maxwords - int of number of test words
	 */
	public int getMaxWordNum() {
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getMaxWords"};
		ScriptCall getMaxWords = new ScriptCall(command);
		int maxWords = Integer.parseInt(getMaxWords.getStdOut());
		
		return maxWords;
	}

}
