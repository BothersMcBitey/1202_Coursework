package animals;

import food.Food;
import main.Zoo;
import structures.Enclosure;
import structures.EnclosureFullException;
import zookeepers.PlayZookeeper;
import zookeepers.UnqualifiedZookeeperException;
import zookeepers.Zookeeper;

public class Chimpanzee extends Ape {
	
	private static final Species chimpanzee = new Species("chimpanzee", 7, 1, 24, new Food[] {Zoo.FRUIT, Zoo.ICE_CREAM});

	/*
	 * creates newborn chimp
	 */
	public Chimpanzee(String name, char gender, Enclosure enclosure){
		this(name, 0, gender, 10, enclosure);
	}
	
	public Chimpanzee(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, health, enclosure, chimpanzee);
	}
	
	@Override
	public void treat(Zookeeper keeper) throws UnqualifiedZookeeperException{
		if(keeper instanceof PlayZookeeper){
			playChase();
		} else {
			throw new UnqualifiedZookeeperException();
		}
	}

	private void playChase() {
		if(health + 4 >= 10){
			health += 4;
			Zoo.out.println(name + " played chase, gained " + 4 + " health");
		} else {
			int change = 10 - health;
			health += change;
			Zoo.out.println(name + " played chase, gained " + change + " health");
		}
	}

	@Override
	protected void giveBirth(String name, char gender) throws EnclosureFullException{
		enclosure.addAnimal(new Chimpanzee(name, 0, gender, 8, enclosure));
	}
}
