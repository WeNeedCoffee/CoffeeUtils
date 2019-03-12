package coffee.weneed.utils.dataholders;

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
	void fromJSON(JSONObject json);

	/**
	 * To JSON.
	 *
	 * @return the JSON object
	 */
	JSONObject toJSON();
}
