package coffee.weneed.utils.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import coffee.weneed.utils.dataholders.IByteArrayDataHolder;
import coffee.weneed.utils.dataholders.IJSONObjectDataHolder;
import coffee.weneed.utils.io.CoffeeReader;
import coffee.weneed.utils.io.CoffeeWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class ACoffeeHousingNode.
 */
public abstract class ACoffeeHousingNode implements IByteArrayDataHolder, IJSONObjectDataHolder {

	/** The parent. */
	protected ACoffeeHousingNode parent;
	protected String ID;
	/** The items. */
	protected Map<String, Object> items = new HashMap<>();

	protected ACoffeeHousingNode() {
		parent = null;
		ID = null;
	}

	/**
	 * Instantiates a new a coffee housing node.
	 *
	 * @param parent the parent
	 */
	public ACoffeeHousingNode(ACoffeeHousingNode parent, String ID) {
		this.parent = parent;
		this.ID = ID;
	}

	/**
	 * Instantiates a new a coffee housing node.
	 */
	protected ACoffeeHousingNode(String ID) {
		parent = null;
		this.ID = ID;
	}

	/**
	 * Deserialize.
	 *
	 * @param lea the lea
	 * @throws IOException
	 */
	protected abstract void deserialize(CoffeeReader lea) throws IOException;

	public String getID() {
		return ID;
	}

	/**
	 * Serialize.
	 *
	 * @param lew the lew
	 */
	protected abstract void serialize(CoffeeWriter lew);
}
