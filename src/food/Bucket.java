package food;

/**
 * A bucket is a representation of the amount of food 
 * of a specific type each Foodstore can hold, and how
 * much it currently contains.
 * 
 * @author callum
 *
 */
public class Bucket {
	
	//max food that can fit in the bucket
	private int capacity;
	//food currently in the bucket
	private int amount;

	public Bucket(int capacity){
		amount = 0;
		this.capacity = capacity;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void removeFood(int amount) throws InsufficientFoodException{
		if(this.amount >= amount){
			this.amount -= amount;
		} else {
			throw new InsufficientFoodException(this.amount, amount);
		}
	}
	
	public void addFood(int amount){
		if(amount > 0){
			if(amount + this.amount > capacity){
				this.amount = capacity;
			} else {
				this.amount += amount;
			}
		}
	}
}
