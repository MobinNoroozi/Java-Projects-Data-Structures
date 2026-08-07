import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * This is the class that can be used to create a LinkedList.
 * @param <T> the type of list.
 * @author Mobin Noroozi
 */
public class ThreeTenLinkedList<T> extends AbstractCollection<T> implements List<T> {
	
	/**
	 * The head node of the linked list.
	 * This reference to the first element in the list.
	 */
	private Node<T> head; 

	/**
	 * The tail node of the linked list.
	 * This reference to the last element in the list.
	 */
	private Node<T> tail;

	/**
	 * The size of the list.
	 * Keeps track of the number of elements in the list.
	 */
	private int size;

	/**
	 * A node in a doubly linked list.
	 * Each node has data and references to the next and previous nodes.
	 * @param <T> the type if the data stored in this node.
	 */
	public static class Node<T> {
		/**
		 * The data is the data stored in the node.
		 */
		public T data;

		/**
		 * The reference to the next node in the list.
		 * If this is the last node, next will be null.
		 */
		public Node<T> next;

		/**
		 * The reference to the previous node in the list.
		 * If this is the first node, prev will be null.
		 */
		public Node<T> prev;
		
		/**
		 * Constructor for the node.
		 * Creates a node with no data and no reference to next and previous.
		 */
		public Node() { 

		}

		/**
		 * Constructor for the node.
		 * Creates a node with data, but no reference to next and previous.
		 * @param data is the data that this node will store.
		 */
		public Node(T data) {
			this(data, null, null); 
		}
		
		/**
		 * Constructor for the node.
		 * Creates a node with data and reference to next and previous.
		 * @param data is the data that this node will store.
		 * @param prev reference to the previous node. If this is the first node, prev will be null.
		 * @param next reference to the next node. If this is the last node, next will be null.
		 * 
		 */
		public Node(T data, Node<T> prev, Node<T> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	/**
	 * This method adds an item to the list to a specific index.
	 * @param index is the index where you want to add the element to.
	 * @param element is the element to be inseterted in the list.
	 * @throws IndexOutOfBoundsException of the index is out of bound.
	 */
	@Override
	public void add(int index, T element) {
		// Checks the index to see if it is valid or not.
		if (index < 0 || index > size){ 
			throw new IndexOutOfBoundsException();
		}
		// Create a node with the element as the data.
		Node<T> newNode = new Node<>(element);
		
		// if the index == size, add to the end.
		if (index == size){
			// If the list is empty, head and tail both point to the new node.
			if(tail == null){
				head = tail = newNode; // Head equals to tail which is equal to the new node.
			}else{
				tail.next = newNode; // Adds the new node after the tail.
				newNode.prev = tail; // Links the new node to the tail again because it is a doubly linked list.
				tail = newNode; // Update the tail to point toward the new node which is at the end.
			}
		}else if (index == 0){ 
			// If index is 0, we add to the front of the list.
			newNode.next = head; // Link the new node to the head.
			head.prev = newNode; // Link the head back to the new node.
			head = newNode; // Update the head to point toward the new node.
		}else{ 
			// Add in the specific index in the middle.
			// A node that iterate through the nodes, to go the specific index. It starts from the head.
			Node<T> current = head;
			for (int i = 0; i < index; i++){
				current = current.next;
			}
			// We are at the node just before the index we want to insert.
			Node<T> before = current.prev;
			// Inserts the new node current.prev and the current.
			before.next  = newNode; // Links the previous node to the current node.
            newNode.prev = before;  // Links the current node to the previous node.
            newNode.next = current; // Links the new node to the current node.
           	    current.prev = newNode; // Link the current node back to the new node.
		}
		size++;
	}
	

	/**
	 * Adds an element to the end of the list.
	 * @param element is the element that will be added to the end of the list.
	 * @return true if the element was successfully added to the end of the list.
	 */
	@Override
	public boolean add(T element) {
		// New node with the element as the data.
		Node<T> newNode = new Node<>(element);
		// If list is empty, set both tail and head to the new node.
		if(tail == null){
			head = tail = newNode;
		}else{
			// Add the node to the end of the list.
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}	
		size ++;
		return true;	
	}
	
	/**
	 * It removed everything from the list.
	 */
	@Override
	public void clear() {
		// Sets both head and tail to null, thus we lose the list and will be collected by garbage collector.
		head = null; 
		tail = null;
		size = 0; // Reset the size to 0.
	}


	/**
	 * Returns an element at the given index.
	 * @param index is the index of the element that you would like to get.
	 * @return the element at the given index.
	 * @throws IndexOutOfBoundsException if the index is out of bound.
	 */
	@Override
	public T get(int index) {
		if(index < 0 || index > size){
			throw new IndexOutOfBoundsException();
		}
		// The current node will go to the desired index.
		Node<T> current = head;
		for(int i = 0; i < index; i++){
			current = current.next;
		}
		// Returns its data.
		return current.data;
	}
	
	/**
	 * Returns the index of the first specific element in the list.
	 * If the element does not exist, it returns -1.
	 * @param o is the element to search for.
	 * @return the index of the first specific element. If not found, returns -1.
	 */
	@Override
	public int indexOf(Object o) {
		// Current starts from head and iterate over the nodes to find the element.
		Node<T> curr = head;
        int indx = 0; // Initializing the index.
			
        if(o == null) { // If the object is null, iterate over the list to find the first index with null data.
            while (curr != null) {
                if (curr.data == null) {
					return indx; // Return that index.
				}
                curr = curr.next; // If not go to the next node.
                indx++; // Increase the index as well.
            }
        }else {
			
            while(curr != null) { // Iterate over each node.				
                if(o.equals(curr.data)){ // If the current data is equal to o, return its index.
					return indx;
				}
                curr = curr.next;
                indx++;
            }
        }
        return -1; // Else return -1.
	}
	
	/**
	 * Removes the element at a specific index.
	 * @param index is the index of the element that you wish to remove.
	 * @return the element that was removed from the list.
	 * @throws IndexOutOfBoundsException if the index is out of bound.
	 */
	@Override
	public T remove(int index) {
		// Checks for the valid index.
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}

		// Current starts from head and iterate over the nodes to find the element.
		Node<T> current = head;
		for(int i = 0; i < index; i++){
			current = current.next;
		}
		// Store the data that will be removed.
		T removedData = current.data;

		// If the node to delete is not the tail, update the next node's previous pointer.
		if (current.next != null) {
        	current.next.prev = current.prev;
    	} else {
        	tail = current.prev; // If there is no next, we removed the tail.
		}
		// If the node to remove is not the head, update the previous node's next.
		if (current.prev != null) {
        	current.prev.next = current.next;
		} else {
			// If there is no prev, we removed the head.
			head = current.next;
		}
		size--;
		return removedData; // Return the removed data.
    }
	
	/**
	 * Removed the first occurance of the specific element in the list.
	 * @param o is the element you wish to delete from the list.
	 * @return true if the element was successfully removed. If the element was not found, it returns false.
	 */
	@Override
	public boolean remove(Object o) {
		// Finds the index of the element.
		int idx = indexOf(o);
        if (idx == -1) { // Return false of the element was not found.
			return false;
		}
        remove(idx); // Remove the element.
        return true;
	}
	
	/**
	 * Replace the element at the specific index with the new element.
	 * @param index is the index of the element you wish to replace.
	 * @param element is the new element that will replace the old element.
	 * @return the element that was replaced.
	 *     @@throws IndexOutOfBoundsException if the index is out of bound.
	 */
	@Override
	public T set(int index, T element) {
		if(index < 0 || index >= size){ // Check if the index is valid.
			throw new IndexOutOfBoundsException();
		}
		// Starts from the head, and go to a specific index.
		Node<T> current = head;
		for(int i = 0; i < index; i++){
			current = current.next;
		}
		// Store the old data.
		T oldData = current.data;
		current.data = element; // Replace the element.
		return oldData; // Return the old data. 
	}
	
	/**
	 * Returns the size of the list.
	 * @return the size of the list.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Returns an iterator over the elements in the list.
	 * It starts at the head and goes over the elements one at the time.
	 * @return an iterator for the list.
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<>() {
			// Start at the head of the list.
			private Node<T> current = head;

			/**
			 * Returns true of there are more elements to iterate over.
			 * @return true if there are more element to iterate over.
			 */
			@Override
			public boolean hasNext() {
				// If the current element is not null, there are more elements.
				return current != null;
			}
			
			/**
			 * Returns the next element in the list and advances the iterator.
			 * @return the next element in the list.
			 */
			@Override
			public T next() {
				// Store the data and advances the current.
				T data = current.data;
				current = current.next;
				return data; // Return the data. 
			}
		};
	}

	/**
	 * Return the head node of the list.
	 * @return the head node of the list.
	 */
	public Node<T> getHead() {
		return head;
	}
	
	//------------------------------------------------
	// Unsupported Operations for this project
	//------------------------------------------------
	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
}
