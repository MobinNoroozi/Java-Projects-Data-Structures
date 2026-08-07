import java.util.AbstractCollection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This class implement the double-ended queue (deque) with dynamic list.
 * @author Mobin Noroozi
 * @param <V> the type of element that this deque holds, and it will be initialized during decleration.
 */
public class ThreeTenArrayDeque<V> extends AbstractCollection<V> implements Deque<V> {
	//don't change this!
	/** This is the default capacity for the list. */
	public static int DEFAULT_CAPACITY = 4;
	/** Initialize an array of type V called storage. */
	private V[] storage;
	/** Sets the size to 0. */
	private int size = 0;
	
	//------------------------------------------------
	// Add any PRIVATE instance variables you want here!
	//------------------------------------------------
	/** This variable store the index of the front of the list.*/
	private int front = 0;
	/** This variable store the index of the back of the list.*/
	private int back = -1;

    /**
     * Adds an element to the front of the list.
     * @param value is the element you want to add to the list.
     */
    @Override
    public void addFirst(V value) {
		if (size == storage.length){
			setCapacity(2 * storage.length); // If the array is full, double the size of the array.
		}
		front = (front - 1 + storage.length) % storage.length; // Decrease the front of the list, meaning we want to go 1 before the current front.
		storage[front] = value; // Change the value of the new front.
		if (size == 0) {
			back = front; // If size was 0, then back and front will be the same index. 
		}
		size++; // Increase the size.
	}

    /**
     * Adds an element to the end of the list.
     * @param value is the element that we want to add to the end of the list.
     */
    @Override
    public void addLast(V value) {
		if (size == storage.length){
			setCapacity(2 * storage.length);
		}
		back = (back + 1) % storage.length; // We increase the back index. It wraps around if needed.
		storage[back] = value; // We set the value.
		size++;
    }

    /**
     * Removes the first element in the list.
     * @return the element that we just removed.
     */
    @Override
    public V removeFirst() {
		if (size == 0) {
			throw new NoSuchElementException(); // If the list is empty, we cannot remove. Thus, we throw an exception.
		}
		V removedValue = storage[front]; // Store the item we wanna remove.
		storage[front] = null; // Make that index null. This removed the element.
		front = (front + 1) % storage.length; // Increase the front. It wraps around if needed.
		size--;
		return removedValue; 
    }

    /**
    *Removes the last element in the list.
    *@return the element that was just removed.
    */
    @Override
    public V removeLast() {
		if (size == 0){
			throw new NoSuchElementException(); // If the list is empty, we cannot remove. Thus, we throw an exception.
		}
		V removedValue = storage[back]; // Store the item that will be removed.
		storage[back] = null; // Make that index null.
		back = (back - 1 + storage.length) % storage.length; // Decrease the back index. It wraps around if needed.
		size--;
		return removedValue;
    }

    /**
     * Returns the first element in the list.
     * @return the first element in the list.
     */
    @Override
    public V getFirst() {
		if(size == 0){
			throw new NoSuchElementException(); // If the list is empty, we can't return anything. Thus, we throw an exception.
		}
		return storage[front]; // Return the front of the list.
    }

    /**
     * Returns the last element in the list.
     * @return the last element in the list.
     */
    @Override
    public V getLast() {
		if(size == 0){
			throw new NoSuchElementException(); // If the list is empty, we can't return anything. Thus, we throw an exception.
		}
		return storage[back]; // Return the end of the list.
    }
	
    /** 
     * Reset the array to the default capacity and it will be empty.
    */
    @Override
    public void clear() {
		size = 0;
		setCapacity(DEFAULT_CAPACITY);
    }

	/**
	 * Return an iterator over the elements in the list from the front to the back.
	 * @return an iterator over the elements in the list from the front to the back.
	 */
	@Override
	public Iterator<V> iterator() {
		return new Iterator<V>(){
			/** We start at the first index. */
			private int index = 0;

			/**
			 * Checks to see if there are more elements to iterate over or not.
			 * @return true if there are more elements to iterate over. Otherwise, it returns false.
			 */
			@Override
			public boolean hasNext(){
				if(index < size){
					return true; // If index == size, we have eached the end of the list.
				}else{
					return false;
				}
			}

			/**
			 * Iterate over the element by one.
			 * @return the element that we just iterate over.
			 */
			@Override
			public V next(){
				if (!hasNext()){
					throw new NoSuchElementException(); // If no more elements, then you cannot iterate over it. Thus, throw an exception.
				}
				V value = storage[(front + index) % storage.length]; // Gets the value at the desired index.
				index++;
				return value;
			}
		};
	}

    /**
     * Return an iterator over the list from the back to the front (in reverse order).
     * @return Return an iterator over the list from the back to the front (in reverse order).
     */
    @Override
    public Iterator<V> descendingIterator() {
		return new Iterator<>() {
			/** We start at the last index. */
			private int index = size - 1;

			/**
			 * Checks to see if there are more elements to iterate over or not.
			 * @return true if there are more elements to iterate over. Otherwise, it returns false.
			 */
			@Override
			public boolean hasNext(){
				if( index >= 0){
					return true;
				}else{
					return false;
				}
			}
			/**
			 * Iterate over the element by one.
			 * @return the element that we just iterate over.
			 */
			@Override
			public V next(){
				if(!hasNext()){
					throw new NoSuchElementException(); // If we are at the end of the list, there are no more item to iterate over. Thus, we throw an exception.
				}
				V value = storage[(front + index) % storage.length]; // Get the value at that index.
				index--; // Decrease the index.
				return value;
			}
		};
    }
	
	/**
	 * Adds an element at the end of the queue.
	 * @param value is the element that you wish to add to the end of the list.
	 * @return true if we were able to add the element. Otherwise, it returns false.
	 */
	@Override
    public boolean add(V value) {
		addLast(value);
		return true;
	}
	
    /**
     * Removes an element from the begining of the queue.
     * @return the element that we just remvoed.
     */
    @Override
    public V remove() {
		return removeFirst();
    }

    /**
     * Peeks at the next element in the queue.
     * @return first element in the queue.
     */
    @Override
    public V element() {
		return getFirst();
    }

    /**
     * Adds an item to the top of the stack.
     * @param value is the element you want to add to the stack.
     */
    @Override
    public void push(V value) {
		addLast(value);
    }

    /**
     * Removes an element from the top of the stack.
     * @return the element that we just removed.
     */
    @Override
    public V pop() {
		return removeLast();
    }
	
	//------------------------------------------------
	// Additional required methods for this project
	//------------------------------------------------
	/**
	 * Constructor for the class.
	 * It declares the array with default capacity.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenArrayDeque() {
		storage = (V[]) new Object[DEFAULT_CAPACITY];
	}
	
	/**
	 * It resized the array if it is able to.
	 * @param newCap is the new capacity you wish to set your new array to.
	 * @return true if we were able to resize the array. Otherwise, it returns false.
	 */
	@SuppressWarnings("unchecked")
	public boolean setCapacity(int newCap) {
		if (newCap < size){ // If the new capacity is smaller than the current size, it returns false.
			return false;
		}
		
		// Creates a new array with the new capacity.
		V[] newStorage = (V[]) new Object[newCap];

		// For the size, set the index of the new storage to the old storage but starts from the front.
		for (int i = 0; i < size; i++) {
			newStorage[i] = storage[(front + i) % storage.length]; // This copies the old storage to the new storage from front to back.
		}

		storage = newStorage; // Set the storage to the new storage.
		front = 0; // Front is the 0.
		back = size - 1; // Size - 1 is the last full index. Thus, it will be the back.
		return true;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	
	/**
	 * Main for testing.
	 * @param args CLA.
	 */
	public static void main(String[] args) {
		ThreeTenArrayDeque<Integer> deque = new ThreeTenArrayDeque<>();
		
		deque.push(1);
		deque.push(2);
		deque.push(3);
		deque.push(4);
		deque.push(5);
		
		System.out.println(deque.size);
		System.out.println(deque.peekFirst());
		System.out.println(deque.peek());
		System.out.println(deque.toString());
		deque.pop();
		deque.pop();
		deque.pop();
		System.out.println(deque.size);
		System.out.println(deque.peekFirst());
		System.out.println(deque.peek());
		System.out.println(deque.toString());
		deque.push(1);
		deque.push(2);
		System.out.println(deque.toString());
		deque.clear();
		deque.push(3);
		deque.push(4);
		deque.push(5);
		deque.push(3);
		deque.push(4);
		deque.push(5);
		deque.push(3);
		deque.push(4);
		
		deque.clear();
		deque.push(555555);
		System.out.println(deque.toString());
	}

	
	//------------------------------------------------
	// Provided methods for this project (DO NOT CHANGE!)
	//------------------------------------------------
	
	@Override
	public int size() {
		return size;
	}
	/**
	 * Shows the capacity.
	 * @return the capacity.
	 */
	public int capacity() {
		return (storage == null) ? -1 : storage.length;
	}
	
    @Override
    public boolean offer(V value) {
        try { add(value); return true; }
		catch(IllegalStateException e) { return false; }
    }

    @Override
    public boolean offerFirst(V value) {
        try { addFirst(value); return true; }
		catch(IllegalStateException e) { return false; }
    }

    @Override
    public boolean offerLast(V value) {
        try { addLast(value); return true; }
		catch(IllegalStateException e) { return false; }
    }

    @Override
    public V poll() {
        try { return poll(); }
		catch(NoSuchElementException e) { return null; }
    }

    @Override
    public V pollFirst() {
        try { return removeFirst(); }
		catch(NoSuchElementException e) { return null; }
    }

    @Override
    public V pollLast() {
        try { return removeLast(); }
		catch(NoSuchElementException e) { return null; }
    }

    @Override
    public V peek() {
        try { return element(); }
		catch(NoSuchElementException e) { return null; }
    }

    @Override
    public V peekFirst() {
        try { return getFirst(); }
		catch(NoSuchElementException e) { return null; }
    }

    @Override
    public V peekLast() {
        try { return getLast(); }
		catch(NoSuchElementException e) { return null; }
    }
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for(V item : this) {
			s.append(item);
			s.append(",");
		}
		
		s.deleteCharAt(s.length()-1);
		return s.toString();
	}
	/**
	 * Reverse string.
	 * @return string.
	 */
	public String toStringRev() {
		StringBuilder s = new StringBuilder();
		
		Iterator<V> itr = descendingIterator();
		while(itr.hasNext()) {
			V item = itr.next();
			s.append(item);
			s.append(",");
		}

		s.deleteCharAt(s.length()-1);
		return s.toString();
	}
	

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }
}