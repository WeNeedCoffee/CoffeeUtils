package coffee.weneed.utils.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.json.JSONObject;
import coffee.weneed.utils.StringUtil;
import coffee.weneed.utils.io.CoffeeAccessor;
import coffee.weneed.utils.io.CoffeeWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class CoffeeHousingList.
 */
public class CoffeeHousingList extends ACoffeeHousingNode implements List<Object> {

	/** The bytes. */
	protected List<Object> items = new ArrayList<>();

	/**
	 * Instantiates a new coffee housing list.
	 *
	 * @param parent the parent
	 */
	public CoffeeHousingList(ACoffeeHousingNode parent) {
		super(parent);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, Object element) {
		items.add(index, element);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(Object e) {
		return items.add(e);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<?> c) {
		return items.addAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<?> c) {
		return items.addAll(index, c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		items.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return items.contains(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return items.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.storage.ACoffeeHousingNode#deserialize(coffee.weneed.
	 * utils.io.CoffeeAccessor)
	 */
	@Override
	protected void deserialize(CoffeeAccessor lea) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.dataholders.IByteArrayDataHolder#fromByteArray(byte[]
	 * )
	 */
	@Override
	public void fromByteArray(byte[] b) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#get(int)
	 */
	@Override
	public Object get(int index) {
		return items.get(index);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object o) {
		return items.indexOf(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#iterator()
	 */
	@Override
	public Iterator<Object> iterator() {
		return items.iterator();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object o) {
		return items.lastIndexOf(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<Object> listIterator() {
		return items.listIterator();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<Object> listIterator(int index) {
		return items.listIterator(index);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#remove(int)
	 */
	@Override
	public Object remove(int index) {
		return items.remove(index);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return items.remove(o);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		return items.removeAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		return items.retainAll(c);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * coffee.weneed.utils.storage.ACoffeeHousingNode#serialize(coffee.weneed.
	 * utils. io.CoffeeWriter)
	 */
	@Override
	protected void serialize(CoffeeWriter lew) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public Object set(int index, Object element) {
		return items.set(index, element);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		return items.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<Object> subList(int fromIndex, int toIndex) {
		return items.subList(fromIndex, toIndex);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#toArray()
	 */
	@Override
	public Object[] toArray() {
		return items.toArray();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return items.toArray(a);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see coffee.weneed.utils.dataholders.IByteArrayDataHolder#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
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
		Map<Integer, Byte> tbytes = new HashMap<>();
		Map<Integer, byte[]> tbyte_arrays = new HashMap<>();
		Map<Integer, Integer> tints = new HashMap<>();
		Map<Integer, Long> tlongs = new HashMap<>();
		Map<Integer, Float> tfloats = new HashMap<>();
		Map<Integer, Double> tdoubles = new HashMap<>();
		Map<Integer, Short> tshorts = new HashMap<>();
		Map<Integer, Character> tchars = new HashMap<>();
		Map<Integer, String> tstrings = new HashMap<>();
		Map<Integer, ACoffeeHousingNode> tchildren = new HashMap<>();
		int k = 0;
		for (Object o : items.toArray()) {
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
			k++;
		}
		for (int i : tbytes.keySet()) {
			bytes.put(String.valueOf(i), tbytes.get(i));
		}
		for (int i : tbyte_arrays.keySet()) {
			byte_arrays.put(String.valueOf(i), StringUtil.bytesToHex(tbyte_arrays.get(i)));
		}
		for (int i : tints.keySet()) {
			ints.put(String.valueOf(i), tints.get(i));
		}
		for (int i : tlongs.keySet()) {
			longs.put(String.valueOf(i), tlongs.get(i));
		}
		for (int i : tfloats.keySet()) {
			floats.put(String.valueOf(i), tfloats.get(i));
		}
		for (int i : tdoubles.keySet()) {
			doubles.put(String.valueOf(i), tdoubles.get(i));
		}
		for (int i : tshorts.keySet()) {
			shorts.put(String.valueOf(i), tshorts.get(i));
		}
		for (int i : tchars.keySet()) {
			chars.put(String.valueOf(i), tchars.get(i));
		}
		for (int i : tstrings.keySet()) {
			strings.put(String.valueOf(i), tstrings.get(i));
		}
		for (int i : tchildren.keySet()) {
			children.put(String.valueOf(i), tchildren.get(i).toJSON());
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
