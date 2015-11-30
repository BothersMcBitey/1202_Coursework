package main;

import java.util.ArrayList;
import java.util.HashSet;

import structures.*;
import zookeepers.*;
import food.*;

public class Zoo {
	
	//Static stuff
	
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
	
	//instance stuff

	private ArrayList<Enclosure> enclosures;
	private ArrayList<Zookeeper> zookeepers;
	private Foodstore store;
	
	public Zoo(){
		initializeFoods();
		store = new Foodstore();
		for(Food f : foods){
			store.addBucket(f, 20);
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
