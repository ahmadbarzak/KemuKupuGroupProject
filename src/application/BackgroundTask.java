package application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.concurrent.Task;

public class BackgroundTask extends Task {
	//This class controls the process for the count down timer
	
	@Override
	protected Object call() throws Exception {
		// TODO Auto-generated method stub
		try {
			String cmd = "for (( i = 20 ; $i > 0; i=i-1)) ; do echo $i ; sleep 1; done";
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			
			Process process = builder.start();
			
			InputStream out = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(out));
			
			String line = null;
			while ((line = stdout.readLine()) != null ) {
				updateMessage(line);
			}	
			updateMessage("Times up!!");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
