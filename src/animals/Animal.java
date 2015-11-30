package animals;

import java.util.HashSet;

import food.Food;
import structures.Enclosure;

public abstract class Animal {

	private int age;
	private char gender;
	public Food[] eats;
	protected int health;
	private int lifeExpectancy;
	private Enclosure enclosure;
	
	protected Animal(int age, char gender, Food[] eats, int health, int lifeExpectancy){
		this.age = age;
		this.gender = gender;
		this.eats = eats;
		this.health = health;
		this.lifeExpectancy = lifeExpectancy;
	}
	
	public void setEnclosure(Enclosure enclosure){
		this.enclosure = enclosure;
	}
	
	public int getAge(){
		return age;
	}
	
	/**
	 * increase age by 1
	 */
	protected void incrAge(){
		age++;
	}
	
	public char getGender(){
		return gender;
	}
	
	public int getLifeExpectancy(){
		return lifeExpectancy;
	}
	
	/**
	 * checks if eats[] contains the given food
	 */
	public boolean canEat(Food food){
		for(Food f : eats){
			if(food.equals(f)) return true;
		}
		return false;
	}
	
	/**
	 * checks if the enclosure's foodstore contains any
	 * food the animal can eat, if it does adds health and waste
	 * as appropriate
	 */
	public void eat(){
		for(Food food : eats){
			try {
				enclosure.getFoodstore().takeFood(food);
				if(health + food.getEnergy() > 10){
					health = 10;
				} else {
					health += food.getEnergy();
				}
				enclosure.addWaste(food.getWaste());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * reduces health by 2
	 */
	public void decreaseHealth(){
		health -= 2;
	}
	
	public void treat(){
		health++;
	}
	
	/**
	 * returns false if animal died of hunger or old age
	 */
	public abstract boolean aMonthPasses();
}