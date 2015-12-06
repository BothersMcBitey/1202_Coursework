package animals;

import zookeepers.PlayZookeeper;
import zookeepers.Zookeeper;

public class Chimpanzee extends Ape {

	/*
	 * creates newborn chimp
	 */
	public Chimpanzee(char gender){
		this(0, gender, 10);
	}
	
	public Chimpanzee(int age, char gender, int health) {
		super(age, gender, health, 24);
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
