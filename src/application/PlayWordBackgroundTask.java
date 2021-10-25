package application;

import javafx.concurrent.Task;

public class PlayWordBackgroundTask extends Task<Object> {
/**
 * This class controls the process for playing the current word
 * Sets up background BASH task to not interfere with timer countdown task
 * Used in AttemptController and PracticeAttemptController
 */
	
	private double speed;

	/**
	 * This function sets the playback speed for the task to be run
	 * @param speed
	 */
	public PlayWordBackgroundTask(double speed) {
		this.speed = speed;
	}

	
	@Override
	protected Object call() throws Exception {
		String wordProgress = Integer.toString(QuizController.getWordProgress());
		String wordAttempt = Integer.toString(QuizController.getWordAttempt());
		String wordSpeed = Double.toString(speed);

		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", wordProgress, wordAttempt, wordSpeed};
		ScriptCall play = new ScriptCall(command);
		play.startProcess();

		return null;
	}

}
