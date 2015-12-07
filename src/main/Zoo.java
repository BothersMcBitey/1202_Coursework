package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

import animals.Chimpanzee;
import animals.Gorilla;
import animals.Lion;
import animals.Tiger;
import structures.*;
import zookeepers.*;
import food.*;

public class Zoo {
	
	//Static stuff ========================================================
	
	public static HashSet<Food> foods;
	
	//So output can be easily switched to a log file
	/*
	 * out is static so it can be used in intitializeFoods() and to make it easier
	 * to reference in other classes, but could just as easily be instance based 
	 * so each zoo can have it's own log
	 */
	public static DelayedPrintStream out;
	
	//So you can control how fast messages are printed to the screen
	public class DelayedPrintStream extends PrintStream{

		private static final int WAIT = 0;
		
		public DelayedPrintStream(OutputStream out) {
			super(out);
		}
		
		@Override
		public void println(String s){
			try {
				Thread.sleep(WAIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			super.println(s);
		}
		
	}
	
	/*
	 * Slightly clunky solution, will rework if I have time
	 */
	public static final Food HAY = new Food("hay", 1, 4);
	public static final Food STEAK = new Food("steak", 3, 4);
	public static final Food FRUIT = new Food("fruit", 2, 3);
	public static final Food CELERY = new Food("celery", 0, 1);
	public static final Food FISH = new Food("fish", 3, 2);
	public static final Food ICE_CREAM = new Food("ice cream", 1, 3);
	
	private static void initializeFoods(){
		foods = new HashSet<Food>();
		out.println("foods created");
		foods.add(HAY);
		out.println("hay added");
		foods.add(STEAK);
		out.println("steak added");
		foods.add(FRUIT);
		out.println("fruit added");
		foods.add(CELERY);
		out.println("celery added");
		foods.add(FISH);
		out.println("fish added");
		foods.add(ICE_CREAM);
		out.println("ice cream added");
	}
	
	//instance stuff ========================================================

	private ArrayList<Enclosure> enclosures;
	private ArrayList<Zookeeper> zookeepers;
	private Foodstore store;
	private String name;
	private final int MAX_FOOD = 50;
	
	public Zoo(String config){
		out = new DelayedPrintStream(System.out);
		out.println("LOADING SIMULATOR:");
		store = new Foodstore();
		out.println("foodstore created");
		if(foods == null) initializeFoods();
		out.println("--------------------------");
		out.println();
		out.println("INITIALIZING ZOO:");
		if(config != null) initializeZoo(config);
		out.println("finished constructing zoo");
		out.println("--------------------------");
		out.println();
	}
	
	private void initializeZoo(String config) {
		//ensure the foodstore has a bucket for all foods
		for(Food f : foods){
			store.addBucket(f, MAX_FOOD);
		}
		enclosures = new ArrayList<>();
		zookeepers = new ArrayList<>();
		//Make sure the file exists and create a reader for it
		BufferedReader br; 
		try{
			br = new BufferedReader(new FileReader(config));
			//go through each line
			String line;
			// [0] is the class, 1 is the parameters
			String[] property;
			String[] parameters;
			while(br.ready()){
				line = br.readLine();
				property = line.split(":");
				//split up everything on the left of the ':' based on ','s
				parameters = property[1].split(",");
				//sort out what to do
				try{
					switch (property[0]) {
						case "zoo":
							name = parameters[0];
							System.out.println("Building " + parameters[0]);
							break;
							
						case "steak":case "hay":case "fruit":case "celery":case "fish":case "icecream":
							addFoodToLastStore(property[0], Integer.parseInt(parameters[0]));
							break;
							
						case "enclosure":							
							Enclosure tempEnclosure = new Enclosure(parameters[0]);
							tempEnclosure.addWaste(Integer.parseInt(parameters[1]));
							enclosures.add(tempEnclosure);
							out.println("creating enclosure");
							break;
							
						case "lion":case "tiger":case "gorilla":case "chimpanzee":							
							addAnimalToEnclosure(property[0], parameters[0], parameters[1].charAt(0), Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]), Integer.parseInt(parameters[4]));
							out.println("adding " + property[0] + " " + parameters[0] + " to enclosure");
							break;
							
						case "zookeeper":							
							zookeepers.add(new Zookeeper(parameters[0], enclosures.get(enclosures.size() -1), store));
							out.println("adding " + property[0] + " to enclosure");
							break;
						case "playZookeeper":							
							zookeepers.add(new PlayZookeeper(parameters[0], enclosures.get(enclosures.size() -1), store));
							out.println("adding " + property[0] + " to enclosure");
							break;
						case "physioZookeeper":							
							zookeepers.add(new PhysioZookeeper(parameters[0], enclosures.get(enclosures.size() -1), store));
							out.println("adding " + property[0] + " to enclosure");
							break;
							
						default:
							System.out.println("Tag \"" + property[0] + "\" not recognized.");
							break;
					}
				} catch (NullPointerException e){
					out.println("ERROR: invalid number of parameters on tag " + line);
				} catch (Exception e){
					out.println("ERROR: unable to parse line " + line);
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void addAnimalToEnclosure(String animal, String name, char gender, int age, int health, int enclosure){
		Enclosure tempEnclosure = enclosures.get(enclosure);
		try{
			switch (animal) {
			case "lion":
				tempEnclosure.addAnimal(new Lion(name, age, gender, health, tempEnclosure));
				break;
			case "tiger":
				tempEnclosure.addAnimal(new Tiger(name, age, gender, health, tempEnclosure));	
				break;
			case "gorilla":
				tempEnclosure.addAnimal(new Gorilla(name, age, gender, health, tempEnclosure));
				break;
			case "chimpanzee":
				tempEnclosure.addAnimal(new Chimpanzee(name, age, gender, health, tempEnclosure));
				break;
			}
		} catch (EnclosureFullException e){
			System.err.println("Cannot add " + animal + " to enclosure " + tempEnclosure);
		}
	}

	private void addFoodToLastStore(String food, int amount) {
		Foodstore targetStore;
		if(enclosures.isEmpty()){
			targetStore = store;
			out.println("adding " + amount + " " + food + " to zoo");
		}else{
			targetStore = enclosures.get(enclosures.size() -1).getFoodstore();
			out.println("adding " + amount + " " + food + " to enclosure");
		}
		switch(food){
			case "steak":
				targetStore.addFood(STEAK, amount);
				break;
			case "hay":
				targetStore.addFood(HAY, amount);
				break;
			case "fruit":
				targetStore.addFood(FRUIT, amount);
				break;
			case "celery":
				targetStore.addFood(CELERY, amount);
				break;
			case "fish":
				targetStore.addFood(FISH, amount);
				break;
			case "icecream":
				targetStore.addFood(ICE_CREAM, amount);
				break;
		}
	}

	public void go(){
		out.println("RUNNING SIMULATION:");
		out.println();
		int month = 1;
		while(true){
			out.println("MONTH " + month + ":");
			aMonthPasses();
			out.println("----------------------");
			out.println();
			month++;
			try{
				Thread.sleep(100);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	private void aMonthPasses(){
		for(Zookeeper z : zookeepers){
			out.println(z.getName() + ":");
			z.aMonthPasses();
			out.println();
		}
		for(Enclosure e : enclosures){
			out.println(e.getName() + ":");
			e.aMonthPasses();
			out.println();
		}
		out.println("Restocking Food:");
		for(Food f : foods){
			store.addFood(f, 20);
			try {
				out.println("adding " + f.getName() + ", store now has " + store.getRemaining(f));
			} catch (FoodNotFoundException e1) {
				//should be impossible
				e1.printStackTrace();
			}
		}
	}
}
