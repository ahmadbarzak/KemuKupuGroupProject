package application;
import javafx.concurrent.Task;

public class BackgroundTaskTwo extends Task<Object> {
	//This class controls the process for the count down timer
	double speed;
	
	public BackgroundTaskTwo(double speed) {
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
			if(isCancelled()) {
				process.destroy();
				System.out.println("destroyed");
				return null;
			}
			process.waitFor();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
