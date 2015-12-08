package animals;


import food.Food;
import main.Zoo;
import structures.Enclosure;
import structures.EnclosureFullException;
import zookeepers.Zookeeper;

public class Lion extends BigCat {
	
	private static final Species lion = new Species("lion", 3, 4, 24, new Food[] {Zoo.STEAK, Zoo.CELERY});

	/*
	 * creates newborn lion of health 10
	 */
	public Lion(String name, char gender, Enclosure enclosure) {
		this(name, 0, gender, 10, enclosure);
	}

	public Lion(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, health, enclosure, lion);
	}

	@Override
	public void treat(Zookeeper keeper) {
		super.stroke(2);
	}
	
	@Override
	protected void giveBirth(String name, char gender) throws EnclosureFullException{
		enclosure.addAnimal(new Lion(name, 0, gender, 8, enclosure));
	}
}
