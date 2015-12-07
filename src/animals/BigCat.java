package animals;

import food.Food;
import main.Zoo;
import structures.Enclosure;
import zookeepers.Zookeeper;

public abstract class BigCat extends Animal {
	
	public BigCat(String name, int age, char gender, int health, Enclosure enclosure) {
		super(name, age, gender, new Food[] {Zoo.STEAK, Zoo.CELERY}, health, 24, enclosure);
	}
	
	@Override
	public void treat(Zookeeper keeper){
		stroke();
	}
	
	public abstract void stroke();
}
