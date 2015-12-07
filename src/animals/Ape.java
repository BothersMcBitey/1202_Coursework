package animals;

import food.Food;
import main.Zoo;
import structures.Enclosure;
import zookeepers.UnqualifiedZookeeperException;
import zookeepers.Zookeeper;

public abstract class Ape extends Animal {

	public Ape(String name, int age, char gender, int health, int lifeExpectancy, Enclosure enclosure) {
		super(name, age, gender, new Food[] {Zoo.FRUIT, Zoo.ICE_CREAM}, health, lifeExpectancy, enclosure);
	}
	
	@Override
	public void treat(Zookeeper keeper) throws UnqualifiedZookeeperException {
		super.treat(keeper);
	}
}
