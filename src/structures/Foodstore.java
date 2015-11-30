package structures;

import java.util.HashMap;

import food.*;
import main.Zoo;

public class Foodstore {

	private HashMap<Food, Bucket> store;
	
	public Foodstore(){	
		store = new HashMap<Food, Bucket>();
	}
	
	public void addFood(String food, int amount){
		
	}
	
	/*
	 * if the requested food isn't present return false,
	 * if it is reduce the stored amount by 1 and return true
	 */
	public boolean takeFood(String food){
		if(store.containsKey(food)){
			store.put(food, store.get(food) - 1);
			return true;
		} else {
			return false;
		}
	}
}
