package application;

import java.io.IOException;

/**
 * This class is the controller class for the practice quiz attempt screen
 * Controls WordAttempt.fxml
 */


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

public class PracticeAttemptController extends AttemptController{
	@FXML private Label wordProgress, attemptNum, timer, score, dashedWord; 
	@FXML TextField wordAttempt;
	@FXML Slider playbackSpeed;
	@FXML Button submitButton;
	double speed;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		speed=1;
	
		setWordAttempt((getWordAttempt()+1));
		attemptNum.setText("attempt "+Integer.toString(getWordAttempt())+" of 2");
		wordProgress.setText("play word "+Integer.toString(getWordProgress())+" of "+Integer.toString(getMaxNumWords()));
		
		String dashedCurrentWord = getDashed();
		if(getWordAttempt()==2) {
			StringBuilder dashedSecondLetterHint = new StringBuilder(dashedCurrentWord);
			dashedSecondLetterHint.replace(1, 2, hintGetter());
			dashedWord.setText(dashedSecondLetterHint.toString());
		} else {
			dashedWord.setText(dashedCurrentWord);
		}
		
		// Gets the value of the play back speed slider
		playbackSpeed.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				speed = 2.25-(playbackSpeed.getValue())/50;
			}
		});
	}
	
	@Override
	public void playWord(ActionEvent event) throws IOException{
		BackgroundTaskTwo bGTaskTwo = new BackgroundTaskTwo(speed);
		Thread thrdTwo = new Thread(bGTaskTwo);
		thrdTwo.start();
	}

}
