package animals;

import food.Food;

public class Species {

	private final String NAME;
	private final int GESTATION_PERIOD;
	private final int LITTER_MAX_SIZE;
	private final int LIFE_EXPECTANCY;
	private final Food[] EATS;
	
	public Species(String name, int gestationPeriod, int litterMaxSize, int lifeExpectancy, Food[] eats){
		this.NAME = name;
		this.GESTATION_PERIOD = gestationPeriod;
		this.LITTER_MAX_SIZE = litterMaxSize;
		this. LIFE_EXPECTANCY = lifeExpectancy;
		this.EATS = eats;
	}

	public String getNAME() {
		return NAME;
	}

	public int getGESTATION_PERIOD() {
		return GESTATION_PERIOD;
	}

	public int getLITTER_MAX_SIZE() {
		return LITTER_MAX_SIZE;
	}

	public int getLIFE_EXPECTANCY() {
		return LIFE_EXPECTANCY;
	}

	public Food[] getEATS() {
		return EATS;
	}
}
