package zookeepers;

import structures.Enclosure;
import structures.Foodstore;

public class PlayZookeeper extends Zookeeper {

	/*
	 * PlayZookeeper doesn't need any different properties to zookeeper, just being an instance
	 * of this class is enough to make them work correctly
	 */
	
	public PlayZookeeper(String name, Enclosure assignedEnclosure, Foodstore zooStore) {
		super(name, assignedEnclosure, zooStore);
	}

}
