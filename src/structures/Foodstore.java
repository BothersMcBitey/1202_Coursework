package structures;

import java.util.HashMap;

import food.*;
import main.Zoo;

public class Foodstore {

	private HashMap<Food, Bucket> store;
	
	public boolean contains(Food food){
		return store.containsKey(food);
	}
	
	public Foodstore(){	
		store = new HashMap<Food, Bucket>();
	}
	
	public void addFood(Food food, int amount){
		if(store.containsKey(food)){
			store.get(food).addFood(amount);
		} else {
			//throw exception later
		}
	}
	
	public void addBucket(Food food, int max){
		store.put(food, new Bucket(max));
	}
	
	/**
	 * if the requested food isn't present return false,
	 * if it is reduce the stored amount by 1 and return true
	 */
	public void takeFood(Food food){
		if(store.containsKey(food)){
			store.get(food).removeFood(1);
		} else {
			//throw
		}
	}
	
	/**
	 * same as above but with string in case
	 * you don't have access to the original 
	 * food object
	 */
	public Food takeFood(String foodName) throws Exception{
		for(Food food : store.keySet()){
			if(food.getName().equals(foodName)){
				store.get(food).removeFood(1);
				return food;
			}
		}
		//add actual exeption
		throw new Exception();
	}
}
