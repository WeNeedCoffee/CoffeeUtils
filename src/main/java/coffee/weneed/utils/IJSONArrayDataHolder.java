package coffee.weneed.utils;

import org.json.JSONArray;

// TODO: Auto-generated Javadoc
/**
 * The Interface IJSONArrayDataHolder.
 */
public interface IJSONArrayDataHolder {

	/**
	 * From JSON.
	 *
	 * @param json the json
	 */
	public void fromJSON(JSONArray json);

	/**
	 * To JSON.
	 *
	 * @return the JSON array
	 */
	public JSONArray toJSON();

}
