package animals;

public abstract class Ape extends Animal {

	public Ape(int age, char gender, int health, int lifeExpectancy) {
		super(age, gender, new String[] {"fruit", "ice cream"}, health, lifeExpectancy);
	}

	@Override
	public boolean aMonthPasses() {
		decreaseHealth();
		eat();
		incrAge();
		if(health <= 0 || getAge() > getLifeExpectancy()){
			return false;
		} else {
			return true;
		}
	}
}
