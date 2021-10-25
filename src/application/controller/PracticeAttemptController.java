package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import application.backgroundTask.PlayWordBackgroundTask;

public class PracticeAttemptController extends AttemptController {
/**
 * This class is the controller class for the practice quiz attempt screen
 * Allows user to play word, adjust speed of synthesis, and enter spelling attempt
 * Uses wordProgress, wordAttempt, currentScore from parent QuizController.java class to keep track of progress
 * Controls PracticeWordAttempt.fxml
 */
	
	@FXML private Label wordProgress, attemptNum, timer, score, dashedWord;
	@FXML private TextField wordAttempt;
	@FXML private Slider playbackSpeed;
	@FXML private Button submitButton, wordPlayer, dontKnow, exitButton;
	@FXML private Button ā, ē, ī, ō, ū, Ā, Ē, Ī, Ō, Ū;
	private double speed;

	
	/**
	 * This function initializes the progress labels when reloaded and updates playback speed when slider is changed
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		speed=1;
		setWordAttempt((getWordAttempt()+1));
		
		// FXML initialization
		setProgressLabels();
		showDashed(getDashed());
		styleAttemptController();
		
		wordPlayer.fire();

		playbackSpeed.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				speed = 2.25-(playbackSpeed.getValue())/50;
			}
		});

	}


	/**
	 * This function plays the given quiz word at selected speed
	 * @param event - button click on speaker
	 */
	@Override
	public void playWord(ActionEvent event){
		PlayWordBackgroundTask bGTaskTwo = new PlayWordBackgroundTask(speed, submitButton, dontKnow, wordPlayer);
		Thread thrdTwo = new Thread(bGTaskTwo);
		thrdTwo.start();
	}

}
