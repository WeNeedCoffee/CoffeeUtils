package coffee.weneed.utils.datatypes;

import java.util.Map.Entry;

// TODO: Auto-generated Javadoc
/**
 * The Class CoffeeEntry.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class CoffeeEntry<K, V> implements Entry<K, V> {
	
	/** The key. */
	private K key;
	
	/** The value. */
	private V value;

	/**
	 * Instantiates a new coffee entry.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public CoffeeEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	public K getKey() {
		return key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	@Override
	public V getValue() {
		return value;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the key
	 * @return the k
	 */
	public K setKey(K key) {
		K k = this.key;
		this.key = key;
		return k;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the value
	 * @return the v
	 */
	@Override
	public V setValue(V value) {
		V val = this.value;
		this.value = value;
		return val;
	}

}
