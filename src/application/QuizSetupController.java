package application;

import java.io.IOException;

import javafx.event.ActionEvent;

public class QuizSetupController extends QuizController{
	
	/**
	 * This function initializes the quiz's progress tracker variables (word number, attempt number, current score)
	 * @param event - button click on begin quiz
	 */
	public void quizSetUp(ActionEvent event) throws IOException{
		setWordProgress(1);
		setWordAttempt(0);
		setCurrentScore(0);
		
		getWords(getTopicFile());
		setMaxNumWords(getMaxWordNum());
		System.out.println(getQuizType());
		
		toWordAttempt(event);
	}
	
	/**
	 * This function creates a list of the words to be tested and stores them in src/script/tempWords
	 * @param topicFilename - name of the filename containing topic's word list
	 */
	public void getWords(String topicFileName){
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getWords",topicFileName};
		callScriptCase(command);
	}
	
	public int getMaxWordNum() {
		String[] command = new String[] {"src/script/quizFunctionality.sh", "getMaxWords"};
		int maxWords = Integer.parseInt(getScriptStdOut(command));
		return maxWords;
	}

}
