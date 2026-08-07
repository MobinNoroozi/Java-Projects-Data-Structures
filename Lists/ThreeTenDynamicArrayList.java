import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 *  This is the class that can be used to create a Dynamic Array List.
 *  @param <T> the type of list.
 *  @author Mobin Noroozi
 */
public class ThreeTenDynamicArrayList<T> extends AbstractCollection<T> implements List<T> {
	//------------------------------------------------
	// Add any PRIVATE instance variables you want here!
	//------------------------------------------------
	// The instance variables for this class. 
	
	/**
	 * Keeps track of the size(number of items) in the array.
	 */
	private int size = 0; 

	/**
	 * The array of the type of the data.
	 * This array grows or shrinks as needed.
	 */
	private T [] listItem; 

	/**
	 * The initial capacity of the list.
	 * This will be used to reset the array to its inicial capacity when the array is cleared.
	 */
	private int initialCapacity; 

	/**
	 * This method adds an item to the list to a specific index.
	 * @param index is the index that you wish to add the element to.
	 * @param element is the item you wish to add to the list.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	@Override
	public void add(int index, T element) {
		//If the index is out of bound, throw an error, and stop the program.
		//Else, continue with adding the element to the list. 
		if (index < 0 || index > size){
			throw new IndexOutOfBoundsException();
		}
		/*	If the size is same as the length of list(capacity), we double the capacity,
		 *  so we have enough space to add the new element to the list.
		 */
		if (size == listItem.length){
			setCapacity(2*listItem.length);
		}
		// Size is the position after the last element. It is 1 more than the last index.
		for(int i = size; i > index; i--){
			listItem[i] = listItem[i-1];
			// With this function, we move each element, up to the index one to the right.
			// So we make space for the element we want to insert at the desired index. 
		}
		listItem[index] = element; // Then we add the element to the desired index.
		size ++; // We increase the size.
	}
	
	/**
	 * We add the element to the end of the list.
	 * @param element is the item you wish to add to the list. 
	 * @return true, if adding was successful.
	 */
	@Override
	public boolean add(T element) {
		/*	If the size is same as the length of list(capacity), we double the capacity,
		 *  so we have enough space to add the new element to the list.
		 */
		if (size == listItem.length){
			setCapacity(listItem.length*2);
		}
		listItem[size] = element; // weadd the item to the end if of the list
		size ++;
		return true;
	}
	
	/**
	 * Clears the array. 
	 */
	@Override
	public void clear() {
		this.size = 0; // We reset the size to the 0.
		setCapacity(initialCapacity); // We clear the array with size of the initial capacity.
	}
	
	/**
	 * It returns the item.
	 * @param index of the element you want to get.
	 * @return the element at the input index.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	@Override
	public T get(int index) {
		//If the index is out of bound, throw an error, and stop the program.
		//Else, continue with adding the element to the list. 
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}else{
			return listItem[index]; // Returns the element.
		}
	}
	
	/**
	 * Returns the index of the first element in the list.
	 * If the it is not found, it returns -1.
	 * @param o the element to search for.
	 * @return the index of the first element that is found, or -1 if nothing is found. 
	 */
	@Override
	public int indexOf(Object o) {
		// Loop through each position in the list.
		for(int i = 0; i < size; i++){
			if(listItem[i] == null){ // If the list item is null, and we are looking for null, we have found it, and return the index.
				if (o == null){
					return i;
				}
				
			}else{
				if(listItem[i].equals(o)){ // If we find a match we return its index.
					return i;
				}
			}
		}
		return -1; //Otherwise, return -1.
	}
	
	/**
	 * Removes the element at the given index, and shift the rest of the elements to the left.
	 * The size is decreased, and the size of the array is adjusted if needed. 
	 * @param index is the indec of the element you want to remove from the list.
	 * @return the element that was removed.
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public T remove(int index) {
		// Checks if the index is valid.
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		// Stores the element that will be removed.
		T removedElement = listItem[index];

		// We go to the element we want to remove.
		// We shift the elements after the removed element one to the left. 
		for(int i = index; i < size-1; i++){
			listItem[i] = listItem[i+1];
		}
		// Sets the last elemnt to the null after shifting.
		listItem[size-1] = null;
		size--;
		// If the size is equal or less than 3rd of the capacity, we decrease the capacity by 50%.
		if(size<= (capacity()/3)){
			setCapacity(capacity()/2);
		}
		return removedElement; // Return the removed element. 
	}
	
	/**
	 * Removes the first item of a specified object from the list.
	 * If found, it removes it, and shift the rest of the element by one to the left.
	 * If not found, it returns false.
	 * @param o is the object that you want to remove from the list.
	 * @return true of the object was found and removed. If not, it returns false.
	 */
	@Override
	public boolean remove(Object o) {
		// Finds the index of the object in the list.
		int num = indexOf(o);
		
		if (num == -1){ // Means that the item was not found.
			return false; // Thus, it returns false. 
		}
		remove(num); // Removed the item, and returns true.
		return true;
	}
	
	/**
	 * Replace the element at the specific index with the inputed element.
	 * @param index is the index if the element you wish to replace.
	 * @param element is the element you want the current element to be replaced with.
	 * @return the element that was replaced.
	 * @throws IndexOutOfBoundsException if the index is out of range. 
	 */
	@Override
	public T set(int index, T element) {
		// Checks if the index is valid.
		if (index <0 || index >= size){
			throw new IndexOutOfBoundsException();
		}

		// Replace the element at the desired index.
		listItem[index] = element;
		return element; // Returns the element that was replaced.
	}
	
	/**
	 * Returns the size of the element.
	 * @return the size of the element.
	 */
	@Override
	public int size() {
		return size;
	}
	

	/**
	 * Return an iterator over the elements in the list. 
	 * @return an iterator over the elements in the list.
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<>() { // Return a new iterator object.
			private int position = 0; // Position keeps track of the current position in the list.

			/**
			 * Return true if there are more elements to iterate over.
			 * @return true if there are more elements to itereate over. Otherwise, return false.
			 */

			@Override
			public boolean hasNext() {
				// Checks if the position is less than the size of the list.
				if (position < size){
					return true;
				}
				else{
					return false;
				}
			}

			/**
			 * Return the next element in the list and increment the iterator. 
			 * @return the nexr element in the list.
			 */
			@Override
			public T next() {
				
				T element = listItem[position];
				position++;
				return element;
			}
		};
	}
	
	/**
	 * Creates a new dynamic array with specified intial capacity.
	 * @param cap the initial capacity of the list. It is set during declaration of on object of dynamic list.
	 * @throws IllegalArgumentException if the specified capacity is less than 1.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenDynamicArrayList(int cap) {
		if(cap < 1){
			throw new IllegalArgumentException("The capacity cannot be less than 1.");
		}
		// Set the initial capacity for the list.
		this.initialCapacity = cap;

		// Creates a new array with the specific capacity. 
		this.listItem = (T []) new Object[cap];
	}
	
	/**
	 * It sets the capacity of the list to a new capacity.
	 * If the new capacity is smaller than the size, the this will not happen, and returns false.
	 * Otherwise, it returns true.
	 * @param newCap is the new capacity for the list.
	 * @return true if the capacity is changed, false if not.
	 */
	@SuppressWarnings("unchecked")
	public boolean setCapacity(int newCap) {
		// If the new capacity is less than current size, it returns false.
		if(newCap < size){
			return false;
		}
		// Creates a new array with the new capacity.
		T[] newListItem = (T []) new Object[newCap];
		// Copies the item from the old list to the new list.
		for(int i = 0; i < size; i++){
			newListItem[i] = listItem[i];
		}
		this.listItem = newListItem; // Replace the old list with the new list.
		return true;
	}
	
	/**
	 * Returns the capacity of the list.
	 * @return a number that indicates the capacity of the list.
	 */
	public int capacity() {
		return  listItem.length;
	}
	
	//------------------------------------------------
	// Unsupported Operations for this project.
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
