package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class InputReader {

	private BufferedReader in;
	private PrintStream out;
	
	public InputReader(InputStream in, PrintStream out){
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = out;
	}
	
	public String getInput(String message){
		boolean success = false;
		String input = null;
		//until valid input is received
		while(!success){
			out.println(message);
			try{
				input = in.readLine();
				success = true;
			} catch (IOException e){
				out.println("ERROR: Failed to read input");
			}
		}
		return input;
	}
}
