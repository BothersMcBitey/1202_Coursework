package structures;

import java.util.HashMap;

import food.*;

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
			addBucket(food, amount);
			addFood(food, amount);
		}
	}
	
	public int getRemaining(Food food) throws FoodNotFoundException{
		if(store.containsKey(food)){
			return store.get(food).getAmount();
		} else {
			throw new FoodNotFoundException(food);
		}
	}
	
	public void addBucket(Food food, int max){
		store.put(food, new Bucket(max));
	}
	
	/**
	 * if the requested food isn't present throw an exception,
	 * if it is reduce the stored amount by the amount requested
	 * @throws FoodNotFoundException 
	 * @throws InsufficientFoodException 
	 */
	public void takeFood(Food food, int amount) throws FoodNotFoundException, InsufficientFoodException{
		if(store.containsKey(food)){
			store.get(food).removeFood(amount);
		} else {
			throw new FoodNotFoundException(food);
		}
	}
}
