package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RewardController extends QuizController implements Initializable{
/**
 * This class is the controller class for the reward screen
 * Allows user to see final score and word outcomes, and pick whether to play again, pick a new topic, or go to opening menu
 * Controls RewardSceen.fxml
 */	
	
	@FXML private Label gameScore;
	@FXML private TextArea firstAttempt,secondAttempt,actual;
	@FXML private Button menu, playAgain, gamesModule, saveScore;
	@FXML private ImageView word1res, word2res, word3res, word4res, word5res;

	ArrayList<ImageView> resultSymbol;
	Image correctImg = new Image("/scenes/images/fullstar.png");
	Image halfCorrectImg = new Image("/scenes/images/halfstar.png");
	Image skipImg = new Image("/scenes/images/skip.png");
	Image wrongImg = new Image("/scenes/images/wrong.png");

	private boolean scoreSaved = false;


	/**
	 * This function displays the users score, and particular word outcomes, and allows for javaFX hover
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameScore.setText(Integer.toString(getCurrentScore())+"/200");
		styleRewardController();
		
		initImageViews();
		populateWordOutcomeTable();
	}
	
	
	/**
	 * This function adds an on-hover effect to the buttons
	 */
	public void styleRewardController() {
		HoverEffects.addHoverEffects(menu, "Red", "Black");
		HoverEffects.addHoverEffects(playAgain, "DarkOrange", "Black");
		HoverEffects.addHoverEffects(gamesModule, "LawnGreen", "Black");
		HoverEffects.addHoverEffects(saveScore, "DarkOrange", "Black");
	}


	/**
	 * This function initializes the resultSymbol imageview array
	 */
	public void initImageViews() {
		resultSymbol = new ArrayList<>();
		resultSymbol.add(word1res);
		resultSymbol.add(word2res);
		resultSymbol.add(word3res);
		resultSymbol.add(word4res);
		resultSymbol.add(word5res);
	}


	/**
	 * This function fills in the text field with the users test words and their attempts
	 */
	public void populateWordOutcomeTable() {
		try{
			BufferedReader reader = new BufferedReader(new FileReader("src/script/results"));
			String nextLine;
			for(int i = 0; i<5 && ((nextLine = reader.readLine()) != null); i++) {
				String[] data = nextLine.split(":");
				String currentWord = data[0];
				String currentAttempt1 = data[1];
				String currentAttempt2 = data[2];
				String currentSymbol = data[3];

				firstAttempt.appendText(currentAttempt1+"\n\n");
				secondAttempt.appendText(currentAttempt2+"\n\n");
				actual.appendText(currentWord+"\n\n");
				setSymbol(currentSymbol, i);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * This function sets an appropriate symbol signifying correct on 1st go,
	 * correct on 2nd go, incorrect, or skipped for a word
	 * @param outcome - "1" for correct first go, "2" for correct second go, "3" for incorrect, "4" skip
	 * @param wordNum - current word data being populated
	 */
	public void setSymbol(String outcome, int wordNum) {
		if(outcome.equals("1")) {
			resultSymbol.get(wordNum).setImage(correctImg);
		} else if(outcome.equals("2")) {
			resultSymbol.get(wordNum).setImage(halfCorrectImg);
		} else if(outcome.equals("3")) {
			resultSymbol.get(wordNum).setImage(wrongImg);
		} else if(outcome.equals("4")) {
			resultSymbol.get(wordNum).setImage(skipImg);
		}
	}


	/**
	 * This function allows a user to save their score if they would like
	 * Only can save score once
	 * @param event - button click on save score button
	 */
	public void saveScore(ActionEvent event){
		if (scoreSaved) {
			noDoubleSaves();
			return;
		}

		String name = getUserName();
		if (name!=null) {
			String[] command = new String[] {"src/script/quizFunctionality.sh", "saveScore", name, Integer.toString(getCurrentScore()), getTopic()};
			ScriptCall saveScore = new ScriptCall(command);
			saveScore.startProcess();
			scoreSaved = true;
		}
	}


	/**
	 * This function retrieves a name that the user wants to save their score under
	 * Ensures the name is less than 11 characters and contains no spaces
	 * @return name - string containing the name user entered
	 */
	public String getUserName() {
		String name = null;
		TextInputDialog dialog = new TextInputDialog("Enter name");
		dialog.setTitle("Save your test score");
		dialog.setHeaderText("Enter the name you want to save your score under\nPlease only enter 10 characters, and use no spaces!");

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()){
			name=result.get();
		}

		if (name==null) {
			return name;
		} else if (name.length()>10 || name.length()==0 || name.contains(" ")) {
			invalidNameAlert();
			name=getUserName();
		}

		return name;
	}


	/**
	 * This function alerts the user if the name they entered is invalid
	 */
	public void invalidNameAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Name Entry");
		alert.setHeaderText("The name you entered was either empty, greater that 10 characters, or contained spaces!");
		alert.setContentText("Press OK to re-enter a valid name");
		alert.showAndWait();
	}


	/**
	 * This function alerts the user if they have already saved their score
	 */
	public void noDoubleSaves() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Save your test score");
		alert.setHeaderText("You have already saved your score :)");
		alert.showAndWait();
		return;
	}


}
