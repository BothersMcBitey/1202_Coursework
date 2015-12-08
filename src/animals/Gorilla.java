package animals;

import food.Food;
import main.Zoo;
import structures.Enclosure;
import structures.EnclosureFullException;
import zookeepers.PlayZookeeper;
import zookeepers.UnqualifiedZookeeperException;
import zookeepers.Zookeeper;

public class Gorilla extends Ape {

	private static final Species gorilla = new Species("gorilla", 9, 1, 32, new Food[] {Zoo.FRUIT, Zoo.ICE_CREAM});
	
	/*
	 * creates newborn gorilla
	 */
	public Gorilla(String name, char gender, Enclosure enclosure){
		this(name, 0, gender, 10, enclosure);
	}
	
	public Gorilla(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, health, enclosure, gorilla);
	}
	
	@Override
	public void treat(Zookeeper keeper) throws UnqualifiedZookeeperException{		
		if(keeper instanceof PlayZookeeper){
			painting();
		} else {
			throw new UnqualifiedZookeeperException();
		}
	}

	private void painting() {
		if(health + 4 >= 10){
			health += 4;
			Zoo.out.println(name + " did painting, gained " + 4 + " health");
		} else {
			int change = 10 - health;
			health += change;
			Zoo.out.println(name + " did painting, gained " + change + " health");
		}
	}
	
	@Override
	protected void giveBirth(String name, char gender) throws EnclosureFullException{
		enclosure.addAnimal(new Gorilla(name, 0, gender, 8, enclosure));
	}
}
