package application.backgroundTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.concurrent.Task;

public class TimerBackgroundTask extends Task<Object> {
/**
 * This class controls the process for the count down timer
 * Used in AttemptController and PracticeAttemptController 
 * Code attributed to softeng206 GUI concurrency lab,
 * and the following links:
 * https://docs.oracle.com/javase/9/docs/api/index.html?javafx%2Fconcurrent%2FTask.html
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
 */	
	
	
	@Override
	protected Object call() throws Exception {
		try {
			//1.6 second delay to give user time to hear the word before timer goes down
			Thread.sleep(1600);
			
			// i=20, 20 second timer
			String cmd = "for (( i = 20 ; $i > 0; i=i-1)) ; do echo $i ; sleep 1; done";
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			
			Process process = builder.start();
			
			InputStream out = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(out));
			
			String line = null;
			
			while ((line = stdout.readLine()) != null ) {
				updateMessage(line);
			}
			// once time is up, user can still enter word  but will not get time bonus
			updateMessage("no time bonus to be awarded");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
