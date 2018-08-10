/*
 * ~~Part of the JavaPenguin project~~
 * Any use of this software must strictly
 * adhere to the LICENSE file.
 */
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
