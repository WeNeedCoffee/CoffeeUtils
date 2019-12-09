package coffee.weneed.utils.storage;

import java.io.File;
import java.io.IOException;
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
// TODO: Write own name when serializing but only if parent is not present. - for example {"a": {"b": { "name": "b"}}, writing {"name": "b"} when serializing is not necessary as it is already written when defining "b", but writing "a" is necessary.

/**
 * The Class CoffeeHousingObject.
 * 
 */
public class CoffeeHousingObject extends ACoffeeHousingNode {

	/** The items. */
	protected Map<String, Object> items = new HashMap<>();

	public CoffeeHousingObject() {
		super();
	}
	
	/**
	 * Instantiates a new coffee housing object.
	 */
	public CoffeeHousingObject(String ID) {
		super(ID);
	}

	/**
	 * Instantiates a new coffee housing object.
	 *
	 * @param parent the parent
	 */
	public CoffeeHousingObject(ACoffeeHousingNode parent, String ID) {
		super(parent, ID);
	}

	/**
	 * Deserialize.
	 *
	 * @param reader the ca
	 * @throws IOException 
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.storage.ACoffeeHousingNode#deserialize(coffee.weneed.
	 * utils.io.CoffeeAccessor)
	 */
	@Override
	protected void deserialize(CoffeeAccessor reader) throws IOException {
		if (reader.readByte() != 0x20) throw new IOException("Error.");
		reader.readSmart();
		if (reader.available() < 2)
			return;
		items.clear();
		int size = reader.readSmart().intValue();
		for (int i = 0; i < size; i++) {
			items.put(reader.readString(), MathUtil.smartNumber(reader.readSmart()));
		}
		size = reader.readSmart().intValue();
		for (int i = 0; i < size; i++) {
			items.put(reader.readString(), reader.readBytes(reader.readSmart().intValue()));
		}
		size = reader.readSmart().intValue();
		for (int i = 0; i < size; i++) {
			items.put(reader.readString(), reader.readString());
		}
		size = reader.readSmart().intValue();
		for (int i = 0; i < size; i++) {
			items.put(reader.readString(), new JSONObject(reader.readString()));
		}
		size = reader.readSmart().intValue();
		for (int i = 0; i < size; i++) {
			items.put(reader.readString(), new JSONArray(reader.readString()));
		}
		size = reader.readSmart().intValue();
		for (int i = 0; i < size; i++) {
			String k = reader.readString();
			ACoffeeHousingNode node = null;
			switch (reader.readByte()) {
				case (byte) 0x12: {
					node = new CoffeeHousingObject(this, k);
					node.deserialize(reader);
					node.parent = this;
					items.put(k, node);
					break;
				}
				case (byte) 0x13: {
					node = new CoffeeHousingList(this, k);
					node.deserialize(reader);
					node.parent = this;
					items.put(k, node);
					break;
				}
			}
		}
		if (reader.readByte() != 0x21) throw new IOException("Error.");
	}

	/**
	 * From byte array.
	 *
	 * @param in the in
	 * @throws IOException 
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.dataholders.IByteArrayDataHolder#fromByteArray(byte[]
	 * )
	 */
	@Override
	public void fromByteArray(byte[] in) throws IOException {
		CoffeeAccessor reader = new CoffeeAccessor(new ByteArrayByteStream(in));
		reader.readByte();
		deserialize(reader);
		reader.readByte();
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
		for (String key : json.keySet()) {
				JSONObject node = null; 
				try {
					node = (JSONObject) json.get(key);
				} catch (Exception e) {
					//TODO
				}
				if (key.equals("strings")) {
					for (String sk : node.keySet()) {
						if (node.get(sk) instanceof String) {
							items.put(sk, node.getString(sk));
						}
					}
				} else if (key.equals("children")) {
					for (String child_name : node.keySet()) {
						if (node.get(child_name) instanceof JSONObject) {
							if (node.getJSONObject(child_name).getInt("type") == 1) {
								CoffeeHousingObject cho = new CoffeeHousingObject(this, child_name);
								cho.fromJSON(node.getJSONObject(child_name));
								items.put(child_name, cho);
							} else if (node.getJSONObject(child_name).getInt("type") == 0) {
								CoffeeHousingList list = new CoffeeHousingList(this, child_name);
								list.fromJSON(node.getJSONObject(child_name));
								items.put(child_name, list);
							}
						}
					}
				} else if (key.equals("numbers")) {
					for (String sk : node.keySet()) {
						if (node.get(sk) instanceof Number) {
							items.put(sk, MathUtil.smartNumber((Number) node.get(sk)));
						}
					}
				} else if (key.equals("byte_arrays")) {
					for (String sk : node.keySet()) {
						if (node.get(sk) instanceof String) {
							items.put(sk, StringUtil.hexToBytes(node.getString(sk)));
						}
					}
				} else if (key.equals("json_objects")) {
					for (String sk : node.keySet()) {
						if (node.get(sk) instanceof JSONObject) {
							items.put(sk, node.getJSONObject(sk));
						}
					}
				} else if (key.equals("json_arrays")) {
					for (String sk : node.keySet()) {
						if (node.get(sk) instanceof JSONArray) {
							items.put(sk, node.getJSONArray(sk));
						}
					}
				}
		}
	}

	/**
	 * Gets the.
	 *
	 * @param key the k
	 * @return the object
	 */
	public Object get(String key) {
		return items.get(key);
	}

	/**
	 * Gets the byte array.
	 *
	 * @param key the k
	 * @return the byte array
	 */
	public byte[] getByteArray(String key) {
		return (byte[]) items.get(key);
	}

	/**
	 * Gets the child.
	 *
	 * @param key the k
	 * @return the child
	 */
	public ACoffeeHousingNode getChild(String key) {
		return (ACoffeeHousingNode) items.get(key);
	}

	/**
	 * Gets the number.
	 *
	 * @param key the k
	 * @return the number
	 */
	public Number getNumber(String key) {
		return (Number) items.get(key);
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
		 * 
		 * 
		 * EDIT: Allow coding processes to be used, preferably do not store as json
		 * store as follows:
		 * \LIST\OBJECT1\CHN.file -- numbers serialized 
		 * \LIST\OBJECT1\CHS.file -- strings serialized
		 * ...
		 * ...
		 * \LIST\OBJECT1\CHB_[name].file -- Bin serialized and named by key
		 * 
		 * Subordinate objects are stored in folders
		 * 
		 * \LIST\OBJECT1\OBJECT2\CHN.file
		 * ...
		 * ...
		 * 
		 * 
		 * lists could hold data? they have to hold what CoffeeHousingLists hold right?
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
	protected void serialize(CoffeeWriter writer) {
		CoffeeWriter twriter = new CoffeeWriter(); //create inner writer so that we can mark the size of it later
		
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
		twriter.writeSmart(numbers.size());
		for (String s : numbers.keySet()) {
			Number b = numbers.get(s);
			twriter.writeString(s);
			twriter.writeSmart(b);
		}
		twriter.writeSmart(byteArrays.size());
		for (String s : byteArrays.keySet()) {
			byte[] bs = byteArrays.get(s);
			twriter.writeString(s);
			twriter.writeSmart(bs.length);
			twriter.write(bs);
		}
		twriter.writeSmart(strings.size());
		for (String s : strings.keySet()) {
			String ss = strings.get(s);
			twriter.writeString(s);
			twriter.writeString(ss);
		}
		twriter.writeSmart(jsonos.size());
		for (String s : jsonos.keySet()) {
			String ss = jsonos.get(s);
			twriter.writeString(s);
			twriter.writeString(ss.toString());
		}
		twriter.writeSmart(jsonas.size());
		for (String s : jsonas.keySet()) {
			String ss = jsonas.get(s);
			twriter.writeString(s);
			twriter.writeString(ss.toString());
		}
		twriter.writeSmart(children.size());
		for (String s : children.keySet()) {
			twriter.writeString(s);
			children.get(s).serialize(twriter);
		}
		
		
		writer.write((byte) 0x20);
		writer.writeSmart(twriter.getSize()); //mark the size of it so that it can be skipped when reading.
		try {
			writer.write(twriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.write((byte) 0x21);
	}

	/**
	 * Sets the object.
	 *
	 * @param key the k
	 * @param value the o
	 * @throws Exception the exception
	 */
	public void set(String key, Object value) throws Exception {
		if (value instanceof Byte || value instanceof byte[] || value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Short || value instanceof String || value instanceof ACoffeeHousingNode || value instanceof JSONObject || value instanceof JSONArray) {
			items.put(key, value);
		} else {
			throw new Exception("Unsupported data type");
		}
	}

	/**
	 * Sets the byte array.
	 *
	 * @param key the k
	 * @param bytes the b
	 */
	public void setByteArray(String key, byte[] bytes) {
		items.put(key, bytes);
	}

	/**
	 * Sets the child.
	 *
	 * @param key   the k
	 * @param child the chn
	 */
	public void setChild(String key, ACoffeeHousingNode child) {
		child.parent = this;
		items.put(key, child);
	}

	/**
	 * Sets the JSON array.
	 *
	 * @param key the k
	 * @param json the a
	 */
	public void setJSONArray(String key, JSONArray json) {
		items.put(key, json);
	}

	/**
	 * Sets the JSON object.
	 *
	 * @param key the k
	 * @param json the o
	 */
	public void setJSONObject(String key, JSONObject json) {
		items.put(key, json);
	}

	/**
	 * Sets the number.
	 *
	 * @param key the k
	 * @param value the n
	 */
	public void setNumber(String key, Number value) {
		items.put(key, MathUtil.smartNumber(value));
	}

	/**
	 * Sets the string.
	 *
	 * @param key the k
	 * @param value the s
	 */
	public void setString(String key, String value) {
		items.put(key, value);
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
		CoffeeWriter writer = new CoffeeWriter();
		writer.write((byte) 0x10);
		serialize(writer);
		writer.write((byte) 0x11);
		return writer.getByteArray();
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
