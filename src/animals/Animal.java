package animals;

import food.Food;
import main.Zoo;
import structures.Enclosure;
import zookeepers.Zookeeper;

public abstract class Animal {

	private String name;
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
	
	public String getName(){
		return name;
	}
	
	/**
	 * returns false if animal died of hunger or old age
	 */
	public boolean aMonthPasses(){
		decreaseHealth();
		eat();
		incrAge();
		Zoo.out.println(name + " is now " + age + " months old and has " + health + " health");
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
				enclosure.getFoodstore().takeFood(food, 1);
				Zoo.out.println(name + " eating " + food.getName());
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
	
	public void treat(Zookeeper keeper){
		health++;
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
}
