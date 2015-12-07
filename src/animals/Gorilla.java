package animals;

import structures.Enclosure;
import zookeepers.PlayZookeeper;
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
	public void treat(Zookeeper keeper){		
		if(keeper instanceof PlayZookeeper){
			painting();
		} else {
			//throw an exception
		}
	}

	private void painting() {
		health += 4;
	}
}
