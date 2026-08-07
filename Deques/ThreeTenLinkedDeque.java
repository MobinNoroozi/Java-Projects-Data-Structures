import java.util.AbstractCollection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This class implement the double-ended queue (deque) with singly linked list.
 * @author Mobin Noroozi
 * @param <V> the type of element that this deque holds, and it will be initialized during decleration.
 */
public class ThreeTenLinkedDeque<V> extends AbstractCollection<V> implements Deque<V> {
	/** Initialize a head node with the type V which will be determine during decleration.*/
	private Node<V> head;
	/** Keeps track of how many elements are in the deque.*/
	private int size = 0;
	
	//------------------------------------------------
	// Add any PRIVATE instance variables you want here!
	//------------------------------------------------
	/** Initialize a tail node with the type V which will be determine during decleration, and set it to null.*/
	Node<V> tail = null;
	
	
	//------------------------------------------------
	// Inner node class. You can add JavaDocs, but don't
	// change the class in ANY way!
	//------------------------------------------------
	/**
	 * This is the skeleton for a node.
	 * @param <T> the type of data that each node store.
	 */
	public static class Node<T> {
		/** The value that each node stores.*/
		public T value;
		/** A reference to the next node in the list.*/
		public Node<T> next;
		
		/** Construct an empty node. */
		public Node() { 

		}
		/**
		 * Construct a node with a value.
		 * @param value the value that this node stores.
		 */
		public Node(T value) {
			this(value, null); 
		}
		/**
		 * Construct a node with a value, and a reference to the next node.
		 * @param value is the value that this node stores.
		 * @param next is the reference to the next node.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}
	
	/**
	 * Insert an element at the front of the deque.
	 * @param value is the element you want to add to the deque.
	 */
	@Override
    public void addFirst(V value) {
		Node<V> newNode = new Node<>(value, head); // We add to the front, so we make a new node with the value and reference to the head.
    	head = newNode; // Then the head will be the new node that we just created.
		if (tail == null){ // If tail is null means there is no element in the list yet.
			tail = head; // Then we set the tail to head.
		}
		size++; // Increase the size adter inserting an element.
	}

    /**
     * Adds an item at the end of the deque.
     * @param value is the value you would like to add to the end of the deque.
     */
    @Override
    public void addLast(V value) {
		if (tail == null){ // If tail is null, it means that the list is empty.
			addFirst(value); // Thus, it does not matter if we add at the end or front, we just add item to the list.
		}else{ 
			Node<V> newNode = new Node<>(value); // If the list is not empty, create a new node with the value.
			tail.next = newNode; // assign the tail.next to the new node.
			tail = newNode; // Then make the new node the tail node.
			size++;
		}
    }

    /**
     * Removes the first element from the deque.
     * @return the item that was removed.
     */
    @Override
    public V removeFirst() {
		if (head == null){
			throw new NoSuchElementException(); // If the list is empty, throw NoSuchElementException.
		}
		if (head == tail){ 
			V data = head.value; // If there is only 1 element in the list, then gets its data.
			head = tail = null; // Assign both head and tail to the null.
			size--; // Decrease the size.
			return data; // Return the data.
		}
		V data = head.value; // If there is more than 1 element in the list, get the data.
		head = head.next; // Iterate the head, we just removed the first element.
		size--;
		return data;
    }

    /**
     * Removes the last element in the list.
     * @return the data that was removed.
     */
    @Override
    public V removeLast() {
		if (head == null){
			throw new NoSuchElementException(); // If the list is empty, throw NoSuchElementException.
		}
		if(head == tail){
			return removeFirst(); // If there is only 1 element in the list, then does not matter to remvoe first or last.
		}
		Node<V> current = head; // If there are more than 1 element in the list, we get a current node to itereate the list to the last.
		while(current.next.next != null){ 
			current = current.next; // We iterate until 1 node before the last node in the list.
		}
		V data = current.next.value; // We store the value.
		current.next = null; // We remove the last node.
		tail = current; // Make the current the last node.
		size--;
		return data; // Return the removed data.
	}

    /**
     * Returns the data of the first element.
     * @return the data of the first element. 
     */
    @Override
    public V getFirst() {
		if (head == null){
			throw new NoSuchElementException(); // If the list empty throw the exception.
		}else{
			return head.value; // Return the value of the head.
		}
    }

    /**
     * Retruns the last element of the list.
     * @return the last element of the list.
     */
    @Override
    public V getLast() {
		if (tail == null){
			throw new NoSuchElementException(); // If the list empty throw the exception.
		}else{
			return tail.value; // Return the value of the tail.
		}
    }
    /**
     * Clears the list.
     */
    @Override
    public void clear() {
        head = tail = null; // Make the head and tail, null. This way we have a new list.
		size = 0; // Set the size to 0.
    }

	/**
	 * Returns an iterator over the elements in the list.
	 * @return an iterator over the elements in the list.
	 */
	@Override
	public Iterator<V> iterator() {
		return new Iterator<V>(){
			/** Create a current node that points to the head. */
			private Node<V> current = head;
			
			/**
			 * Returns true or false if the list has more elements to iterate over.
			 * @return true if ther eare more elements to iterate over in the list, false otherwise. 
			 */
			@Override
			public boolean hasNext() {
				return current != null;
			}

            /**
             * Iterate over the list by one element.
             * @return the element that we just iterated over.
             */
            @Override
			public V next() {
				if (current == null) {
					throw new NoSuchElementException(); // If we are at the end, and they want us to go to the next, we can't. Thus, we throw an exception.
				}
				V value = current.value; // otherwise grab the value, advance, and return it.
				current = current.next;
				return value;
			}
		};
	}
	
	/**
	 * Adds an element to the end for enqeue.
	 * @param value is the element you want to enqeue.
	 * @return true if we were able to add the element.
	 */
	@Override
    public boolean add(V value) {
		addLast(value);
		return true;
	}
	
    /**
     * Removes the first element when we deqeue.
     * @return the element that we just removed.
     */
    @Override
    public V remove() {
		return removeFirst();	
    }

    /**
     * This is a peek at the queue.
     * @return the element that is next in the queue.
     */
    @Override
    public V element() {
		return getFirst();
    }

    /**
    * Adds an element to the stack.
    * @param value is the element that you want to add to your stack.
    */
    @Override
    public void push(V value) {
		addFirst(value); // For linked list stack, we add at the beginning, and remove from beginning.
    }

    /**
	 * Removes an element from the stack.
	 * @return the element that we just removed.
	 */
    @Override
    public V pop() {
		return removeFirst();
    }
	
	//------------------------------------------------
	// Additional required methods for this project
	//------------------------------------------------
	
	/**
	 * Constructor for our class. Sets the head and tail to null, and the size to 0.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenLinkedDeque() {
		head = null;
		tail = null;
		size = 0;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	
	/**
	 * The main method used for testing.
	 * @param args CLA.
	 */
	public static void main(String[] args) {
		ThreeTenLinkedDeque<Integer> deque = new ThreeTenLinkedDeque<>();
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
	}
	
	//------------------------------------------------
	// Provided methods for this project (DO NOT CHANGE!)
	//------------------------------------------------
	
	@Override
	public int size() {
		return size;
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
	
	

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<V> descendingIterator() {
        throw new UnsupportedOperationException();
    }
}