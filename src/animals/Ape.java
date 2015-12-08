package animals;

import structures.Enclosure;
import zookeepers.UnqualifiedZookeeperException;
import zookeepers.Zookeeper;

public abstract class Ape extends Animal {

	public Ape(String name, int age, char gender, int health, Enclosure enclosure, Species species) {
		super(name, age, gender, health, enclosure, species);
	}
	
	@Override
	public void treat(Zookeeper keeper) throws UnqualifiedZookeeperException {
		super.treat(keeper);
	}
}
