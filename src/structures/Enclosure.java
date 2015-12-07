package structures;

import java.util.ArrayList;

import javax.xml.transform.Templates;

import animals.*;
import food.Food;
import main.Zoo;

public class Enclosure {

	private Foodstore foodstore;
	private ArrayList<Animal> animals;
	private int waste;
	private String name;
	
	public Enclosure(String name){
		foodstore = new Foodstore();
		animals = new ArrayList<Animal>();
		waste = 0;
		this.name = name;
	}
	
	public void addAnimal(Animal animalToAdd) throws EnclosureFullException {
		if(animals.size() < 20){
			animals.add(animalToAdd);
			for(Food f : animalToAdd.eats){
				if(!foodstore.contains(f)){
					foodstore.addBucket(f, 5);
				}
			}
		} else {
			throw new EnclosureFullException();
		}
	}
	
	public void removeAnimal(Animal animalToRemove) throws AnimalNotFoundException{
		if(animals.contains(animalToRemove)){
			animals.remove(animalToRemove);
		} else{
			throw new AnimalNotFoundException();
		}
	}
	
	public Animal[] getAnimals(){
		return animals.toArray(new Animal[animals.size()]);
	}
	
	/**
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
	
	public String getName(){
		return name;
	}
	
	/**
	 * passes the month for each animal, removes animal
	 * if it died
	 */
	public void aMonthPasses(){
		Animal[] tempAnimals = new Animal[0]; 
	    tempAnimals =  animals.toArray(tempAnimals);
		for(Animal a : tempAnimals){
			if(!a.aMonthPasses()){
				animals.remove(a);
				Zoo.out.println(name + " removing " + a.getName() + " because it died");
			}
		}
		Zoo.out.println(name + " has " + waste + " loads of wate in it");
	}
}
