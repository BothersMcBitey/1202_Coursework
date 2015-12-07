package main;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BootStrapper {

	public static void main(String[] args) {
		if(args.length <= 1){
			if(Files.exists(Paths.get(args[0]))){
				Zoo z = new Zoo(args[0]);
				z.go();
			} else {
				System.out.println("ERROR: Config file does not exist, aborting simulation");
			}
		} else {
			System.out.println("ERROR: Too many arguments");
			System.out.println();
			System.out.println("Use: java BootStrapper [configPath]");
		}
	}
}