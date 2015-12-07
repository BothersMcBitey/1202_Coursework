package animals;

import structures.Enclosure;
import zookeepers.PlayZookeeper;
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
	public void treat(Zookeeper keeper){
		if(keeper instanceof PlayZookeeper){
			playChase();
		} else {
			//throw an exception
		}
	}

	private void playChase() {
		health += 4;
	}

}
