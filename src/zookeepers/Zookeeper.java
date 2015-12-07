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
			
			//move the food as needed
			moveFood(animals);

			//remove up to 20 units of waste
			assignedEnclosure.removeWaste(20);
			Zoo.out.println(name + " removing 20 waste from " + assignedEnclosure.getName());
			
			//treat up to 2 animals
			Random r = new Random();
			int animalsToTreat = r.nextInt(MAX_TREATED_ANIMALS);
			for(int i = 0; i < animalsToTreat; i++){
				//add loop to catch exception
				int j = r.nextInt(animals.length);
				try {
					animals[j].treat(this);
					Zoo.out.println(name + " treating " + animals[j].getName());
				} catch (UnqualifiedZookeeperException e) {
					Zoo.out.println(name + " tried to treat " + animals[j].getName() + ", but " + name + " is unable to");
				}
				
			}
		} else {
			Zoo.out.println(name + " not doing anything because all the animals are dead");
		}
	}
	
	/*food moving code put here to make it easier to read aMonthPasses() 
	 */
	private void moveFood(Animal[] animals){
		//move up to 20 units of food from zoo store to enclosure
		ArrayList<Food> requiredFoods = new ArrayList<Food>();
		//used to keep track of how many animals in each enclosure needs a certain food
		HashMap<Food, Integer> amountNeeded =  new HashMap<>();
		
		//check which foods are needed and how much
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
			//all required to move food from zoostore to enclosure
			int amountToTake = (MAX_FOOD_TRANSFERED * amountNeeded.get(f)) / requiredFoods.size();
			int amountTaken = 0;
			int spaceAvailable; //space left in enclosure foodstore's bucket
			int amountLeft; //amount of food left in zoo foodstore's bucket
			
			try {
				spaceAvailable = assignedEnclosure.getFoodstore().getSpace(f);
			} catch (FoodNotFoundException e) {
				//in case the enclosure doesn't have the required bucket
				assignedEnclosure.getFoodstore().addBucket(f, 20);
				spaceAvailable = 20;
			}
			
			//actually move the food
			try {
				amountLeft = zooStore.getRemaining(f);
				
				//if there's enough food left in the zoostore
				if(amountLeft >= amountToTake){
					//and there's space in the enclosure
					if(spaceAvailable >= amountToTake){
						//move ALL THE FOOD
						zooStore.takeFood(f, amountToTake);
						amountTaken = amountToTake;
					} else {
						//take enough food to fill the enclosure
						zooStore.takeFood(f, spaceAvailable);
						amountTaken = spaceAvailable;
					}
				} else {
					//take the food that's available
					zooStore.takeFood(f, amountLeft);
					amountTaken = amountLeft;
				}
			} catch (FoodNotFoundException e) {
				e.printStackTrace();
			} catch (InsufficientFoodException e) {
				e.printStackTrace();
			}
			
			//put the food taken in the enclosure
			assignedEnclosure.getFoodstore().addFood(f, amountTaken);
			Zoo.out.println(name + " moving " + amountTaken + " " + f.getName() + " from zoostore to " + assignedEnclosure.getName() + " store");
		}
	}
	
	public String getName(){
		return name;
	}
}
