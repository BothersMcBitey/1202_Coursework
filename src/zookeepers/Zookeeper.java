package zookeepers;

import java.util.ArrayList;
import java.util.Random;

import animals.Animal;
import food.Food;
import food.InsufficientFoodException;
import structures.*;

public class Zookeeper {
	
	private final int MAX_TREATED_ANIMALS = 2;
	private final int MAX_FOOD_TRANSFERED = 20;
	
	private Enclosure assignedEnclosure;
	private Foodstore zooStore;

	public Zookeeper(Enclosure assignedEnclosure, Foodstore zooStore){
		this.assignedEnclosure = assignedEnclosure;
		this.zooStore = zooStore;
	}

	public void aMonthPasses(){
		//move up to 20 units of food from zoo store to enclosure
		Animal[] animals = assignedEnclosure.getAnimals();
		ArrayList<Food> requiredFoods = new ArrayList<Food>(); 
		for(Animal a : animals){
			for(Food f : a.eats){
				if(!requiredFoods.contains(f)){
					requiredFoods.add(f);
				}
			}
		}
		int amountToTake = MAX_FOOD_TRANSFERED / requiredFoods.size();
		for(Food f : requiredFoods){
			try {
				zooStore.takeFood(f, amountToTake);
			} catch (FoodNotFoundException e) {
				// this shouldn't be possible, so will just print stack trace
				e.printStackTrace();
			} catch (InsufficientFoodException e) {
				// try again, only taking amount left
				try {
					zooStore.takeFood(f, e.getAmountLeft());
				} catch (FoodNotFoundException | InsufficientFoodException e1) {
					//if this fails something REALLY bad just happened, so just print the stacktrace
					e1.printStackTrace();
				}
			}
			assignedEnclosure.getFoodstore().addFood(f, amountToTake);
		}
		//remove up to 20 units of waste
		assignedEnclosure.removeWaste(20);
		//treat up to 2 animals
		Random r = new Random();
		int animalsToTreat = r.nextInt(MAX_TREATED_ANIMALS);
		for(int i = 0; i < animalsToTreat; i++){
			//add loop to catch exception
			animals[r.nextInt(animals.length)].treat(this);
		}
	}
}
