package coffee.weneed.utils;

import java.util.Map.Entry;

public interface ICoffeeEntry<K, V> extends Entry<K, V> {
	
	public K setKey(K key);
}
