package zookeepers;

import structures.Enclosure;
import structures.Foodstore;

public class PhysioZookeeper extends Zookeeper {

	public PhysioZookeeper(String name, Enclosure assignedEnclosure, Foodstore zooStore) {
		super(name, assignedEnclosure, zooStore);
	}

}
