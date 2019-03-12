package coffee.weneed.utils.dataholders;

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
	void fromJSON(JSONArray json);

	/**
	 * To JSON.
	 *
	 * @return the JSON array
	 */
	JSONArray toJSON();

}
