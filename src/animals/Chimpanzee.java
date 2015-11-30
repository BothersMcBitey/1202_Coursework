package animals;

public class Chimpanzee extends Ape {

	/*
	 * creates newborn chimp
	 */
	public Chimpanzee(char gender){
		this(0, gender, 10);
	}
	
	public Chimpanzee(int age, char gender, int health) {
		super(age, gender, health, 24);
	}
	
	@Override
	public void treat(){
		playChase();
	}

	private void playChase() {
		health += 4;
	}

}
