package structures;

import food.Food;

public class FoodNotFoundException extends Exception {

	Food food;
	
	public FoodNotFoundException(Food food){
		this.food = food;
	}
}
