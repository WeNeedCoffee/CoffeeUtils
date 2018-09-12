package coffee.weneed.utils.storage;

import coffee.weneed.utils.net.io.LittleEndianAccessor;
import coffee.weneed.utils.net.io.LittleEndianWriter;

public abstract class ACoffeeHousingNode {

	protected ACoffeeHousingNode parent;

	public ACoffeeHousingNode(ACoffeeHousingNode parent) {
		this.parent = parent;
	}

	protected abstract void deserialize(LittleEndianAccessor lea);

	protected abstract void serialize(LittleEndianWriter lew);
}