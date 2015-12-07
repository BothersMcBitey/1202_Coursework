package animals;

import main.Zoo;
import structures.Enclosure;
import zookeepers.PlayZookeeper;
import zookeepers.UnqualifiedZookeeperException;
import zookeepers.Zookeeper;

public class Gorilla extends Ape {

	/*
	 * creates newborn gorilla
	 */
	public Gorilla(String name, char gender, Enclosure enclosure){
		this(name, 0, gender, 10, enclosure);
	}
	
	public Gorilla(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, health, 32, enclosure);
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
}
