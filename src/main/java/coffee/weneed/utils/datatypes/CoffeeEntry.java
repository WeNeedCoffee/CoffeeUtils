package coffee.weneed.utils.datatypes;

import java.util.Map.Entry;

public class CoffeeEntry<K, V> implements Entry<K, V> {
	private K key;
	private V value;

	public CoffeeEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	public K setKey(K key) {
		K k = this.key;
		this.key = key;
		return k;
	}

	@Override
	public V setValue(V value) {
		V val = this.value;
		this.value = value;
		return val;
	}

}
