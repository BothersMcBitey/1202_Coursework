package animals;

import java.util.HashSet;

import food.Food;
import main.Zoo;

public abstract class BigCat extends Animal {
	
	public BigCat(int age, char gender, int health) {
		super(age, gender, new Food[] {Zoo.STEAK, Zoo.CELERY}, health, 24);
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
	
	@Override
	public void treat(){
		stroked();
	}
	
	public abstract void stroked();
}
