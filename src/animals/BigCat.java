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
		stroke(1);
	}
	
	/*all cats are stroked, but they react differently to it, so
	 * they should pass how much health it gives them by overriding treat
	 */
	public void stroke(int healthBonus){
		if(health + healthBonus >= 10){
			health += healthBonus;
			Zoo.out.println(name + " was stroked, gained " + healthBonus + " health");
		} else {
			int change = 10 - health;
			health += change;
			Zoo.out.println(name + " was stroked, gained " + change + " health");
		}
	}
}
