package coffee.weneed.utils.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import coffee.weneed.utils.HexUtil;
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class CoffeeHousingList.
 */
public class CoffeeHousingList extends ACoffeeHousingNode {

	/** The bytes. */
	protected Map<Integer, Byte> bytes = new HashMap<>();

	/** The byte arrays. */
	protected Map<Integer, byte[]> byte_arrays = new HashMap<>();

	/** The ints. */
	protected Map<Integer, Integer> ints = new HashMap<>();

	/** The longs. */
	protected Map<Integer, Long> longs = new HashMap<>();

	/** The floats. */
	protected Map<Integer, Float> floats = new HashMap<>();

	/** The doubles. */
	protected Map<Integer, Double> doubles = new HashMap<>();

	/** The shorts. */
	protected Map<Integer, Short> shorts = new HashMap<>();

	/** The chars. */
	protected Map<Integer, Character> chars = new HashMap<>();

	/** The strings. */
	protected Map<Integer, String> strings = new HashMap<>();

	/** The children. */
	protected Map<Integer, ACoffeeHousingNode> children = new HashMap<>();

	/**
	 * Instantiates a new coffee housing list.
	 *
	 * @param parent the parent
	 */
	public CoffeeHousingList(ACoffeeHousingNode parent) {
		super(parent);
	}

	/**
	 * Delete index.
	 *
	 * @param i the i
	 * @return true, if successful
	 */
	private boolean deleteIndex(int i) {
		if (bytes.containsKey(i)) {
			bytes.remove(i);
			return true;
		} else if (byte_arrays.containsKey(i)) {
			byte_arrays.remove(i);
			return true;
		} else if (ints.containsKey(i)) {
			ints.remove(i);
			return true;
		} else if (longs.containsKey(i)) {
			longs.remove(i);
			return true;
		} else if (floats.containsKey(i)) {
			floats.remove(i);
			return true;
		} else if (doubles.containsKey(i)) {
			doubles.remove(i);
			return true;
		} else if (shorts.containsKey(i)) {
			shorts.remove(i);
			return true;
		} else if (chars.containsKey(i)) {
			chars.remove(i);
			return true;
		} else if (strings.containsKey(i)) {
			strings.remove(i);
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.storage.ACoffeeHousingNode#deserialize(coffee.weneed.utils.io.CoffeeAccessor)
	 */
	@Override
	protected void deserialize(CoffeeAccessor ca) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.dataholders.IByteArrayDataHolder#fromByteArray(byte[])
	 */
	@Override
	public void fromByteArray(byte[] b) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.dataholders.IJSONObjectDataHolder#fromJSON(org.json.JSONObject)
	 */
	@Override
	public void fromJSON(JSONObject json) {
		// TODO Auto-generated method stub

	}

	/**
	 * Removes the index.
	 *
	 * @param i the i
	 */
	public void removeIndex(int i) {
		if (!deleteIndex(i)) {
			return;
		}
		Set<Integer> l = bytes.keySet();
		for (int e : l) {
			if (e > i) {
				byte x = bytes.get(e);
				bytes.put(e - 1, x);
				bytes.remove(e);
			}
		}

		l = byte_arrays.keySet();
		for (int e : l) {
			if (e > i) {
				byte[] x = byte_arrays.get(e);
				byte_arrays.put(e - 1, x);
				byte_arrays.remove(e);
			}
		}

		l = ints.keySet();
		for (int e : l) {
			if (e > i) {
				int x = ints.get(e);
				ints.put(e - 1, x);
				ints.remove(e);
			}
		}

		l = longs.keySet();
		for (int e : l) {
			if (e > i) {
				long x = longs.get(e);
				longs.put(e - 1, x);
				longs.remove(e);
			}
		}

		l = floats.keySet();
		for (int e : l) {
			if (e > i) {
				float x = floats.get(e);
				floats.put(e - 1, x);
				floats.remove(e);
			}
		}

		l = doubles.keySet();
		for (int e : l) {
			if (e > i) {
				double x = doubles.get(e);
				doubles.put(e - 1, x);
				doubles.remove(e);
			}
		}

		l = shorts.keySet();
		for (int e : l) {
			if (e > i) {
				short x = shorts.get(e);
				shorts.put(e - 1, x);
				shorts.remove(e);
			}
		}

		l = chars.keySet();
		for (int e : l) {
			if (e > i) {
				char x = chars.get(e);
				chars.put(e - 1, x);
				chars.remove(e);
			}
		}

		l = strings.keySet();
		for (int e : l) {
			if (e > i) {
				String x = strings.get(e);
				strings.put(e - 1, x);
				strings.remove(e);
			}
		}

		l = children.keySet();
		for (int e : l) {
			if (e > i) {
				ACoffeeHousingNode x = children.get(e);
				children.put(e - 1, x);
				children.remove(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.storage.ACoffeeHousingNode#serialize(coffee.weneed.utils.io.CoffeeWriter)
	 */
	@Override
	protected void serialize(CoffeeWriter cw) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see coffee.weneed.utils.dataholders.IByteArrayDataHolder#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return null;
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

		for (int i : this.bytes.keySet()) {
			bytes.put(String.valueOf(i), this.bytes.get(i));
		}
		for (int i : this.byte_arrays.keySet()) {
			byte_arrays.put(String.valueOf(i), HexUtil.getHexFromBytes(this.byte_arrays.get(i)));
		}
		for (int i : this.ints.keySet()) {
			ints.put(String.valueOf(i), this.ints.get(i));
		}
		for (int i : this.longs.keySet()) {
			longs.put(String.valueOf(i), this.longs.get(i));
		}
		for (int i : this.floats.keySet()) {
			floats.put(String.valueOf(i), this.floats.get(i));
		}
		for (int i : this.doubles.keySet()) {
			doubles.put(String.valueOf(i), this.doubles.get(i));
		}
		for (int i : this.shorts.keySet()) {
			shorts.put(String.valueOf(i), this.shorts.get(i));
		}
		for (int i : this.chars.keySet()) {
			chars.put(String.valueOf(i), this.chars.get(i));
		}
		for (int i : this.strings.keySet()) {
			strings.put(String.valueOf(i), this.strings.get(i));
		}
		for (int i : this.children.keySet()) {
			children.put(String.valueOf(i), this.children.get(i).toJSON());
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
