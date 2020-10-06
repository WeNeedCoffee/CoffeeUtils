package coffee.weneed.utils;

import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;
import coffee.weneed.utils.io.CoffeeWriter;

public class JSONUtil {

	/***
	 * Convert JSON into pseudo CoffeeStorage format
	 * @param json
	 * @return
	 */
	public static byte[] bytesFromJSONArray(final JSONArray json) {
		final CoffeeWriter writer = new CoffeeWriter();
		writer.write((byte) 0x92);
		writer.writeSmart(json.length());
		for (final Object object : json.toList()) {
			if (object instanceof JSONObject) {
				writer.write(bytesFromJSONObject((JSONObject) object));
			} else if (object instanceof JSONArray) {
				writer.write(bytesFromJSONArray((JSONArray) object));
			} else if (object instanceof Number) {
				writer.writeSmart((Number) object);
			} else if (object instanceof String) {
				writer.writeString((String) object);
			}
		}
		writer.write((byte) 0x93);
		return writer.getByteArray();
	}

	/***
	 * Convert JSON into pseudo CoffeeStorage format
	 * @param json
	 * @return
	 */
	public static byte[] bytesFromJSONObject(final JSONObject json) {
		final CoffeeWriter writer = new CoffeeWriter();
		writer.write((byte) 0x90);
		writer.writeSmart(json.length());
		for (final Entry<String, Object> entry : json.toMap().entrySet()) {
			writer.writeString(entry.getKey());
			final Object value = entry.getValue();
			if (value instanceof JSONObject) {
				writer.write(bytesFromJSONObject((JSONObject) value));
			} else if (value instanceof JSONArray) {
				writer.write(bytesFromJSONArray((JSONArray) value));
			} else if (value instanceof Number) {
				writer.writeSmart((Number) value);
			} else if (value instanceof String) {
				writer.writeString((String) value);
			}
		}
		writer.write((byte) 0x91);
		return writer.getByteArray();
	}
}
