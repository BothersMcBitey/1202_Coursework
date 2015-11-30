package structures;

import java.util.ArrayList;

import animals.*;

public class Enclosure {

	private Foodstore foodstore;
	private ArrayList<Animal> animals;
	private int waste;
	
	public Enclosure(){
		foodstore = new Foodstore();
		animals = new ArrayList<Animal>();
		waste = 0;
	}
	
	public void addAnimal(Animal animalToAdd){
		if(animals.size() < 20){
			animals.add(animalToAdd);
		}
		//TODO: add code to handle full array
	}
	
	public void removeAnimal(Animal animalToRemove){
		if(animals.contains(animalToRemove)){
			animals.remove(animalToRemove);
		}
		//TODO: add code to handle missing animal
	}
	
	/*
	 *Removes specified amount of waste from enclosure, 
	 *sets waste to zero if removing more waste than is present
	 */
	public void removeWaste(int amount){
		if(waste >= amount){
			waste -= amount;
		} else {
			waste = 0;
		}
	}
	
	public void addWaste(int amount){
		waste += amount;
	}
	
	public int getWasteSize(){
		return waste;
	}
	
	public Foodstore getFoodstore() {
		return foodstore;
	}
	
	public int size(){
		return animals.size();
	}
	
	/*
	 * passes the month for each animal, removes animal
	 * if it died
	 */
	public void aMonthPasses(){
		for(Animal a : animals){
			if(!a.aMonthPasses()){
				animals.remove(a);
			}
		}
	}
}
