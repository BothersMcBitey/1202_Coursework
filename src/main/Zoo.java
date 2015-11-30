package main;

import java.util.ArrayList;
import java.util.HashSet;

import structures.*;
import zookeepers.*;
import food.*;

public class Zoo {
	
	//Static stuff
	
	public static HashSet<Food> foods;
	
	private static void initializeFoods(){
		foods = new HashSet<Food>();
		foods.add(new Food("hay", 1, 4));
		foods.add(new Food("steak", 3, 4));
		foods.add(new Food("fruit", 2, 3));
		foods.add(new Food("celery", 0, 1));
		foods.add(new Food("fish", 3, 2));
		foods.add(new Food("ice cream", 1, 3));
	}
	
	//instance stuff

	private ArrayList<Enclosure> enclosures;
	private ArrayList<Zookeeper> zookeepers;
	private Foodstore store;
	
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
		for(String f : foods){
			store.addFood(f, 20);
		}
	}
}
