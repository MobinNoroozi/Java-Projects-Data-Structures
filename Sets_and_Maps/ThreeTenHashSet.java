import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;
/**
 * Generic type hash set implementation using linked list.
 * @author Mobin Noroozi
 * @param <V> Specific type of element that the user will input.  
 */
public class ThreeTenHashSet<V> implements Set<V> {
	/**
	 * Sets the default number of slots for the array.
	 */
	public static int DEFAULT_SLOTS = 10;
	/**
	 * Initialize the node array named storage.
	 */
	private Node<V>[] storage;
	
	//------------------------------------------------
	// Add any PRIVATE instance variables you want here!
	//------------------------------------------------
	/**
	 * Keeps track of the size of the storage.
	 */
	private int size = 0; // Keeps track of the size.
	
	/**
	 * Keeps track of the load factor of the storage.
	 */
	private double permittedLoad; // Keeps track of the load factor.

	/**
	 * Keeps track of the capacity of the storage. 
	 */
	private int capacity; //Keeps track of the capacity.

	/**
	 * Calculate the hash code of the object, and returns the possible index.
	 * @param obj the object that the function will compute the index for.
	 * @return the index of the object in the array. 
	 */
	private int getIndex(Object obj){
		return Math.abs(obj.hashCode()) % capacity; 
	}
	
	/**
	 * Node class which provide the skeleton for the node.
	 * @param <T> is the type of the data that each node will store.
	 */
	public static class Node<T> {

		/**
		 * Stores the data of in the node that is a generic type.
		 */
		public T data; // Data is the data that the node stores.

		/**
		 * Stores the next node.
		 */
		public Node<T> next;
		/**
		 * Default constructor.
		 */
		public Node() { }
		/**
		 * Constructor with data input only, and stores the next node as null.
		 * @param data the data to store in the node.
		 */
		public Node(T data) { this(data, null); }
		/**
		 * Constructor with data and next node input.
		 * @param data the data to store in the node.
		 * @param next the next node in the linked list.
		 */
		public Node(T data, Node<T> next) {
			this.data = data;
			this.next = next;
		}
	}

	/**
	 * Return the number of the elements in the set.
	 * @return the number of the elements in the set.
	 *  */	
	@Override
	public int size() {
		return size;
	}

	/**
	 * Checks if the set contains the input object.
	 * @param obj the object that we try to find in the set
	 * @return true if we find the object, if not, it returns false.
	 */
	@Override
	public boolean contains(Object obj) {
		int index = getIndex(obj); // Get the index of the inputed object.
		Node<V> current = storage[index]; // We create a current node and we point it to the index of the object so we can check, if it is in that linked list or not.
		while(current != null){ // We iterate through each node.
			if(current.data.equals(obj)){ // If the data of the node equakls to the input object, we return true.
				return true;
			}
			current = current.next;
		}
		return false; // Else, returns false.
	}

	/**
	 * Returns an iterator over the elements in the set. 
	 * The iterator throws ConcurrentModificationException if the set is modified after the iterator is created.
	 * @return an iterator over the set elements.
	 */
	@Override
	public Iterator<V> iterator() {
		return new Iterator<>() {

			int counter = 0; // Tracks how many elements we have visited. 
			Node<V> current = null; // Points to the current node in the list.
			int index = 0; // Keeps track of the index of the current, and we start from 0/
			int expectedSize = size; // We use this variable to see if the size has been changed or not.

			/**
			 * Checks if the there are more elmenet to iterate over or not.
			 * @return true of the there are more elements to iterate over in the list.
			 */
			@Override
			public boolean hasNext() {
				return (counter < expectedSize); // If the counter is less than the expected size, then there are more elements to iterate over. 
			}

			/**
			 * Iterate over the next element in the list.
			 * @return the element that we just iterated over.
			 */
			@Override
			public V next() {
				if (expectedSize != size) { // If the size has been changed, throw ConcurrentModificationException. 
					throw new ConcurrentModificationException();
				}
				
				while (current == null && index < storage.length) { // Means either we are starting at a null head, or we finished a chain, and we need to move to a different chain.
					current = storage[index++];
				}
	
				
				V result = current.data; // Holds the current data we want to return.
				current = current.next;
				counter++; // Increment this so hasNext() can use this and track the process.
				return result; 
			}
		};
	}

	/**
	 * Adds an item to the set.
	 * @param value the value to add.
	 * @return true of the insertion was successful. If not, return false.
	 */
	@Override
	public boolean add(V value) {
		if(contains(value) == true){ // If the value already exist in the set, it returns false because we do not need to add it.
			return false;
		}
		int index = getIndex(value); // Get the index and create a node at that index.
		Node<V> current = storage[index];
		if (current == null){ // If the node very first node is empty, then we add the value, to the very first node.
			Node<V> newNode = new Node<>(value);
			storage[index] = newNode;
			size++; // After adding we increment the size.
		} else{
			Node<V> newNode = new Node<>(value); // If there are item in the list, we add this value to the begining of the linked list.
			newNode.next = current;
			storage[index] = newNode;
			size++;
		}

		while(((double)size/capacity) > permittedLoad){ // After adding, if the load is larger than the permitted load, we rehash until we need to.
			rehash(2*capacity); // We keep doing that as long as we need to.
		}
		
		return true;
	}

	/**
	 * Remove a specific object from the set.
	 * @return true if we were able to remvove the item, and it returns false, if we were not.
	 * @param obj is the object we want to remove from the set.
	 */
	@Override
	public boolean remove(Object obj) {
		if(this.contains(obj) == false){ // If the object is not in the set, return false.
			return false;
		}
		int index = getIndex(obj);
		Node<V> current = storage[index]; // Get the index, create a node and go to that index.
		if(storage[index].data.equals(obj)){ // If the data in the very first node in the list equals to the obj, then just point to the next node.
			storage[index] = storage[index].next; // This will delete the node.
			size--; // Decrease the size after removing and return true.
			return true;
		}
		while(current.next != null){ // We iterate over the list, until we find the data in the node.
			if(current.next.data.equals(obj)){
				Node<V> newNode = current.next.next; // We create a node that points to the next node that the data is in there.
				current.next = newNode; // Then we assign the current to two nodes ahead. That is how we remove that node.
				size--;
				return true;
				
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Remove everything from the set, and reset the size to the default capacity.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void clear() {
		Node<V>[] newStorage = new Node[DEFAULT_SLOTS]; // Creates a new array of nodes with the default storage. 
		storage = newStorage; // It assigns the storage to the new empty storage.
		capacity = newStorage.length; // We update the size and capacity.
		size = 0;
	
	}
	
	//------------------------------------------------
	// Additional required methods for this project.
	//------------------------------------------------
	
	/**
	 * Returns the capacity of the storage.
	 * @return the capacity of the storage.
	 */
	public int getNumSlots() {
		return capacity;
	}
	
	/**
	 * It search in the list to find an object, and if we find one, it returns the data. 
	 * @param obj is the object to search for.
	 * @return the object if we find a match. If we do not, it returns null.
	 */
	public V get(Object obj) {
		int index = getIndex(obj);
		Node<V> current = storage[index]; // Go the index that we believe the object is there.
		while(current != null){ // Iterate through each node, and if we find a match, return the data.
			if(current.data.equals(obj)){
				return current.data;
			}
			current = current.next;
		}
		return null;
	}
	
	/**
	 * Construct a hash set with a specific permitted load.
	 * @param permittedLoad the permitted load factor set by user during declaring the hash set.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashSet(double permittedLoad) {
		storage = new Node[DEFAULT_SLOTS]; // Declare the storage with default size.
		this.permittedLoad = permittedLoad;
		this.capacity = DEFAULT_SLOTS; // Capacity here will be default slots, but it can be changed later. 
	}

	/**
	 * Iterates through every element in the list, and rehash them into the new storage with a new size.
	 * @param newNumSlots the new capacity of the new storage.
	 * @return true if re-hashing was successful. It returns false, if it wasn't.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int newNumSlots) {
		if(newNumSlots < 1){ // You cannot rehash with a list smaller than 1.
			return false;
		}
		Node<V>[] oldStorage = storage; // Make a copy of the old storage.
		storage = new Node[newNumSlots]; // Update the new storage with the capacity.
		int oldCapacity = capacity;
		capacity = newNumSlots;

		for(int i = 0; i < oldCapacity; i++){
			Node<V> current = oldStorage[i]; // We start at the beginning of the old storage and will iterate over every element.
			while(current != null){
				int newIndex = getIndex(current.data); // Get the new index.
				if(storage[newIndex] == null){ // Try to add it to the new list. If the very first node of the array is null, make it the new data. 
					Node<V> newNode = new Node<>(current.data);
					storage[newIndex] = newNode;
				}else{
					Node<V> currentTwo = storage[newIndex]; // This one iterate over the nodes in the new storage. 
					Node<V> newNode = new Node<>(current.data);
					newNode.next = currentTwo;
					storage[newIndex] = newNode;
				}
				current = current.next;
			}

		}
		return true;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	
	/**
	 * The main method for testing the code.
	 * @param args is the CLA element.
	 */
	public static void main(String[] args) {
		ThreeTenHashSet<String> str = new ThreeTenHashSet<>(1);
		
		if(str.getNumSlots() == 10 && str.size() == 0) {
			System.out.println("Yay 1");
		}
		System.out.println(str.toStringDebug()); //does not use iterator
		System.out.println(str); //uses iterator
		
		if(str.add("apple") && str.add("banana") && str.add("coconut") && !str.add("banana") && str.size() == 3) {
			System.out.println("Yay 2");
		}
		System.out.println(str.toStringDebug()); //does not use iterator
		System.out.println(str); //uses iterator

		for(int i = 0; i < 7; i++) str.add(""+i);
		if(str.getNumSlots() == 10 && str.size() == 10 && str.add("7") && str.getNumSlots() == 20) {
			System.out.println("Yay 3");
		}
		if(str.get("banana") != null){
			System.out.println("Yay4");
		}else{
			System.out.println("Nope");
		}
		System.out.println(str.toStringDebug()); //does not use iterator
		System.out.println(str); //uses iterator
	}
	
	//------------------------------------------------
	// Provided methods for this project (DO NOT CHANGE!)
	//------------------------------------------------
	
	@Override
	public boolean addAll(Collection<? extends V> c) {
		boolean ret = false;
		for(V item : c) if(add(item)) ret = true;
		return ret;
	}

	@Override
	public boolean isEmpty() {
		return (size() == 0);
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object item : c) if(!contains(item)) return false;
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for(Object item : c) if(remove(item)) ret = true;
		return ret;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{");

		for(V item : this) {
			s.append(item);
			s.append(",");
		}

		if(size() > 0) s.setCharAt((s.length()-1), '}');
		else s.append("}");
		return s.toString().trim();
	}
	/**
	 * To String Debug.
	 * @return string.
	 */
	public String toStringDebug() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			s.append("[");
			s.append(i);
			s.append("]");
			Node<V> curr = storage[i];
			while(curr != null) {
				s.append("-->");
				s.append(curr.data.toString());
				curr = curr.next;
			}
			s.append("\n");
		}
		return s.toString().trim();
	}
	
	//------------------------------------------------
	// Unsupported Operations for this project
	//------------------------------------------------

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
}