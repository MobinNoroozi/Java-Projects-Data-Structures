import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ThreeTenHashMap<K,V> implements Map<K,V> {
	//------------------------------------------------
	// You may NOT add any additional instance variables
	//------------------------------------------------
	
	//you must use this storage for the hash table
	//and you may not alter this variable's name, type, etc.
	private ThreeTenHashSet<Pair<K,V>> storage;

	//------------------------------------------------
	// Inner entry class. You need to finish this!
	//------------------------------------------------
	public static class Pair<K,V> implements Map.Entry<K,V> {
		//you can/should add some instance variables here
		private K key;
		private V value;
		
		public Pair(K key, V value) {
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

		@Override
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}
		
		@Override
		public String toString() {
			//this method is done
			return getKey().toString()+":"+getValue().toString();
		}

		// FIXME ###
		@Override
		public int hashCode() {
			if (key == null){
				return 0;
			}else{
				return key.hashCode();
			}	
		}

		// FIXME ###
		@Override
		public boolean equals(Object o) {
			Pair<?, ?> other = (Pair<?, ?>) o;
			return this.key.equals(other.key);

		}
	}
	
	//------------------------------------------------
	// Required methods of a Java Map for this project
	// For method specifications, see: https://docs.oracle.com/javase/9/docs/api/java/util/Map.html
	//------------------------------------------------

	@Override
	public V get(Object key) {
		Pair<K, V> testPair = new Pair<>((K) key, null);
		for(Pair<K, V> p : storage){
			if (p.equals(testPair)){
				return p.getValue();
			}
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		Pair<K, V> newPair = new Pair<>(key, value);
		for (Pair<K, V> p : storage){
			if (p.equals(newPair)){
				return p.setValue(value);
			}
		}
		storage.add(newPair);
		return null;
		//my solution to this is ~5 lines long,
		//any your solution shouldn't need to
		//be much longer!
	}

	@Override
	public V remove(Object key) {
		Pair<K, V> testPair = new Pair<>((K) key, null);
		for(Pair<K, V> p : storage){
			if (p.equals(testPair)) {
				storage.remove(p);
				return p.getValue();
			}
		}
		//You may assume the key will not be null.
		return null;
		
		//my solution to this is ~4 lines long,
		//any your solution shouldn't need to
		//be much longer!
	}
	
	@Override
	public boolean containsKey(Object key) {
		//You may assume the key will not be null.
		//return false;
		return get(key) != null;

		
		//my solution to this is 1 line long,
		//any your solution shouldn't need to
		//be much longer!
	}

	@Override
	public boolean containsValue(Object value) {
		for (Pair<K, V> p : storage) {
			if (p.getValue().equals(value)) {
				return true;
			}
		}
		return false;
		
		//my solution to this is ~4 lines long,
		//any your solution shouldn't need to
		//be much longer!
	}
	
	//------------------------------------------------
	// Additional required methods for this project
	//------------------------------------------------
	
	public ThreeTenHashMap(double permittedLoad) {
		storage = new ThreeTenHashSet<>(permittedLoad);
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	
	public static void main(String[] args) {
		ThreeTenHashMap<String,Integer> map = new ThreeTenHashMap<>(1);
		
		if(map.getNumSlots() == 10 && map.size() == 0) {
			System.out.println("Yay 1");
		}
		System.out.println(map.toStringDebug()); //does not use iterator
		System.out.println(map); //uses iterator
		
		if(map.put("apple",1) == null && map.put("banana",1) == null && map.put("coconut",2) == null && map.put("banana",3) == 1 && map.size() == 3) {
			System.out.println("Yay 2");
		}
		System.out.println(map.toStringDebug()); //does not use iterator
		System.out.println(map); //uses iterator

		for(int i = 0; i < 7; i++) map.put(""+i,i);
		if(map.getNumSlots() == 10 && map.size() == 10 && map.put("7",7) == null && map.getNumSlots() == 20) {
			System.out.println("Yay 3");
		}
		System.out.println(map.toStringDebug()); //does not use iterator
		System.out.println(map); //uses iterator
	}
	
	//------------------------------------------------
	// Provided methods for this project (DO NOT CHANGE!)
	//------------------------------------------------
	
	@Override
	public int size() {
		return storage.size();
	}

	@Override
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public String toString() {
		return storage.toString();
	}

	public String toStringDebug() {
		return storage.toStringDebug();
	}

	public int getNumSlots() {
		return storage.getNumSlots();
	}

	public boolean rehash(int newNumSlots) {
		return storage.rehash(newNumSlots);
	}
	
	//------------------------------------------------
	// Unsupported Operations for this project
	//------------------------------------------------
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}
}