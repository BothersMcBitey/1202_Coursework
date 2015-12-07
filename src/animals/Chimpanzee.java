package animals;

import main.Zoo;
import structures.Enclosure;
import zookeepers.PlayZookeeper;
import zookeepers.UnqualifiedZookeeperException;
import zookeepers.Zookeeper;

public class Chimpanzee extends Ape {

	/*
	 * creates newborn chimp
	 */
	public Chimpanzee(String name, char gender, Enclosure enclosure){
		this(name, 0, gender, 10, enclosure);
	}
	
	public Chimpanzee(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, health, 24, enclosure);
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

}
