package food;

public class InsufficientFoodException extends Exception {

	//lets catching class try to take food again, knowing how much is left
	int amountLeft;
	int amountTaken;
	
	public InsufficientFoodException(int amountLeft, int amountTaken) {
	 this.amountLeft = amountLeft;
	 this.amountTaken = amountTaken;
	}
	
	public int getAmountLeft() {
		return amountLeft;
	}
}
