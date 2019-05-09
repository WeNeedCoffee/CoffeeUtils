package coffee.weneed.utils.storage;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import coffee.weneed.utils.LogicUtil;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.storage.ACoffeeHousingNode#deserialize(coffee.weneed.
	 * utils.io.CoffeeAccessor)
	 */
	@Override
	protected void deserialize(CoffeeAccessor ca) {
		if (ca.available() < 2) { // byte for terminator
			return;
		}
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
			items.put(ca.readString(), ca.readChar());
		}
		e = ca.readSmart().intValue();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readString());
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.dataholders.IByteArrayDataHolder#fromByteArray(byte[]
	 * )
	 */
	@Override
	public void fromByteArray(byte[] in) {
		CoffeeAccessor ca = null;
		if (compress) {
			try {
				ca = new CoffeeAccessor(new ByteArrayByteStream(LogicUtil.decompress(in)));
			} catch (Exception e) {
				ca = new CoffeeAccessor(new ByteArrayByteStream(in));
			}
		} else {
			ca = new CoffeeAccessor(new ByteArrayByteStream(in));
		}
		ca.readByte();
		ca.readByte();
		deserialize(ca);
		ca.readByte();
	}

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
				}
				if (k.equals("children")) {
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
				}
				if (k.equals("numbers")) {
					for (String sk : obj.keySet()) {
						if (obj.get(sk) instanceof Number) {
							items.put(sk, MathUtil.smartNumber((Number) obj.get(sk)));
						}
					}
				}
				if (k.equals("byte_arrays")) {
					for (String sk : obj.keySet()) {
						if (obj.get(sk) instanceof String) {
							items.put(sk, StringUtil.hexToBytes((String) obj.get(sk)));
						}
					}
				}
			}
		}
	}

	public Object get(String k) {
		return items.get(k);
	}

	public byte[] getByteArray(String k) {
		return (byte[]) items.get(k);
	}

	public ACoffeeHousingNode getChild(String k) {
		return (ACoffeeHousingNode) items.get(k);
	}

	public Number getNumber(String k) {
		return (Number) items.get(k);
	}

	public String getString(String k) {
		return (String) items.get(k);
	}

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
		Map<String, byte[]> byte_arrays = new HashMap<>();
		Map<String, Character> chars = new HashMap<>();
		Map<String, String> strings = new HashMap<>();
		Map<String, ACoffeeHousingNode> children = new HashMap<>();

		for (String k : items.keySet()) {
			Object o = items.get(k);
			if (o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double) {
				numbers.put(k, (Number) o);
			} else if (o instanceof byte[]) {
				byte_arrays.put(k, (byte[]) o);
			} else if (o instanceof Character) {
				chars.put(k, (char) o);
			} else if (o instanceof String) {
				strings.put(k, (String) o);
			} else if (o instanceof ACoffeeHousingNode) {
				children.put(k, (ACoffeeHousingNode) o);
			}
		}
		cw.writeSmart(numbers.size());
		for (String s : numbers.keySet()) {
			Number b = numbers.get(s);
			cw.writeString(s);
			cw.writeSmart(b);
		}
		cw.writeSmart(byte_arrays.size());
		for (String s : byte_arrays.keySet()) {
			byte[] bs = byte_arrays.get(s);
			cw.writeString(s);
			cw.writeSmart(bs.length);
			cw.write(bs);
		}
		cw.writeSmart(chars.size());
		for (String s : chars.keySet()) {
			char c = chars.get(s);
			cw.writeString(s);
			cw.writeChar(c);
		}
		cw.writeSmart(strings.size());
		for (String s : strings.keySet()) {
			String ss = strings.get(s);
			cw.writeString(s);
			cw.writeString(ss);
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
	 * @throws Exception
	 */
	public void set(String k, Object o) throws Exception {
		if (o instanceof Byte || o instanceof byte[] || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double || o instanceof Short || o instanceof String || o instanceof ACoffeeHousingNode) {
			items.put(k, o);
		} else {
			throw new Exception("Unsupported data type");
		}
	}

	public void setByteArray(String k, byte[] b) {
		items.put(k, b);
	}

	public void setChild(String k, ACoffeeHousingNode chn) {
		chn.parent = this;
		items.put(k, chn);
	}

	public void setNumber(String k, Number n) {
		items.put(k, MathUtil.smartNumber(n));
	}

	public void setString(String k, String s) {
		items.put(k, s);
	}

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
		if (compress) {
			return LogicUtil.compress(cw.getByteArray());
		} else {
			return cw.getByteArray();
		}
	}

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
		JSONObject chars = new JSONObject();
		JSONObject strings = new JSONObject();
		JSONObject children = new JSONObject();
		Map<String, Number> tnumbers = new HashMap<>();
		Map<String, byte[]> tbyte_arrays = new HashMap<>();
		Map<String, Character> tchars = new HashMap<>();
		Map<String, String> tstrings = new HashMap<>();
		Map<String, ACoffeeHousingNode> tchildren = new HashMap<>();
		for (String k : items.keySet()) {
			Object o = items.get(k);
			if (o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double) {
				numbers.put(k, o);
			} else if (o instanceof byte[]) {
				tbyte_arrays.put(k, (byte[]) o);
			} else if (o instanceof String) {
				tstrings.put(k, (String) o);
			} else if (o instanceof ACoffeeHousingNode) {
				tchildren.put(k, (ACoffeeHousingNode) o);
			}
		}
		for (String s : tnumbers.keySet()) {
			numbers.put(s, tnumbers.get(s));
		}
		for (String s : tbyte_arrays.keySet()) {
			byte_arrays.put(s, StringUtil.bytesToHex(tbyte_arrays.get(s)));
		}
		for (String s : tchars.keySet()) {
			chars.put(s, tchars.get(s));
		}
		for (String s : tstrings.keySet()) {
			strings.put(s, tstrings.get(s));
		}
		for (String s : tchildren.keySet()) {
			children.put(s, tchildren.get(s).toJSON());
		}
		json.put("type", 1);
		if (numbers.length() > 0) {
			json.put("numbers", numbers);
		}
		if (byte_arrays.length() > 0) {
			json.put("byte_arrays", byte_arrays);
		}
		if (strings.length() > 0) {
			json.put("strings", strings);
		}
		if (children.length() > 0) {
			json.put("children", children);
		}
		return json;
	}

}
