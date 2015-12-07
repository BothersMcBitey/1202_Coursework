package animals;

import structures.Enclosure;

public class Tiger extends BigCat {
	
	/*
	 * creates newborn Tiger of health 10
	 */
	public Tiger(String name, char gender, Enclosure enclosure) {
		this(name, 0, gender, 10, enclosure);
	}

	public Tiger(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, health, enclosure);
	}

	@Override
	public void stroke() {
		health += 3;
	}

}
