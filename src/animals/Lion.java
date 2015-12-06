package animals;

public class Lion extends BigCat {

	/*
	 * creates newborn lion of health 10
	 */
	public Lion(char gender) {
		super(0, gender, 10);
	}

	public Lion(int age, char gender, int health) {
		super(age, gender, health);
	}

	@Override
	public void stroke() {
		health += 2;
	}

}
