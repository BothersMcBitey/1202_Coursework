package zookeepers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import animals.Animal;
import food.Food;
import food.InsufficientFoodException;
import main.Zoo;
import structures.*;

public class Zookeeper {
	
	private final int MAX_TREATED_ANIMALS = 2;
	private final int MAX_FOOD_TRANSFERED = 20;
	
	private Enclosure assignedEnclosure;
	private Foodstore zooStore;
	private String name;

	public Zookeeper(String name, Enclosure assignedEnclosure, Foodstore zooStore){
		this.name = name;
		this.assignedEnclosure = assignedEnclosure;
		this.zooStore = zooStore;
	}

	public void aMonthPasses(){
		Animal[] animals = assignedEnclosure.getAnimals();
		//make sure there's animals left
		if(animals.length > 0){
			//move up to 20 units of food from zoo store to enclosure
			ArrayList<Food> requiredFoods = new ArrayList<Food>();
			HashMap<Food, Integer> amountNeeded =  new HashMap<>();
			for(Animal a : animals){
				for(Food f : a.eats){
					if(!requiredFoods.contains(f)){
						requiredFoods.add(f);
						amountNeeded.put(f, 1);
					} else {
						amountNeeded.put(f, amountNeeded.get(f) + 1);
					}
				}
			}
			for(Food f : requiredFoods){
				int amountToTake = (MAX_FOOD_TRANSFERED * amountNeeded.get(f)) / requiredFoods.size();
				int amountTaken = 0;
				try {
					zooStore.takeFood(f, amountToTake);
					amountTaken = amountToTake;
				} catch (FoodNotFoundException e) {
					// this shouldn't be possible, so will just print stack trace
					e.printStackTrace();
				} catch (InsufficientFoodException e) {
					// try again, only taking amount left
					try {
						zooStore.takeFood(f, e.getAmountLeft());
						amountTaken = e.getAmountLeft();
					} catch (FoodNotFoundException | InsufficientFoodException e1) {
						//if this fails something REALLY bad just happened, so just print the stacktrace
						e1.printStackTrace();
					}
				}
				assignedEnclosure.getFoodstore().addFood(f, amountTaken);
				Zoo.out.println(name + " moving " + amountTaken + " " + f.getName() + " from zoostore to " + assignedEnclosure.getName() + " store");
			}
			

			//remove up to 20 units of waste
			assignedEnclosure.removeWaste(20);
			Zoo.out.println(name + " removing 20 waste from " + assignedEnclosure.getName());
			
			//treat up to 2 animals
			Random r = new Random();
			int animalsToTreat = r.nextInt(MAX_TREATED_ANIMALS);
			for(int i = 0; i < animalsToTreat; i++){
				//add loop to catch exception
				int j = r.nextInt(animals.length);
				animals[j].treat(this);
				Zoo.out.println(name + " treating " + animals[j].getName());
			}
		} else {
			Zoo.out.println(name + " not doing anything because all the animals are dead");
		}
	}
	
	public String getName(){
		return name;
	}
}
