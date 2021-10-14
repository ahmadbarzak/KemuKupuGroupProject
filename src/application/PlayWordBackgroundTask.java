package application;

/**
 * This class controls the process for playing the current word
 * Used in AttemptController and PracticeAttemptController 
 */

import javafx.concurrent.Task;

public class PlayWordBackgroundTask extends Task<Object> {
	private double speed;
	
	
	public PlayWordBackgroundTask(double speed) {
		this.speed = speed;
	}
	
	
	@Override
	protected Object call() throws Exception {
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
