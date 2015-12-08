package structures;

import animals.Animal;

public class EnclosureFullException extends Exception {
	
	private Animal a;
	
	public EnclosureFullException(Animal a){
		this.a = a;
	}
	
	public Animal getAnimal(){
		return a;
	}
}
