package zookeepers;

import structures.Enclosure;
import structures.Foodstore;

public class PhysioZookeeper extends Zookeeper {

	/*
	 * PhysioZookeeper doesn't need any different properties to Zookeeper, just being an instance
	 * of this class is enough to make them work correctly
	 */
	
	public PhysioZookeeper(String name, Enclosure assignedEnclosure, Foodstore zooStore) {
		super(name, assignedEnclosure, zooStore);
	}

}
