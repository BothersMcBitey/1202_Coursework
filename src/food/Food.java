package food;

public class Food {

	//name of the food, e.g. "steak"
	private String name;
	//how much health it restores
	private int energy;
	//how much waste it produces
	private int waste;
	
	public Food(String name, int energy, int waste){
		this.name = name;
		this.energy = energy;
		this.waste = waste;
	}
	
	public String getName(){
		return name;
	}
	
	public int getEnergy(){
		return energy;
	}
	public int getWaste(){
		return waste;
	}
}
