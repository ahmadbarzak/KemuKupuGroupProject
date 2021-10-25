package application;

import javafx.concurrent.Task;
import javafx.scene.control.Button;

public class PlayWordBackgroundTask extends Task<Object> {
/**
 * This class controls the process for playing the current word
 * Sets up background BASH task to not interfere with timer countdown task
 * Used in AttemptController and PracticeAttemptController
 */
	
	private double speed;
	private Button submitButton, dontKnow, wordPlayer;

	/**
	 * This function sets the playback speed for the task to be run
	 * @param speed
	 */
	public PlayWordBackgroundTask(double speed, Button submitButton, Button dontKnow, Button wordPlayer) {
		this.speed = speed;
		this.submitButton = submitButton;
		this.dontKnow = dontKnow;
		this.wordPlayer = wordPlayer;
	}

	
	@Override
	protected Object call() throws Exception {
		submitButton.setDisable(true);
		dontKnow.setDisable(true);
		wordPlayer.setDisable(true);
		
		String wordProgress = Integer.toString(QuizController.getWordProgress());
		String wordAttempt = Integer.toString(QuizController.getWordAttempt());
		String wordSpeed = Double.toString(speed);

		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", wordProgress, wordAttempt, wordSpeed};
		ScriptCall play = new ScriptCall(command);
		play.startProcess();
		submitButton.setDisable(false);
		dontKnow.setDisable(false);
		wordPlayer.setDisable(false);
		
		return true;
	}

}
