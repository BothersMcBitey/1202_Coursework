package animals;

import food.Food;
import food.InsufficientFoodException;
import main.Zoo;
import structures.Enclosure;
import structures.FoodNotFoundException;
import zookeepers.UnqualifiedZookeeperException;
import zookeepers.Zookeeper;

public abstract class Animal {

	protected String name;
	private int age;
	private char gender;
	public Food[] eats;
	protected int health;
	private int lifeExpectancy;
	private Enclosure enclosure;
	
	protected Animal(String name, int age, char gender, Food[] eats, int health, int lifeExpectancy, Enclosure enlosure){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.eats = eats;
		this.health = health;
		this.lifeExpectancy = lifeExpectancy;
		setEnclosure(enlosure);
	}
	
	//returns false if animal died of hunger or old age
	public boolean aMonthPasses(){
		decreaseHealth();
		eat();
		incrAge();
		Zoo.out.println(name + " is now " + age + " months old and has " + health + " health");
		//Check if the animal died
		if(getAge() > getLifeExpectancy()){
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
	
	/*
	 * checks if the enclosure's foodstore contains any
	 * food the animal can eat, if it does adds health and waste
	 * as appropriate
	 */
	public void eat(){
		for(Food food : eats){
			try {
				//try to take some food from the enclosure
				enclosure.getFoodstore().takeFood(food, 1);
				//work out how much health the animal gains from eating
				if(health + food.getEnergy() >= 10){
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
		for(Food f : eats){
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
	
	//Setters ===============================================
	
	public void setEnclosure(Enclosure enclosure){
		this.enclosure = enclosure;
	}
	
	//Getters ===============================================
	
	public int getAge(){
		return age;
	}
	
	public char getGender(){
		return gender;
	}
	
	public int getLifeExpectancy(){
		return lifeExpectancy;
	}
	
	public String getName(){
		return name;
	}
}
