package application;

/**
 * This class controls the process for calling the BASH script
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ScriptCall {
	private String[] command;
	
	public ScriptCall(String[] command) {
		this.command=command;
	}
	
	public void startProcess() {
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public String getStdOut() {
		String scriptStdOut="";
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command(command);
			Process process = pb.start();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));			
			scriptStdOut=stdout.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return scriptStdOut;
	}

}
