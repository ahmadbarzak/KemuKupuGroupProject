package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		setMaxNumWords(getMaxWords());
		System.out.println(getQuizType());
		
		toWordAttempt(event);
	}
	
	/**
	 * This function creates a list of the words to be tested and stores them in src/script/tempWords
	 * @param topicFilename - name of the filename containing topic's word list
	 */
	public void getWords(String topicFileName){
		try {
			// Calling getWords case in script file to create and populate a text file with quiz words
			String[] command = new String[] {"src/script/quizFunctionality.sh", "getWords",topicFileName};
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getMaxWords() {
		int maxWords=0;
		try {
			String command = "cat src/script/tempWords | wc -l | sed 's/ //g'";
			ProcessBuilder pb = new ProcessBuilder("/bin/bash","-c", command);
			Process process = pb.start();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			process.waitFor();
			maxWords = Integer.parseInt(stdout.readLine());	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return maxWords;
	}

}
