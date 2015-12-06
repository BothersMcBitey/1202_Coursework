package animals;

import food.Food;
import main.Zoo;
import zookeepers.Zookeeper;

public abstract class BigCat extends Animal {
	
	public BigCat(int age, char gender, int health) {
		super(age, gender, new Food[] {Zoo.STEAK, Zoo.CELERY}, health, 24);
	}
	
	@Override
	public void treat(Zookeeper keeper){
		stroke();
	}
	
	public abstract void stroke();
}
