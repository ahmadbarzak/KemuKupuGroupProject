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
		
		//To prevent repeated clicking of the wordPlayer button,
		//or submitting before fully hearing the word, the buttons are disabled
		submitButton.setDisable(true);
		dontKnow.setDisable(true);
		wordPlayer.setDisable(true);
		
		//Variables necessary to run bash script that performs text to speech is obtained
		String wordProgress = Integer.toString(QuizController.getWordProgress());
		String wordAttempt = Integer.toString(QuizController.getWordAttempt());
		String wordSpeed = Double.toString(speed);

		//bash script is run, allowing text to speech to play the word
		String[] command = new String[] {"src/script/quizFunctionality.sh", "play", wordProgress, wordAttempt, wordSpeed};
		ScriptCall play = new ScriptCall(command);
		play.startProcess();
		
		//the buttons are finally enabled allowing the user to either repeat the word, submit the answer or skip
		submitButton.setDisable(false);
		dontKnow.setDisable(false);
		wordPlayer.setDisable(false);
		
		return true;
	}

}
