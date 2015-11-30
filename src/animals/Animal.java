package animals;

import structures.Enclosure;

public abstract class Animal {

	private int age;
	private char gender;
	private String[] eats;
	protected int health;
	private int lifeExpectancy;
	private Enclosure enclosure;
	
	protected Animal(int age, char gender, String[] eats, int health, int lifeExpectancy){
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
	
	//increase age by 1
	protected void incrAge(){
		age++;
	}
	
	public char getGender(){
		return gender;
	}
	
	public int getLifeExpectancy(){
		return lifeExpectancy;
	}
	
	/*
	 * checks if eats[] contains the given food
	 */
	public boolean canEat(String food){
		for(String s : eats){
			if(food.equals(s)) return true;
		}
		return false;
	}
	
	/*
	 * checks if the enclosure's foodstore contains any
	 * food the animal can eat, if it does adds health and waste
	 * ass apropriate
	 */
	public void eat(){
		for(String s : eats){
			if(enclosure.getFoodstore().takeFood(s)){
				switch(s){
					case "hay" : 
						health += 1;
						enclosure.addWaste(4);
						break;
					case "steak" : 
						health += 3;
						enclosure.addWaste(4);
						break;
					case "fruit" : 
						health += 2;
						enclosure.addWaste(3);
						break;
					case "celery" : 
						health += 0;
						enclosure.addWaste(1);
						break;
					case "fish" : 
						health += 3;
						enclosure.addWaste(2);
						break;
					case "ice cream" : 
						health += 1;
						enclosure.addWaste(3);
						break;
				}
			}
		}
	}
	
	/*
	 * reduces health by 2
	 */
	public void decreaseHealth(){
		health -= 2;
	}
	
	public void treat(){
		health++;
	}
	
	/*
	 * returns false if animal died of hunger or old age
	 */
	public abstract boolean aMonthPasses();
}
