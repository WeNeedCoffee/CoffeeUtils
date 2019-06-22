package coffee.weneed.utils.storage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import coffee.weneed.utils.MathUtil;
import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.io.ByteArrayByteStream;
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class CoffeeHousingObject.
 */
public class CoffeeHousingObject extends ACoffeeHousingNode {

	/** The items. */
	protected Map<String, Object> items = new HashMap<>();

	/**
	 * Instantiates a new coffee housing object.
	 */
	public CoffeeHousingObject() {
		super();
	}

	/**
	 * Instantiates a new coffee housing object.
	 *
	 * @param parent the parent
	 */
	public CoffeeHousingObject(ACoffeeHousingNode parent) {
		super(parent);
	}

	/**
	 * Deserialize.
	 *
	 * @param ca the ca
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.storage.ACoffeeHousingNode#deserialize(coffee.weneed.
	 * utils.io.CoffeeAccessor)
	 */
	@Override
	protected void deserialize(CoffeeAccessor ca) {
		if (ca.available() < 2)
			return;
		items.clear();
		int e = ca.readSmart().intValue();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), MathUtil.smartNumber(ca.readSmart()));
		}
		e = ca.readSmart().intValue();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readBytes(ca.readSmart().intValue()));
		}
		e = ca.readSmart().intValue();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readString());
		}
		e = ca.readSmart().intValue();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), new JSONObject(ca.readString()));
		}
		e = ca.readSmart().intValue();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), new JSONArray(ca.readString()));
		}
		e = ca.readSmart().intValue();
		for (int i = 0; i < e; i++) {
			String k = ca.readString();
			ACoffeeHousingNode node = null;
			switch (ca.readByte()) {
				case (byte) 0x12: {
					node = new CoffeeHousingObject(this);
					node.deserialize(ca);
					node.parent = this;
					items.put(k, node);
					break;
				}
				case (byte) 0x13: {
					node = new CoffeeHousingList(this);
					node.deserialize(ca);
					node.parent = this;
					items.put(k, node);
					break;
				}
			}
		}
	}

	/**
	 * From byte array.
	 *
	 * @param in the in
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.dataholders.IByteArrayDataHolder#fromByteArray(byte[]
	 * )
	 */
	@Override
	public void fromByteArray(byte[] in) {
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(in));
		ca.readByte();
		ca.readByte();
		deserialize(ca);
		ca.readByte();
	}

	/**
	 * From JSON.
	 *
	 * @param json the json
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.dataholders.IJSONObjectDataHolder#fromJSON(org.json.
	 * JSONObject)
	 */
	@Override
	public void fromJSON(JSONObject json) {
		for (String k : json.keySet()) {
			Object o = json.get(k);
			if (o instanceof JSONObject) {
				JSONObject obj = (JSONObject) o;
				if (k.equals("strings")) {
					for (String sk : obj.keySet()) {
						if (obj.get(sk) instanceof String) {
							items.put(sk, obj.getString(sk));
						}
					}
				} else if (k.equals("children")) {
					for (String sk : obj.keySet()) {
						if (obj.get(sk) instanceof JSONObject) {
							if (obj.getJSONObject(sk).getInt("type") == 1) {
								CoffeeHousingObject cho = new CoffeeHousingObject(this);
								cho.fromJSON(obj.getJSONObject(sk));
								items.put(sk, cho);
							} else if (obj.getJSONObject(sk).getInt("type") == 0) {
								CoffeeHousingList cho = new CoffeeHousingList(this);
								cho.fromJSON(obj.getJSONObject(sk));
								items.put(sk, cho);
							}
						}
					}
				} else if (k.equals("numbers")) {
					for (String sk : obj.keySet()) {
						if (obj.get(sk) instanceof Number) {
							items.put(sk, MathUtil.smartNumber((Number) obj.get(sk)));
						}
					}
				} else if (k.equals("byte_arrays")) {
					for (String sk : obj.keySet()) {
						if (obj.get(sk) instanceof String) {
							items.put(sk, StringUtil.hexToBytes(obj.getString(sk)));
						}
					}
				} else if (k.equals("json_objects")) {
					for (String sk : obj.keySet()) {
						if (obj.get(sk) instanceof JSONObject) {
							items.put(sk, obj.getJSONObject(sk));
						}
					}
				} else if (k.equals("json_arrays")) {
					for (String sk : obj.keySet()) {
						if (obj.get(sk) instanceof JSONArray) {
							items.put(sk, obj.getJSONArray(sk));
						}
					}
				}

			}
		}
	}

	/**
	 * Gets the.
	 *
	 * @param k the k
	 * @return the object
	 */
	public Object get(String k) {
		return items.get(k);
	}

	/**
	 * Gets the byte array.
	 *
	 * @param k the k
	 * @return the byte array
	 */
	public byte[] getByteArray(String k) {
		return (byte[]) items.get(k);
	}

	/**
	 * Gets the child.
	 *
	 * @param k the k
	 * @return the child
	 */
	public ACoffeeHousingNode getChild(String k) {
		return (ACoffeeHousingNode) items.get(k);
	}

	/**
	 * Gets the number.
	 *
	 * @param k the k
	 * @return the number
	 */
	public Number getNumber(String k) {
		return (Number) items.get(k);
	}

	/**
	 * Gets the string.
	 *
	 * @param k the k
	 * @return the string
	 */
	public String getString(String k) {
		return (String) items.get(k);
	}

	/**
	 * Save.
	 *
	 * @param folder the folder
	 */
	protected void save(File folder) {
		/*
		 * lists can only contain objects objects can contain lists or objects
		 * objects hold data lists do not hold data \LIST\OBJECT\OBJECT.json --
		 * contains all data within an object object data is held within the
		 * folder named after the object, not the parent folder
		 * \LIST\OBJECT1\OBJECT1.json RIGHT \LIST\OBJECT1.json WRONG
		 */
		if (!folder.exists()) {
			folder.mkdir();
		}
		if (!folder.isDirectory())
			return;
		new File(folder.getPath() + File.separator + folder.getName() + ".json");
	}

	/**
	 * Serialize.
	 *
	 * @param cw the cw
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.storage.ACoffeeHousingNode#serialize(coffee.weneed.
	 * utils. io.CoffeeWriter)
	 */
	@Override
	protected void serialize(CoffeeWriter cw) {
		cw.write((byte) 0x12);
		Map<String, Number> numbers = new HashMap<>();
		Map<String, byte[]> byteArrays = new HashMap<>();
		Map<String, String> strings = new HashMap<>();
		Map<String, String> jsonos = new HashMap<>();
		Map<String, String> jsonas = new HashMap<>();
		Map<String, ACoffeeHousingNode> children = new HashMap<>();
		for (String k : items.keySet()) {
			Object o = items.get(k);
			if (o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double) {
				numbers.put(k, (Number) o);
			} else if (o instanceof byte[]) {
				byteArrays.put(k, (byte[]) o);
			} else if (o instanceof String) {
				strings.put(k, (String) o);
			} else if (o instanceof ACoffeeHousingNode) {
				children.put(k, (ACoffeeHousingNode) o);
			} else if (o instanceof JSONObject) {
				jsonos.put(k, ((JSONObject) o).toString());
			} else if (o instanceof JSONArray) {
				jsonas.put(k, ((JSONArray) o).toString());
			}
		}
		cw.writeSmart(numbers.size());
		for (String s : numbers.keySet()) {
			Number b = numbers.get(s);
			cw.writeString(s);
			cw.writeSmart(b);
		}
		cw.writeSmart(byteArrays.size());
		for (String s : byteArrays.keySet()) {
			byte[] bs = byteArrays.get(s);
			cw.writeString(s);
			cw.writeSmart(bs.length);
			cw.write(bs);
		}
		cw.writeSmart(strings.size());
		for (String s : strings.keySet()) {
			String ss = strings.get(s);
			cw.writeString(s);
			cw.writeString(ss);
		}
		cw.writeSmart(jsonos.size());
		for (String s : jsonos.keySet()) {
			String ss = jsonos.get(s);
			cw.writeString(s);
			cw.writeString(ss.toString());
		}
		cw.writeSmart(jsonas.size());
		for (String s : jsonas.keySet()) {
			String ss = jsonas.get(s);
			cw.writeString(s);
			cw.writeString(ss.toString());
		}
		cw.writeSmart(children.size());
		for (String s : children.keySet()) {
			cw.writeString(s);
			children.get(s).serialize(cw);
		}
	}

	/**
	 * Sets the object.
	 *
	 * @param k the k
	 * @param o the o
	 * @throws Exception the exception
	 */
	public void set(String k, Object o) throws Exception {
		if (o instanceof Byte || o instanceof byte[] || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double || o instanceof Short || o instanceof String || o instanceof ACoffeeHousingNode || o instanceof JSONObject || o instanceof JSONArray) {
			items.put(k, o);
		} else {
			throw new Exception("Unsupported data type");
		}
	}

	/**
	 * Sets the byte array.
	 *
	 * @param k the k
	 * @param b the b
	 */
	public void setByteArray(String k, byte[] b) {
		items.put(k, b);
	}

	/**
	 * Sets the child.
	 *
	 * @param k   the k
	 * @param chn the chn
	 */
	public void setChild(String k, ACoffeeHousingNode chn) {
		chn.parent = this;
		items.put(k, chn);
	}

	/**
	 * Sets the JSON array.
	 *
	 * @param k the k
	 * @param a the a
	 */
	public void setJSONArray(String k, JSONArray a) {
		items.put(k, a);
	}

	/**
	 * Sets the JSON object.
	 *
	 * @param k the k
	 * @param o the o
	 */
	public void setJSONObject(String k, JSONObject o) {
		items.put(k, o);
	}

	/**
	 * Sets the number.
	 *
	 * @param k the k
	 * @param n the n
	 */
	public void setNumber(String k, Number n) {
		items.put(k, MathUtil.smartNumber(n));
	}

	/**
	 * Sets the string.
	 *
	 * @param k the k
	 * @param s the s
	 */
	public void setString(String k, String s) {
		items.put(k, s);
	}

	/**
	 * To byte array.
	 *
	 * @return the byte[]
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see coffee.weneed.utils.dataholders.IByteArrayDataHolder#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		CoffeeWriter cw = new CoffeeWriter();
		cw.write((byte) 0x10);
		serialize(cw);
		cw.write((byte) 0x11);
		return cw.getByteArray();
	}

	/**
	 * To JSON.
	 *
	 * @return the JSON object
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see coffee.weneed.utils.dataholders.IJSONObjectDataHolder#toJSON()
	 */
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		JSONObject numbers = new JSONObject();
		JSONObject byte_arrays = new JSONObject();
		JSONObject strings = new JSONObject();
		JSONObject objects = new JSONObject();
		JSONObject arrays = new JSONObject();
		JSONObject children = new JSONObject();
		json.put("type", 1);
		for (String k : items.keySet()) {
			Object o = items.get(k);
			if (o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double) {
				numbers.put(k, MathUtil.smartNumber((Number) o));
			} else if (o instanceof byte[]) {
				byte_arrays.put(k, StringUtil.bytesToHex((byte[]) o));
			} else if (o instanceof String) {
				strings.put(k, o);
			} else if (o instanceof ACoffeeHousingNode) {
				children.put(k, ((ACoffeeHousingNode) o).toJSON());
			} else if (o instanceof JSONObject) {
				objects.put(k, o);
			} else if (o instanceof JSONArray) {
				arrays.put(k, o);
			}
		}
		if (numbers.length() > 0) {
			json.put("numbers", numbers);
		}
		if (byte_arrays.length() > 0) {
			json.put("byte_arrays", byte_arrays);
		}
		if (strings.length() > 0) {
			json.put("strings", strings);
		}
		if (objects.length() > 0) {
			json.put("json_objects", objects);
		}
		if (arrays.length() > 0) {
			json.put("json_arrays", arrays);
		}
		if (children.length() > 0) {
			json.put("children", children);
		}
		return json;
	}

}
