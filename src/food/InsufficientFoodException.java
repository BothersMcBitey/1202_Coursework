package food;

public class InsufficientFoodException extends Exception {

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
