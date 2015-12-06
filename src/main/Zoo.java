package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	
	//Static stuff ======================================================
	
	public static HashSet<Food> foods;
	
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
		foods.add(HAY);
		foods.add(STEAK);
		foods.add(FRUIT);
		foods.add(CELERY);
		foods.add(FISH);
		foods.add(ICE_CREAM);
	}
	
	//instance stuff ========================================================

	private ArrayList<Enclosure> enclosures;
	private ArrayList<Zookeeper> zookeepers;
	private Foodstore store;
	private String name;
	
	public Zoo(String config){
		store = new Foodstore();
		if(foods == null) initializeFoods();
		if(config != null) initializeZoo(config);
	}
	
	private void initializeZoo(String config) {
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
				switch (property[0]) {
					case "zoo":
						System.out.println("Building " + parameters[0]);
						name = parameters[0];
						break;
						
					case "steak":case "hay":case "fruit":case "celery":case "fish":case "icecream":
						addFoodToLastStore(property[0], Integer.parseInt(parameters[0]));
						break;
						
					case "enclosure":
						Enclosure tempEnclosure = new Enclosure();
						tempEnclosure.addWaste(Integer.parseInt(parameters[0]));
						enclosures.add(tempEnclosure);
						break;
						
					case "lion":case "tiger":case "gorilla":case "chimpanzee":
						addAnimalToEnclosure(property[0], parameters[0].charAt(0), Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]));
						break;
						
					case "zookeeper":
						zookeepers.add(new Zookeeper(enclosures.get(enclosures.size() -1), store));
						break;
					case "playZookeeper":
						zookeepers.add(new PlayZookeeper(enclosures.get(enclosures.size() -1), store));
						break;
					case "physioZookeeper":
						zookeepers.add(new PhysioZookeeper(enclosures.get(enclosures.size() -1), store));
						break;
						
					default:
						System.out.println("Tag \"" + property[0] + "\" not recognized.");
						break;
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void addAnimalToEnclosure(String animal, char gender, int age, int health, int enclosure){
		Enclosure tempEnclosure = enclosures.get(enclosure);
		try{
			switch (animal) {
			case "lion":
				tempEnclosure.addAnimal(new Lion(age, gender, health));
				break;
			case "tiger":
				tempEnclosure.addAnimal(new Tiger(age, gender, health));	
				break;
			case "gorilla":
				tempEnclosure.addAnimal(new Gorilla(age, gender, health));
				break;
			case "chimpanzee":
				tempEnclosure.addAnimal(new Chimpanzee(age, gender, health));
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
		}else{
			targetStore = enclosures.get(enclosures.size() -1).getFoodstore();
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
			case "celerey":
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
		while(true){
			aMonthPasses();
			try{
				Thread.sleep(500);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	private void aMonthPasses(){
		for(Enclosure e : enclosures){
			e.aMonthPasses();
		}
		for(Zookeeper z : zookeepers){
			z.aMonthPasses();
		}
		for(Food f : foods){
			store.addFood(f, 20);
		}
	}
}
