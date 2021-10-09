package application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.concurrent.Task;

public class BackgroundTaskTwo extends Task {
	//This class controls the process for the count down timer
	double speed;
	
	public BackgroundTaskTwo(double speed) {
		this.speed = speed;
	}
	
	@Override
	protected Object call() throws Exception {
		// TODO Auto-generated method stub
		String wordProgress = Integer.toString(QuizController.getWordProgress());
		String wordAttempt = Integer.toString(QuizController.getWordAttempt());
		String wordSpeed = Double.toString(speed);
		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", wordProgress, wordAttempt, wordSpeed};
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
