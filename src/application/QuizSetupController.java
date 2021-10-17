package application;

/**
 * This class sets up a new quiz (test or practice) - gets words and resets game variables
 */

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class QuizSetupController extends QuizController{
	
	/**
	 * This function initializes the quiz's progress tracker variables (word number, attempt number, current score) and gets words
	 * @param event - button click on begin quiz
	 */
	public void quizSetUp(ActionEvent event){
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
	
	public void quickHelp(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Quick Test Help");
		alert.setHeaderText("Pre-test Help");
		alert.setContentText("To complete a Kēmu Kupu test, press begin quiz to hear the first word.\n"
				+ "Enter the spelling of the played word in the text box, or press on the speaker to replay the word.\n"
				+ "You can adjust the speed of the speech by moving the slider and pressing on the speaker.\n"
				+ "Once you have entered your spelling, press enter or submit to check.\n"
				+ "If correct, you'll move on to the next word, else try again with a second letter hint!"
				+ "Repeat this for the 5 words.\n\n"
				+ "Scoring: 20 points for correct + full time bonus (if correct on first go) or 1/2 x time bonus (correct on second go)\n"
				+ "Your total score will be the score of all 5 words cumulated\n\n"
				+ "Good luck!");
		
		alert.showAndWait();
	}

}
