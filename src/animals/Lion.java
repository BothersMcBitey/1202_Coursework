package animals;


import structures.Enclosure;
import zookeepers.Zookeeper;

public class Lion extends BigCat {

	/*
	 * creates newborn lion of health 10
	 */
	public Lion(String name, char gender, Enclosure enclosure) {
		super(name, 0, gender, 10, enclosure);
	}

	public Lion(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, health, enclosure);
	}

	@Override
	public void treat(Zookeeper keeper) {
		super.stroke(2);
	}
}
