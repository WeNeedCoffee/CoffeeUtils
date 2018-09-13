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

	/** The bytes. */
	protected Map<String, Byte> bytes = new HashMap<>();

	/** The byte arrays. */
	protected Map<String, byte[]> byte_arrays = new HashMap<>();

	/** The ints. */
	protected Map<String, Integer> ints = new HashMap<>();

	/** The longs. */
	protected Map<String, Long> longs = new HashMap<>();

	/** The floats. */
	protected Map<String, Float> floats = new HashMap<>();

	/** The doubles. */
	protected Map<String, Double> doubles = new HashMap<>();

	/** The shorts. */
	protected Map<String, Short> shorts = new HashMap<>();

	/** The chars. */
	protected Map<String, Character> chars = new HashMap<>();

	/** The strings. */
	protected Map<String, String> strings = new HashMap<>();

	/** The children. */
	protected Map<String, ACoffeeHousingNode> children = new HashMap<>();

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
		bytes.clear();
		byte_arrays.clear();
		ints.clear();
		longs.clear();
		floats.clear();
		doubles.clear();
		shorts.clear();
		chars.clear();
		strings.clear();
		int e = ca.readInt();
		for (int i = 0; i < e; i++) {
			bytes.put(ca.readString(), ca.readByte());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			byte_arrays.put(ca.readString(), ca.readBytes(ca.readInt()));
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			ints.put(ca.readString(), ca.readInt());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			longs.put(ca.readString(), ca.readLong());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			floats.put(ca.readString(), ca.readFloat());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			doubles.put(ca.readString(), ca.readDouble());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			shorts.put(ca.readString(), ca.readShort());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			chars.put(ca.readString(), ca.readChar());
		}
		e = ca.readInt();
		for (int i = 0; i < e; i++) {
			strings.put(ca.readString(), ca.readString());
		}
		children.clear();
		int n = ca.readInt();
		for (int i = 0; i < n; i++) {
			String k = ca.readString();
			ACoffeeHousingNode node = null;
			switch (ca.readByte()) {
				case (byte) 0x12: {
					node = new CoffeeHousingObject(this);
					node.deserialize(ca);
					node.parent = this;
					children.put(k, node);
					break;
				}
				case (byte) 0x13: {
					node = new CoffeeHousingList(this);
					node.deserialize(ca);
					node.parent = this;
					children.put(k, node);
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

	/**
	 * Gets the child node.
	 *
	 * @param key the key
	 * @return the child node
	 */
	public ACoffeeHousingNode getChildNode(String key) {
		return children.get(key);
	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.storage.ACoffeeHousingNode#serialize(coffee.weneed.utils.io.CoffeeWriter)
	 */
	@Override
	protected void serialize(CoffeeWriter cw) {
		cw.write((byte) 0x12);
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
			((ACoffeeHousingNode) o).parent = this;
			children.put(k, (ACoffeeHousingNode) o);
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

		for (String s : this.bytes.keySet()) {
			bytes.put(s, this.bytes.get(s));
		}
		for (String s : this.byte_arrays.keySet()) {
			byte_arrays.put(s, HexUtil.getHexFromBytes(this.byte_arrays.get(s)));
		}
		for (String s : this.ints.keySet()) {
			ints.put(s, this.ints.get(s));
		}
		for (String s : this.longs.keySet()) {
			longs.put(s, this.longs.get(s));
		}
		for (String s : this.floats.keySet()) {
			floats.put(s, this.floats.get(s));
		}
		for (String s : this.doubles.keySet()) {
			doubles.put(s, this.doubles.get(s));
		}
		for (String s : this.shorts.keySet()) {
			shorts.put(s, this.shorts.get(s));
		}
		for (String s : this.chars.keySet()) {
			chars.put(s, this.chars.get(s));
		}
		for (String s : this.strings.keySet()) {
			strings.put(s, this.strings.get(s));
		}
		for (String s : this.children.keySet()) {
			children.put(s, this.children.get(s).toJSON());
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
