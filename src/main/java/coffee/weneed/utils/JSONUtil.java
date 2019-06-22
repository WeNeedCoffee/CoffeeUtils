package coffee.weneed.utils;

import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;
import coffee.weneed.utils.io.CoffeeWriter;

public class JSONUtil {

	public byte[] bytesFromJSONObject(JSONObject json) {
		CoffeeWriter cw = new CoffeeWriter();
		cw.write((byte) 0x90);
		cw.writeSmart(json.length());
		for (Entry<String, Object> o : json.toMap().entrySet()) {
			cw.writeString(o.getKey());
			Object v = o.getValue();
			if (v instanceof JSONObject) {
				cw.write(bytesFromJSONObject((JSONObject) v));
			} else if (v instanceof JSONArray) {
				cw.write(bytesFromJSONArray((JSONArray) v)); 
			} else if (v instanceof Number) {
				cw.writeSmart((Number) v); 
			} else if (v instanceof String) {
				cw.writeString((String) v); 
			}
		}
		cw.write((byte) 0x91);
		return cw.getByteArray();
	}
	
	public byte[] bytesFromJSONArray(JSONArray json) {
		CoffeeWriter cw = new CoffeeWriter();
		cw.write((byte) 0x92);
		cw.writeSmart(json.length());
		for (Object o : json.toList()) {
			if (o instanceof JSONObject) {
				cw.write(bytesFromJSONObject((JSONObject) o));
			} else if (o instanceof JSONArray) {
				cw.write(bytesFromJSONArray((JSONArray) o)); 
			} else if (o instanceof Number) {
				cw.writeSmart((Number) o); 
			} else if (o instanceof String) {
				cw.writeString((String) o); 
			}
		}
		cw.write((byte) 0x93);
		return cw.getByteArray();
	}
}
