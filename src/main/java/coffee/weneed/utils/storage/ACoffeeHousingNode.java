package coffee.weneed.utils.storage;

import coffee.weneed.utils.dataholders.IByteArrayDataHolder;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class ACoffeeHousingNode.
 */
public abstract class ACoffeeHousingNode implements IByteArrayDataHolder, IJSONObjectDataHolder {

	/** The parent. */
	protected ACoffeeHousingNode parent;
	/**
	 * Instantiates a new a coffee housing node.
	 */
	public ACoffeeHousingNode() {
		this.parent = null;
	}
	
	/**
	 * Instantiates a new a coffee housing node.
	 *
	 * @param parent the parent
	 */
	public ACoffeeHousingNode(ACoffeeHousingNode parent) {
		this.parent = parent;
	}

	/**
	 * Deserialize.
	 *
	 * @param lea the lea
	 */
	protected abstract void deserialize(CoffeeAccessor lea);

	/**
	 * Serialize.
	 *
	 * @param lew the lew
	 */
	protected abstract void serialize(CoffeeWriter lew);
}
