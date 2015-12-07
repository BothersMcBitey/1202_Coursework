package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	
 	/* So output can be easily switched to a log file, So you can control how fast 
	 * messages are printed to the screen
	 * 
	 * out is static so it can be used in intitializeFoods() and to make it easier
	 * to reference in other classes, but could just as easily be instance based 
	 * so each zoo can have it's own log - could pass out as parameter
	 */
	public static DelayedPrintStream out;
	
	//Slightly clunky solution, will rework if I have time
	public static HashSet<Food> foods;
	
	/* Make a bunch of food constants other classes can refer to so you can
	 * assume you're always referring to the same instance of STEAK for example 
	 */
	public static final Food HAY = new Food("hay", 1, 4);
	public static final Food STEAK = new Food("steak", 3, 4);
	public static final Food FRUIT = new Food("fruit", 2, 3);
	public static final Food CELERY = new Food("celery", 0, 1);
	public static final Food FISH = new Food("fish", 3, 2);
	public static final Food ICE_CREAM = new Food("ice cream", 1, 3);
	
	/*
	 * Adds each food type to the 'foods' hashset so they can be iterated over 
	 */
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
		out = new DelayedPrintStream(System.out, 250);
		
		//sets up the food store and food type constants
		out.println("LOADING SIMULATOR:");
		store = new Foodstore();
		out.println("foodstore created");
		if(foods == null) initializeFoods();
		out.println("--------------------------");
		out.println();
		
		//loads the zoo from the config file supplied
		out.println("INITIALIZING ZOO:");
		initializeZoo();
		if(config != null){
			loadConfig(config);
			out.println("finished constructing zoo");
		} else {
			out.println("no config file, creating empty zoo");
		}
		out.println("--------------------------");
		out.println();
	}
	
	public void go(){
		out.println("RUNNING SIMULATION: " + name);
		out.println();
		
		int month = 1;
		int interval = 1000;
		
		while(true){
			out.println("MONTH " + month + ":");
			aMonthPasses();
			out.println("----------------------");
			out.println();
			month++;
			try{
				Thread.sleep(interval);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	private void aMonthPasses(){
		//restock the zoo store first, which reduces chance of the zoo running out of food
		out.println("Restocking Food:");
		for(Food f : foods){
			store.addFood(f, 20);
			try {
				out.println("adding " + f.getName() + ", store now has " + store.getRemaining(f));
			} catch (FoodNotFoundException e) {
				/* should be impossible, as initializeZoo() adds buckets for all the foods, and by
				 * adding the target food to the store in the line prior you guarantee a bucket exists anyway
				 */
				e.printStackTrace();
			}
		}
		out.println();
		
		//Zookeepers do stuff next, which reduces chance of enclosures running out of food
		for(Zookeeper z : zookeepers){
			out.println(z.getName() + ":");
			z.aMonthPasses();
			out.println();
		}
		
		//now each enclosure can work out what it's doing
		for(Enclosure e : enclosures){
			out.println(e.getName() + ":");
			e.aMonthPasses();
		}
	}
	
	private void initializeZoo() {
		//ensure the food store has a bucket for each food
		for(Food f : foods){
			store.addBucket(f, MAX_FOOD);
		}
		enclosures = new ArrayList<>();
		zookeepers = new ArrayList<>();
	}
	
	private void loadConfig(String config){
		//Make sure the file exists and create a reader for it
		BufferedReader br; 
		try{
			br = new BufferedReader(new FileReader(config));
			//go through each line
			String line;
			/*
			 * property will contain two strings, the tag (e.g. "tiger:") and the parameters (e.g. "Leo,M,12,5,0")
			 * parameters will contain each parameter as a separate string, e.g. {"leo", "m", "12", "5", "0"}
			 */
			String[] property;
			String[] parameters;
			//while there's still text in the file
			while(br.ready()){
				line = br.readLine();
				//convert the line to property and param as described above
				property = line.split(":");
				parameters = property[1].split(",");
				try{
					/* process each tag as described in the README
					 */
					switch (property[0]) {
						case "zoo":
							name = parameters[0];
							out.println("Building " + parameters[0]);
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
							
						//very minor code duplication across these three, but trying to avoid this would be quite awkward, so it stays
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
							
						//so users can see when they've got unused tags
						default:
							out.println("Tag \"" + property[0] + "\" not recognized.");
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
	
	private void addAnimalToEnclosure(String animal, String name, char gender, int age, int health, int enclosureIndex){ 
		Enclosure targetEnclosure = enclosures.get(enclosureIndex);
		try{
			switch (animal) {
				case "lion":
					targetEnclosure.addAnimal(new Lion(name, age, gender, health, targetEnclosure));
					break;
				case "tiger":
					targetEnclosure.addAnimal(new Tiger(name, age, gender, health, targetEnclosure));	
					break;
				case "gorilla":
					targetEnclosure.addAnimal(new Gorilla(name, age, gender, health, targetEnclosure));
					break;
				case "chimpanzee":
					targetEnclosure.addAnimal(new Chimpanzee(name, age, gender, health, targetEnclosure));
					break;
			}
		} catch (EnclosureFullException e){
			out.println("Cannot add " + animal + " to enclosure " + targetEnclosure);
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
}
