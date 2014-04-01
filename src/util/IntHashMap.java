package util;

import java.util.Iterator;

/**
 * The IntHashMap is a data structure that maps keys to ints in a hash table.
 * It performs basic operations in amortized constant time.
 * 
 * @author Ravi
 * @version 032314
 * @param <K>	key type
 */
public class IntHashMap<K> {

	private static final int defaultInitialCapcity = 16;
	private static final double defaultLoadFactor = 0.75;
	
	//Instance fields
	private LinkedList<IntEntry>[] array;
	private int size;
	private int initialCapacity;
	private double loadFactor;
	
	/**
	 * Creates a new IntHashMap with default initial capacity (16) and default
	 * load factor (0.75).
	 */
	public IntHashMap() {
		this(defaultInitialCapcity, defaultLoadFactor);
	}
	
	/**
	 * Creates a new IntHashMap with specified initial capacity and default load
	 * factor (0.75).
	 * 
	 * @param initialCapacity	the initial capacity of this HashMap's array
	 */
	public IntHashMap(int initialCapacity) {
		this(initialCapacity, defaultLoadFactor);
	}
	
	/**
	 * Creates a new IntHashMap with specified initial capacity and load factor
	 * 
	 * @param initialCapacity	the initial capacity of this HashMap's array
	 * @param loadFactor		the max load factor
	 * @param loadFactor
	 */
	@SuppressWarnings("unchecked")
	public IntHashMap(int initialCapacity, double loadFactor) {
		array = new LinkedList[initialCapacity];
		this.initialCapacity = initialCapacity;
		this.loadFactor = loadFactor;
	}
	
	/**
	 * Removes all of the items from this map.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		array = new LinkedList[initialCapacity];
		size = 0;
	}
	
	/**
	 * Returns true if the specified key exists in this map
	 * 
	 * @param key	key to test
	 * @return	true if exists in map, false otherwise
	 */
	public boolean containsKey(Object key) { 
		LinkedList<IntEntry> list = array[bucketIndex(key)];
		if(list == null)
			return false;
		for(IntEntry e : list) {
			if(e.key.equals(key))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the specified value exists in this map
	 * 
	 * @param value		value to test
	 * @return	true if exists in map, false otherwise
	 */
	public boolean containsValue(int value) { 
		for(int i = 0; i < array.length; i++) {
			LinkedList<IntEntry> list = array[i];
			if(list != null) {
				for(IntEntry e : list) {
					if(e.value == value)
						return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the value associated with the given key in this map and 0 if
	 * the key does not exist in this map.
	 * <p>
	 * NB, a return value of 0 does not necessarily mean the key does not
	 * exist in this map, the associated value could be 0.
	 * 
	 * @param key	key who's value to return
	 * @return	the value associated with specified key
	 */
	public int get(Object key) { 
		LinkedList<IntEntry> list = array[bucketIndex(key)];
		if(list == null)
			return 0;
		for(IntEntry e : list) {
			if(e.key.equals(key))
				return e.value;
		}
		return 0;
	}
	
	/**
	 * Returns true if empty, false otherwise.
	 * 
	 * @return	true if no entries, false otherwise
	 */
	public boolean isEmpty() { 
		return size == 0;
	}
	
	/**
	 * Adds the specified key-value pair to this map. If the key is previously
	 * associated with a value, overwrites that value.
	 * 
	 * @param key		key to add to map
	 * @param value		value to associate with key
	 * @return	previous value associated with key if exists; null otherwise
	 */
	public int put(K key, int value) {
		checkLoadFactor();
		int index = bucketIndex(key);
		LinkedList<IntEntry> list = array[index];
		if(list == null) {
			list = new LinkedList<IntEntry>();
			array[index] = list;
		}
		
		for(IntEntry e : list) {
			if(e.key.equals(key)) {
				e.value = value;
				return value;
			}
		}
		list.add(new IntEntry(key, value));
		size++;
		return value;
	}
	
	/**
	 * Removes the specified key from this map and returns its associated
	 * value, if the key exists
	 * 
	 * @param key	key to remove
	 * @return	the value associated with key if exists; null otherwise
	 */
	public int remove(Object key) { 
		LinkedList<IntEntry> list = array[bucketIndex(key)];
		if(list == null)
			return 0;
		
		for(Iterator<IntEntry> i = list.iterator(); i.hasNext(); ) {
			IntEntry e = i.next();
			if(e.key.equals(key)) {
				i.remove();
				return e.value;
			}
		}
		return 0;
	}
	
	/**
	 * Returns the number of entries in this HashMap.
	 * 
	 * @return	number of entries
	 */
	public int size() { 
		return size;
	}
	
	/**
	 * Prints out every key-value pair in this HashMap, in no particular order.
	 * 
	 * @return	String representation of this HashMap
	 */
	public String toString() { 
		String toReturn = "{";
		for(int i = 0; i < array.length; i++) {
			LinkedList<IntEntry> list = array[i];
			if(list != null) {
				for(IntEntry e : list) {
					if(!toReturn.equals("{"))
						toReturn += ", ";
					toReturn += e.key + "=" + e.value;
				}
			}
		}
		
		return toReturn + "}";
	}
	
	/**
	 * Compresses a hash code to fit in the array.
	 * 
	 * @param hashCode	hashCode of an object
	 * @return	compressed code for array index
	 */
	private int bucketIndex(Object o) {
		int compressed = (int)(((127L * (long)o.hashCode() + 66029L) % 1999993L) % array.length);
		if (compressed < 0)
			compressed += array.length;
		return compressed;
	}
	
	@SuppressWarnings("unchecked")
	private void checkLoadFactor() {
		if((double)size / (double)array.length > loadFactor) {
			LinkedList<IntEntry>[] oldArray = array;
			array = new LinkedList[array.length*2];
			size = 0;
			
			for(int i = 0; i < oldArray.length; i++) {
				LinkedList<IntEntry> list = oldArray[i];
				if(list != null) {
					for(IntEntry e : list)
						put((K)e.key, e.value);
				}
			}
		}
	}
	
}
