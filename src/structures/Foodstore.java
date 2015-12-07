package structures;

import java.util.HashMap;

import food.*;

public class Foodstore {

	//links each food with its own bucket
	private HashMap<Food, Bucket> store;
	
	public Foodstore(){	
		store = new HashMap<Food, Bucket>();
	}
	
	/* if the requested food isn't present throw an exception,
	 * if it is reduce the stored amount by the amount requested
	 */
	public void takeFood(Food food, int amount) throws FoodNotFoundException, InsufficientFoodException{
		if(store.containsKey(food)){
			store.get(food).removeFood(amount);
		} else {
			throw new FoodNotFoundException(food);
		}
	}
	
	public void addFood(Food food, int amount){
		if(store.containsKey(food)){
			store.get(food).addFood(amount);
		} else {
			addBucket(food, amount);
			addFood(food, amount);
		}
	}
	
	//get how much food of a certain type is left
	public int getRemaining(Food food) throws FoodNotFoundException{
		if(store.containsKey(food)){
			return store.get(food).getAmount();
		} else {
			throw new FoodNotFoundException(food);
		}
	}
	//get how much space is left in a bucket
	public int getSpace(Food food) throws FoodNotFoundException{
		if(store.containsKey(food)){
			return store.get(food).getCapacity() - store.get(food).getAmount();
		} else {
			throw new FoodNotFoundException(food);
		}
	}
	
	public void addBucket(Food food, int max){
		store.put(food, new Bucket(max));
	}	
	
	public boolean contains(Food food){
		return store.containsKey(food);
	}
}
