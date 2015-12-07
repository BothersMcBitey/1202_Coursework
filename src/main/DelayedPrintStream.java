package main;

import java.io.OutputStream;
import java.io.PrintStream;

/*This class just overrides the printstream's println() method, adding a pause of
 * a specified number of milliseconds to the beginning of each print attempt, so
 * it's easier to keep up with the console updates 
 */
public class DelayedPrintStream extends PrintStream{

	private int wait;
	
	public DelayedPrintStream(OutputStream out, int wait) {
		super(out);
		this.wait = wait;
	}
	
	@Override
	public void println(String s){
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		super.println(s);
	}
	
	public void setWait(int wait){
		this.wait = wait;
	}
	
}
