package animals;

import zookeepers.PlayZookeeper;
import zookeepers.Zookeeper;

public class Gorilla extends Ape {

	/*
	 * creates newborn gorilla
	 */
	public Gorilla(char gender){
		this(0, gender, 10);
	}
	
	public Gorilla(int age, char gender, int health) {
		super(age, gender, health, 32);
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
