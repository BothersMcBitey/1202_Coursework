package animals;

import java.util.Random;

import food.Food;
import food.InsufficientFoodException;
import main.Zoo;
import structures.Enclosure;
import structures.EnclosureFullException;
import structures.FoodNotFoundException;
import zookeepers.UnqualifiedZookeeperException;
import zookeepers.Zookeeper;

public abstract class Animal {
	
	protected final Species species;
	private final char GENDER;
	private boolean pregnant = false;
	private int monthsPregnant;
	
	protected String name;
	private int age;
	protected int health;
	protected Enclosure enclosure;
	
	protected Animal(String name, int age, char gender, int health, Enclosure enlosure, Species species){
		this.name = name;
		this.age = age;
		//makes sure gender can only be 'm' or 'f'
		if(gender == 'm' || gender == 'M'){
			this.GENDER = 'm';
		} else {
			if(gender == 'f' || gender == 'F'){
				this.GENDER = 'f';	
			} else {
				this.GENDER = 'm';
			}
		}
		this.health = health;
		setEnclosure(enlosure);
		this.species = species;
	}
	
	//returns false if animal died of hunger or old age
	public boolean aMonthPasses() throws EnclosureFullException{
		breed();
		decreaseHealth();
		eat();
		incrAge();
		Zoo.out.println(name + " is now " + age + " months old and has " + health + " health");
		//Check if the animal died
		if(getAge() > species.getLIFE_EXPECTANCY()){
			Zoo.out.println(name + " has died of old age");
			return false;
		} else {
			if(health <= 0){
				Zoo.out.println(name + " has died of bad health");
				return false;
			} else {
				return true;
			}
		}		
	}
	
	/* Checks the enclosure for other animals of the same type and of the opposite gender.
	 * If one is found, tries to breed.
	 */
	public void breed() throws EnclosureFullException{
		/* only one gender needs to try reproduction, and only allowing females to
		 * limits how much you need to affect other objects in the breed() method  
		 */
		if(GENDER == 'f'){
			Random r = new  Random();
			if(pregnant){
				monthsPregnant++;
				Zoo.out.println(name + " is now " + monthsPregnant + " months pregnant");
				//check if it should give birth
				if(monthsPregnant >= species.getGESTATION_PERIOD()){
					Zoo.out.println(name + " is giving birth");
					//randomize number of children
					int litterSize = r.nextInt(species.getLITTER_MAX_SIZE()) + 1;
					//for each child
					for(int i = 0; i < litterSize; i++){
						char gender;
						String name;
						
						//get a name and gender
						if(r.nextBoolean()){
							gender = 'm';
							name = Zoo.in.getInput("Enter a name for the male " + species.getNAME() + " infant: "); 
						} else {
							gender = 'f';
							name = Zoo.in.getInput("Enter a name for the female " + species.getNAME() + " infant: "); 
						}

						pregnant = false;
						//add to enclsoure
						giveBirth(name, gender);
					}					
				}
			//if not pregnant, try to be
			} else {
				for(Animal a : enclosure.getAnimals()){
					if(a.getSpecies() == this.species){
						if(a.getGender() == 'm'){
							//Make sure the animal isn't already pregnant, and so there isn't a 100% chance of becoming pregnant
							if(!pregnant && r.nextBoolean()){
								Zoo.out.println(name + " mated with " + a.getName() + " and is now pregnant");
								pregnant = true;
								monthsPregnant = 0;
							}
						}
					}
				}
			}
		}
				
	}
	
	/*
	 * checks if the enclosure's foodstore contains any
	 * food the animal can eat, if it does adds health and waste
	 * as appropriate
	 */
	public void eat(){
		for(Food food : species.getEATS()){
			try {
				//try to take some food from the enclosure
				enclosure.getFoodstore().takeFood(food, 1);
				//work out how much health the animal gains from eating
				//tempHealth declared because using instance health in if statement was causing negative health changes
				int tempHealth = health + food.getEnergy();
				if(tempHealth >= 10){
					Zoo.out.println(name + " ate " + food.getName() + ", gained " + (10 - health) + " health");
					health = 10;
				} else {
					health += food.getEnergy();
					Zoo.out.println(name + " ate " + food.getName() + ", gained " + food.getEnergy() + " health");
				}
				//make the animal poop
				enclosure.addWaste(food.getWaste());
			//if the animal couldn't get the food it wanted, let the user know
			} catch (FoodNotFoundException e) {
				Zoo.out.println(name + " tried to eat " + food.getName() + ", but " + enclosure.getName() + " doesn't stock it");
			} catch (InsufficientFoodException e) {
				Zoo.out.println(name + " tried to eat " + food.getName() + ", but there was none left");
			}
		}
	}
	
	//checks if eats[] contains the given food
	public boolean canEat(Food food){
		for(Food f : species.getEATS()){
			if(food.equals(f)) return true;
		}
		return false;
	}

	public void decreaseHealth(){
		health -= 2;
	}
	
	/* takes Zookeeper param and throws exception so specialised animals can 
	 * stop unqualified keepers from treating them
	 */
	public void treat(Zookeeper keeper) throws UnqualifiedZookeeperException{
		health++;
	}
	
	protected void incrAge(){
		age++;
	}
	
	//abstract because each animal will need to call different constructors
	protected abstract void giveBirth(String name, char gender) throws EnclosureFullException;
	
	//Setters ===============================================
	
	public void setEnclosure(Enclosure enclosure){
		this.enclosure = enclosure;
	}
	
	//Getters ===============================================
	
	public Species getSpecies(){
		return species;
	}
	
	public int getAge(){
		return age;
	}
	
	public char getGender(){
		return GENDER;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isPregnant(){
		return pregnant;
	}
}
