package structures;

import java.util.ArrayList;

import animals.*;
import food.Food;
import main.Zoo;

public class Enclosure {

	private final int MAX_SIZE = 4;
	
	private Foodstore foodstore;
	private ArrayList<Animal> animals;
	private int waste;
	private String name;
	private Zoo parentZoo;
	
	public Enclosure(String name, Zoo parentZoo){
		foodstore = new Foodstore();
		animals = new ArrayList<Animal>();
		waste = 0;
		this.name = name;
		this.parentZoo = parentZoo;
	}
	
	/* passes the month for each animal, removes animal
	 * if it died
	 */
	public void aMonthPasses(){
		/*create temporary array of animals, because removing dead animals from the
		 * list while iterating through it causes an exception  
		 */
		Animal[] tempAnimals = new Animal[0]; 
	    tempAnimals =  animals.toArray(tempAnimals);
		for(Animal a : tempAnimals){
			//gets the animal to do it's stuff for the month, and checks of it died
			try {
				if(!a.aMonthPasses()){
					animals.remove(a);
					Zoo.out.println(name + " removing " + a.getName() + " because it died");
				}
				Zoo.out.println();
			} catch (EnclosureFullException e) {
				//if the animal gave birth but the enclosure is full
				findAlternativeEnclosure(e);
			}
		}
		Zoo.out.println(name + " has " + waste + " loads of wate in it");
	}
	
	/*try to find somewhere else for the newborn animal to stay in, if all
	 * enclosures are full it gets sent 'upstate'
	 */
	private void findAlternativeEnclosure(EnclosureFullException e) {
		Enclosure[] enclosures = parentZoo.getEnclosures();
		int fullEnclosures = 0;
		boolean success = false;
		
		Zoo.out.println("WARNING: " + name + " is full, where should we send the infant?");
		//display non-empty enclosures
		Zoo.out.println("Available enclosures:");
		for(Enclosure enc : enclosures){
			if(enc.isFull()){
				fullEnclosures++;
			} else {
				Zoo.out.println(enc.getName());
			}
		}
		//check if all enclosures are full
		if(fullEnclosures >= enclosures.length){
			Zoo.out.println("WARNING: All enclosures are full, sending infant to a farm upstate");
		} else {
			//if not, ask where to put the animal until you get a good answer
			while (!success){
				//get users choice
				String target = Zoo.in.getInput("Enter target enclosure:");
				//check if it exists
				for(Enclosure enc : enclosures){
					//if it does, mark as success and try add the animal
					if(enc.getName().equalsIgnoreCase(target)){
						success = true;
						try {
							enc.addAnimal(e.getAnimal());
							e.getAnimal().setEnclosure(enc);
						} catch (EnclosureFullException e1) {
						//if the chosen enclosure is full, mark as failure and try again
							success = false;
							Zoo.out.println("WARNING: " + target + " is also full, try again");
						}
					}
				}
				if(!success){
					Zoo.out.println("ERROR: " + target + " is not a valid enclosure, try again");
				}
			}
		}
	}

	public void addAnimal(Animal animalToAdd) throws EnclosureFullException {
		if(animals.size() < MAX_SIZE){
			animals.add(animalToAdd);
			//make sure the enclosure has buckets for the food the new animal eats
			for(Food f : animalToAdd.getSpecies().getEATS()){
				if(!foodstore.contains(f)){
					foodstore.addBucket(f, 5);
				}
			}
		} else {
			throw new EnclosureFullException(animalToAdd);
		}
	}
	
	public void removeAnimal(Animal animalToRemove) throws AnimalNotFoundException{
		if(animals.contains(animalToRemove)){
			animals.remove(animalToRemove);
		} else{
			throw new AnimalNotFoundException();
		}
	}
	
	/*Removes specified amount of waste from enclosure, 
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
	
	//getters=============================================
	
	public boolean isFull(){
		if(animals.size() >= MAX_SIZE){
			return true;
		} else {
			return false;
		}
	}
	
	public Animal[] getAnimals(){
		return animals.toArray(new Animal[animals.size()]);
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
}
