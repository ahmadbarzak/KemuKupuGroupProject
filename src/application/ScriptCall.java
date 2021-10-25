package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ScriptCall {
/**
 * This class controls the process for calling the BASH script
 */
	
	private String[] command;
	
	
	/**
	 * This is the constructor method for ScriptCall which sets the command to be run 
	 * @param command - string array containing the command to be started
	 */
	public ScriptCall(String[] command) {
		this.command=command;
	}
	
	
	/**
	 * This function begins the command
	 */
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
		
	
	/**
	 * This function obtains the standard output from the bash file
	 * @return scriptStdOut - immediate standard output from script
	 */
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
