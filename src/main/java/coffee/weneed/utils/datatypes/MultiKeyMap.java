package coffee.weneed.utils.datatypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * MultiKeyHashMap provides to store values with two level hierarchy of keys,
 * super key (K1) and sub key (K2). The objects are inserted using super and sub
 * keys. It is not mandatory to use both keys as hierarchy, user can use two
 * keys to store the values and use either of the key to retrieve it. Original
 * taken from https://gist.github.com/prathabk/1587699 by Prathab K @
 * https://github.com/prathabk
 *
 * @author Prathab K, Dalethium
 * @param <K1> the generic type
 * @param <K2> the generic type
 * @param <V>  the value type
 */
public class MultiKeyMap<K1, K2, V> {

	/**
	 * Map structure holding another Map structure to implement MultiKeyHashMap.
	 */
	private Map<K1, Map<K2, V>> mkMap;

	/**
	 * Initializes the MultiKeyHashMap.
	 */
	public MultiKeyMap() {
		mkMap = new HashMap<>();
	}

	/**
	 * Clears the entire hash map.
	 */
	public void clear() {
		for (Map<K2, V> m : mkMap.values()) {
			m.clear();
		}
		mkMap.clear();
	}

	/**
	 * Returns <tt>true</tt> if value object is present for the specified
	 * (super)key K1.
	 *
	 * @param k1 key1 (super-key)
	 * @return <tt>true</tt> if value object present
	 */
	public boolean containsKey(K1 k1) {
		return mkMap.containsKey(k1);
	}

	/**
	 * Returns <tt>true</tt> if value object is present for the specified
	 * (super)key K1 and (sub)key K2.
	 *
	 * @param k1 key1 (super-key)
	 * @param k2 key2 (sub-key)
	 * @return <tt>true</tt> if value object present
	 */
	public boolean containsKey(K1 k1, K2 k2) {
		if (mkMap.containsKey(k1)) {
			Map<K2, V> k2Map = mkMap.get(k1);
			return k2Map.containsKey(k2);
		}
		return false;
	}

	/**
	 * Entry set.
	 *
	 * @return the sets the
	 */
	public Set<Entry<K1, Map<K2, V>>> entrySet() {
		return mkMap.entrySet();
	}

	/**
	 * Gets the value object for the specified (super)key K1.
	 *
	 * @param k1 key1 (super-key)
	 * @return HashMap structure contains the values for the key k1 if exists or
	 *         <tt>null</tt> if does not exists
	 */
	public Map<K2, V> get(K1 k1) {
		return mkMap.get(k1);
	}

	/**
	 * Gets the value object for the specified (super)key K1 and (sub)key K2.
	 *
	 * @param k1 key1 (super-key)
	 * @param k2 key2 (sub-key)
	 * @return value object if exists or <tt>null</tt> if does not exists
	 */
	public V get(K1 k1, K2 k2) {
		if (mkMap.containsKey(k1)) {
			Map<K2, V> k2Map = mkMap.get(k1);
			return k2Map.get(k2);
		}
		return null;
	}

	/**
	 * Returns all the value objects in the MultiKeyHashMap.
	 *
	 * @return value objects as List
	 */
	public List<V> getAllItems() {
		List<V> items = new ArrayList<>();
		for (Map<K2, V> m : mkMap.values()) {
			for (V v : m.values()) {
				items.add(v);
			}
		}
		return items;
	}

	/**
	 * Gets the value object for the specified (sub)key K2.
	 *
	 * @param k2 key2 (sub-key)
	 * @return value object if exists or <tt>null</tt> if does not exists
	 */
	public V getBySubKey(K2 k2) {
		for (Map<K2, V> m : mkMap.values()) {
			if (m.containsKey(k2)) {
				return m.get(k2);
			}
		}
		return null;
	}

	/**
	 * Puts the value object based on the (super)key K1 and (sub)key K2.
	 *
	 * @param k1 key1 (super-key)
	 * @param k2 key2 (sub-key)
	 * @param v  value object
	 * @return previous value associated with specified key, or <tt>null</tt> if
	 *         there was no mapping for key.
	 */
	public V put(K1 k1, K2 k2, V v) {
		Map<K2, V> k2Map = null;
		if (mkMap.containsKey(k1)) {
			k2Map = mkMap.get(k1);
		} else {
			k2Map = new HashMap<>();
			mkMap.put(k1, k2Map);
		}
		return k2Map.put(k2, v);
	}

	/**
	 * Put all.
	 *
	 * @param key the key
	 * @param map the map
	 */
	public void putAll(K1 key, Map<K2, V> map) {
		for (Entry<K2, V> item : map.entrySet()) {
			put(key, item.getKey(), item.getValue());
		}
	}

	/**
	 * Put all.
	 *
	 * @param map the map
	 */
	public void putAll(Map<K1, Map<K2, V>> map) {
		for (Entry<K1, Map<K2, V>> entry : map.entrySet()) {
			for (Entry<K2, V> item : entry.getValue().entrySet()) {
				put(entry.getKey(), item.getKey(), item.getValue());
			}
		}
	}

	/**
	 * Put all.
	 *
	 * @param map the map
	 */
	public void putAll(MultiKeyMap<K1, K2, V> map) {
		for (Entry<K1, Map<K2, V>> entry : map.entrySet()) {
			for (Entry<K2, V> item : entry.getValue().entrySet()) {
				put(entry.getKey(), item.getKey(), item.getValue());
			}
		}
	}

	/**
	 * Removes the value object for the specified (super)key K1 and (sub)key K2.
	 *
	 * @param k1 key1 (super-key)
	 * @param k2 key2 (sub-key)
	 * @return previous value associated with specified key, or <tt>null</tt> if
	 *         there was no mapping for key.
	 */
	public V remove(K1 k1, K2 k2) {
		if (mkMap.containsKey(k1)) {
			Map<K2, V> k2Map = mkMap.get(k1);
			return k2Map.remove(k2);
		}
		return null;
	}

	/**
	 * Size of MultiKeyHashMap.
	 *
	 * @return MultiKeyHashMap size
	 */
	public int size() {
		int size = 0;
		for (Map<K2, V> m : mkMap.values()) {
			size++;
			size += m.size();
		}
		return size;
	}

}