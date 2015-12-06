package animals;

import food.Food;
import main.Zoo;
import zookeepers.Zookeeper;

public abstract class Ape extends Animal {

	public Ape(int age, char gender, int health, int lifeExpectancy) {
		super(age, gender, new Food[] {Zoo.FRUIT, Zoo.ICE_CREAM}, health, lifeExpectancy);
	}
	
	@Override
	public void treat(Zookeeper keeper) {
		super.treat(keeper);
	}
}
