package animals;

import java.util.Random;

import main.Zoo;
import structures.Enclosure;
import structures.EnclosureFullException;
import zookeepers.Zookeeper;
import food.Food;

public class Tiger extends BigCat {
	
	private static final Species tiger = new Species("tiger", 3, 6, 24, new Food[] {Zoo.STEAK, Zoo.CELERY});
	
	/*
	 * creates newborn Tiger of health 10
	 */
	public Tiger(String name, char gender, Enclosure enclosure) {
		this(name, 0, gender, 10, enclosure);
	}

	public Tiger(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, health, enclosure, tiger);
	}
	
	@Override
	public void treat(Zookeeper keeper) {
		super.stroke(3);
	}

	@Override
	protected void giveBirth(String name, char gender) throws EnclosureFullException{
		enclosure.addAnimal(new Tiger(name, 0, gender, 8, enclosure));
	}
}
