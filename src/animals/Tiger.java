package animals;

public class Tiger extends BigCat {
	
	/*
	 * creates newborn Tiger of health 10
	 */
	public Tiger(char gender) {
		super(0, gender, 10);
	}

	public Tiger(int age, char gender, int health) {
		super(age, gender, health);
	}

	@Override
	public void stroke() {
		health += 3;
	}

}
