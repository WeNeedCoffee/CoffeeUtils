package coffee.weneed.utils.storage;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import coffee.weneed.utils.HexUtil;
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
	 *
	 * @param parent the parent
	 */
	public CoffeeHousingObject(ACoffeeHousingNode parent) {
		super(parent);
	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.storage.ACoffeeHousingNode#deserialize(coffee.weneed.utils.io.CoffeeAccessor)
	 */
	@Override
	protected void deserialize(CoffeeAccessor ca) {
		if (ca.available() < 2) { // byte for terminator
			return;
		}
		items.clear();
		int e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readByte());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readBytes(ca.readInt()));
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readInt());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readLong());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readFloat());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readDouble());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readShort());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readChar());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			items.put(ca.readString(), ca.readString());
		}
		int n = ca.readInt();
		for (int i = 0; i < n; i++) {
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

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.dataholders.IByteArrayDataHolder#fromByteArray(byte[])
	 */
	@Override
	public void fromByteArray(byte[] in) {
		CoffeeAccessor ca = new CoffeeAccessor(new ByteArrayByteStream(in));
		ca.readByte();
		ca.readByte();
		deserialize(ca);
		ca.readByte();
	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.dataholders.IJSONObjectDataHolder#fromJSON(org.json.JSONObject)
	 */
	@Override
	public void fromJSON(JSONObject json) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.storage.ACoffeeHousingNode#serialize(coffee.weneed.utils.io.CoffeeWriter)
	 */
	@Override
	protected void serialize(CoffeeWriter cw) {
		cw.write((byte) 0x12);
		Map<String, Byte> bytes = new HashMap<>();
		Map<String, byte[]> byte_arrays = new HashMap<>();
		Map<String, Integer> ints = new HashMap<>();
		Map<String, Long> longs = new HashMap<>();
		Map<String, Float> floats = new HashMap<>();
		Map<String, Double> doubles = new HashMap<>();
		Map<String, Short> shorts = new HashMap<>();
		Map<String, Character> chars = new HashMap<>();
		Map<String, String> strings = new HashMap<>();
		Map<String, ACoffeeHousingNode> children = new HashMap<>();

		for (String k : items.keySet()) {
			Object o = items.get(k);
			if (o instanceof Byte) {
				bytes.put(k, (byte) o);
			} else if (o instanceof byte[]) {
				byte_arrays.put(k, (byte[]) o);
			} else if (o instanceof Integer) {
				ints.put(k, (int) o);
			} else if (o instanceof Long) {
				longs.put(k, (long) o);
			} else if (o instanceof Float) {
				floats.put(k, (float) o);
			} else if (o instanceof Double) {
				doubles.put(k, (double) o);
			} else if (o instanceof Short) {
				shorts.put(k, (short) o);
			} else if (o instanceof Character) {
				chars.put(k, (char) o);
			} else if (o instanceof String) {
				strings.put(k, (String) o);
			} else if (o instanceof ACoffeeHousingNode) {
				children.put(k, (ACoffeeHousingNode) o);
			}
		}
		cw.writeInt(bytes.size());
		for (String s : bytes.keySet()) {
			byte b = bytes.get(s);
			cw.writeString(s);
			cw.write(b);
		}
		cw.writeInt(byte_arrays.size());
		for (String s : byte_arrays.keySet()) {
			byte[] bs = byte_arrays.get(s);
			cw.writeString(s);
			cw.writeInt(bs.length);
			cw.write(bs);
		}
		cw.writeInt(ints.size());
		for (String s : ints.keySet()) {
			int i = ints.get(s);
			cw.writeString(s);
			cw.writeInt(i);
		}
		cw.writeInt(longs.size());
		for (String s : longs.keySet()) {
			long o = longs.get(s);
			cw.writeString(s);
			cw.writeLong(o);
		}
		cw.writeInt(floats.size());
		for (String s : floats.keySet()) {
			float f = floats.get(s);
			cw.writeString(s);
			cw.writeFloat(f);
		}
		cw.writeInt(doubles.size());
		for (String s : doubles.keySet()) {
			double d = doubles.get(s);
			cw.writeString(s);
			cw.writeDouble(d);
		}
		cw.writeInt(shorts.size());
		for (String s : shorts.keySet()) {
			short i = shorts.get(s);
			cw.writeString(s);
			cw.writeShort(i);
		}
		cw.writeInt(chars.size());
		for (String s : chars.keySet()) {
			char c = chars.get(s);
			cw.writeString(s);
			cw.writeChar(c);
		}
		cw.writeInt(strings.size());
		for (String s : strings.keySet()) {
			String ss = strings.get(s);
			cw.writeString(s);
			cw.writeString(ss);
		}
		cw.writeInt(children.size());
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
	 */
	public void setObject(String k, Object o) {
		if (o instanceof Byte || o instanceof byte[] || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double
				|| o instanceof Short || o instanceof Character || o instanceof String || o instanceof ACoffeeHousingNode) {
			items.put(k, o);
		} else {
			// throw something
		}
	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.dataholders.IByteArrayDataHolder#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		CoffeeWriter lew = new CoffeeWriter();
		lew.write((byte) 0x10);
		serialize(lew);
		lew.write((byte) 0x11);
		return lew.getByteArray();
	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.dataholders.IJSONObjectDataHolder#toJSON()
	 */
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		JSONObject bytes = new JSONObject();
		JSONObject byte_arrays = new JSONObject();
		JSONObject ints = new JSONObject();
		JSONObject longs = new JSONObject();
		JSONObject floats = new JSONObject();
		JSONObject doubles = new JSONObject();
		JSONObject shorts = new JSONObject();
		JSONObject chars = new JSONObject();
		JSONObject strings = new JSONObject();
		JSONObject children = new JSONObject();
		Map<String, Byte> tbytes = new HashMap<>();
		Map<String, byte[]> tbyte_arrays = new HashMap<>();
		Map<String, Integer> tints = new HashMap<>();
		Map<String, Long> tlongs = new HashMap<>();
		Map<String, Float> tfloats = new HashMap<>();
		Map<String, Double> tdoubles = new HashMap<>();
		Map<String, Short> tshorts = new HashMap<>();
		Map<String, Character> tchars = new HashMap<>();
		Map<String, String> tstrings = new HashMap<>();
		Map<String, ACoffeeHousingNode> tchildren = new HashMap<>();

		for (String k : items.keySet()) {
			Object o = items.get(k);
			if (o instanceof Byte) {
				tbytes.put(k, (byte) o);
			} else if (o instanceof byte[]) {
				tbyte_arrays.put(k, (byte[]) o);
			} else if (o instanceof Integer) {
				tints.put(k, (int) o);
			} else if (o instanceof Long) {
				tlongs.put(k, (long) o);
			} else if (o instanceof Float) {
				tfloats.put(k, (float) o);
			} else if (o instanceof Double) {
				tdoubles.put(k, (double) o);
			} else if (o instanceof Short) {
				tshorts.put(k, (short) o);
			} else if (o instanceof Character) {
				tchars.put(k, (char) o);
			} else if (o instanceof String) {
				tstrings.put(k, (String) o);
			} else if (o instanceof ACoffeeHousingNode) {
				tchildren.put(k, (ACoffeeHousingNode) o);
			}
		}
		for (String s : tbytes.keySet()) {
			bytes.put(s, tbytes.get(s));
		}
		for (String s : tbyte_arrays.keySet()) {
			byte_arrays.put(s, HexUtil.getHexFromBytes(tbyte_arrays.get(s)));
		}
		for (String s : tints.keySet()) {
			ints.put(s, tints.get(s));
		}
		for (String s : tlongs.keySet()) {
			longs.put(s, tlongs.get(s));
		}
		for (String s : tfloats.keySet()) {
			floats.put(s, tfloats.get(s));
		}
		for (String s : tdoubles.keySet()) {
			doubles.put(s, tdoubles.get(s));
		}
		for (String s : tshorts.keySet()) {
			shorts.put(s, tshorts.get(s));
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

		if (bytes.length() > 0) {
			json.put("bytes", bytes);
		}
		if (byte_arrays.length() > 0) {
			json.put("byte_arrays", byte_arrays);
		}
		if (ints.length() > 0) {
			json.put("ints", ints);
		}
		if (longs.length() > 0) {
			json.put("longs", longs);
		}
		if (floats.length() > 0) {
			json.put("floats", floats);
		}
		if (doubles.length() > 0) {
			json.put("doubles", doubles);
		}
		if (shorts.length() > 0) {
			json.put("shorts", shorts);
		}
		if (chars.length() > 0) {
			json.put("chars", chars);
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
