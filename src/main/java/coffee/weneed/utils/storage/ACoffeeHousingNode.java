package coffee.weneed.utils.storage;

import coffee.weneed.utils.dataholders.IByteArrayDataHolder;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;

public abstract class ACoffeeHousingNode implements IJSONObjectDataHolder, IByteArrayDataHolder {

	protected ACoffeeHousingNode parent;

	public ACoffeeHousingNode(ACoffeeHousingNode parent) {
		this.parent = parent;
	}

	protected abstract void deserialize(CoffeeAccessor lea);

	protected abstract void serialize(CoffeeWriter lew);
}
