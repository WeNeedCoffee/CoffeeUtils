package coffee.weneed.utils;

import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Interface IJSONObjectDataHolder.
 */
public interface IJSONObjectDataHolder {

	/**
	 * From JSON.
	 *
	 * @param json the json
	 */
	public void fromJSON(JSONObject json);

	/**
	 * To JSON.
	 *
	 * @return the JSON object
	 */
	public JSONObject toJSON();
}
