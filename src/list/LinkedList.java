package list;

import java.util.Iterator;

/**
 * The LinkedList class holds a list of items in a doubly linked list. It
 * gives a string representation, returns its size, gets the value at a given
 * index, sets a value at a given index, adds an object to the list at a given
 * index, removes an object from a given index, and can add an remove from the
 * beginning and end.
 * 
 * @author Ravi
 * @version 022114
 * @param <E> Object that the LinkedList holds
 */
public class LinkedList<E> implements Iterable<E> {

	// Instance variables
    private DoubleNode first;
	private DoubleNode last;
	private int size;

    // Instance Methods        
    /**
     * Creates an empty <code>LinkedList</code>
     */
	public LinkedList() {
        first = null;
        last = null;
        size = 0;
	}
        
    /**
     * Prints out every element in the list.
     * 
     * @return the String representing the LinkedList
     */
	public String toString() {
        DoubleNode node = first;
        if (node == null)
            return "[]";
        String s = "[";
        while (node.next != null) {
            s += node.value + ", ";
            node = node.next;
        }
        return s + node.value + "]";
	}
        
    /**
     * Returns the node at the specified index, traversing from the first
     * node. Precondition:  0 <= index <= size / 2.
     * 
     * @param index index from which node is returned
     * @return the node at the specified index
     */
	private DoubleNode getNodeFromFirst(int index) {
        return getNodeFromFirst(index, first);
	}
        
    /**
     * Helper method which recursively scans the list for the element at the
     * given index.
     * 
     * @param index index from which node is returned
     * @param current the current node (used in recursion)
     * @return the node at the specified index 
     */
    private DoubleNode getNodeFromFirst(int index, DoubleNode current) {
        if(index == 0)
            return current;
        return getNodeFromFirst(index-1, current.next);
    }

     /**
     * Returns the node at the specified index, traversing from the last
     * node. Precondition:  size / 2 <= index < size.
     * 
     * @param index index from which node is returned
     * @return the node at the specified index
     */
	private DoubleNode getNodeFromLast(int index) {
        return getNodeFromLast(index, last);
	}
        
    /**
     * Helper method which recursively scans the list for the element at the
     * given index.
     * 
     * @param index index from which node is returned
     * @param current the current node (used in recursion)
     * @return the node at the specified index 
     */
    private DoubleNode getNodeFromLast(int index, DoubleNode current) {
        if(index == size-1)
            return current;
        return getNodeFromLast(index+1, current.previous);
    }
        
    /**
     * Returns the node at the specified index, traversing from the front
     * or back, depending on which is closer. Precondition:  0 <= index < size.
     * 
     * @param index index from which node is returned
     * @return the node at the specified index
     */
	private DoubleNode getNode(int index) {
        if(index <= size/2)
            return getNodeFromFirst(index);
        return getNodeFromLast(index);
	}
        
    /**
     * Returns the size of this LinkedList. 
     */
	public int size() {
        return size;
	}
        
    /**
     * Returns the value of this LinkedList at the specified index
     * 
     * @param index index from which to return the value
     * @return the value of the LinkedList
     */
	@SuppressWarnings("unchecked")
	public E get(int index) {
        return (E)getNode(index).value;
	}
             
    /**
     * Replaces the element at position index with obj and returns the
     * element formerly at the specified position
     * 
     * @param index index at which to set
     * @param obj new value to set to
     * @return old value
     */
	@SuppressWarnings("unchecked")
	public E set(int index, E obj) {
        DoubleNode temp = getNode(index);
        E toReturn = (E)temp.value;
        temp.value = obj;
        return toReturn;
	}

    /**
     * Appends obj to this list.
     * 
     * @param obj value to append
     */
	public void add(E obj) {
        if(first == null) {
            first = new DoubleNode(obj);
            last = first;
        }
        else {
            last.next = new DoubleNode(obj);
            last.next.previous = last;
            last = last.next;
        }
        size++;
	}

    /**
     * Removes the element at the specified index and returns its value
     * 
     * @param index element which to remove
     * @return value at index
     */
	@SuppressWarnings("unchecked")
	public E remove(int index)	{
        DoubleNode toReturn;
        if(size == 0)
            return null;
        else if(size == 1)  {
            toReturn = first;
            first = null;
            last = null;
        }
        else if(index == 0) {
            toReturn = first;
            first = first.next;
            first.previous = null;
        }
        else if(index == size-1) {
            toReturn = last;
            last = last.previous;
            last.next = null;
        }
        else  {
            toReturn = getNode(index);
            toReturn.previous.next = toReturn.next;
            toReturn.next.previous = toReturn.previous;
        }
        size--;
        return (E)toReturn.value;
	}

    /**
     * Inserts the new element at the required index, moving all the
     * elements past the index one to the right.
     * 
     * @param index index at which to insert
     * @param obj element which to insert
     */
	public void add(int index, E obj) {
        if(index == size)
            add(obj);
        else {
            DoubleNode toInsert = new DoubleNode(obj);
            if(index == 0) {
                DoubleNode nextNode = first;
                first = toInsert;
                toInsert.next = nextNode;
                nextNode.previous = toInsert;
            }
            else {
                DoubleNode prevNode = getNode(index-1);
                DoubleNode nextNode = prevNode.next;
                toInsert.previous  = prevNode;
                toInsert.next = nextNode;
                prevNode.next = toInsert;
                nextNode.previous  = toInsert;
            }
            size++;
        }
	}

    /**
     * Create a new iterator for this LinkedList.
     * 
     * @return	an iterator over this LinkedList
     */
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Iterator for the LinkedList class
     */
    private class LinkedListIterator implements Iterator<E> {

        // Instance variables
        private DoubleNode nextNode, removalNode;

        // Instance methods
        public LinkedListIterator() {
            nextNode = first;
        }

        /**
         * Returns true if the iteration has more elements.
         */
        public boolean hasNext() {
            return nextNode != null;
        }

        /**
         * Returns the next element in the iteration
         */
        @SuppressWarnings("unchecked")
		public E next() {
            if(!hasNext())
                throw new java.util.NoSuchElementException();

            E toReturn = (E)nextNode.value;
            removalNode = nextNode;
            nextNode = nextNode.next;
            return toReturn;
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator.
         * This method can be called only once per call to next().
         */
        public void remove() {
            if(removalNode == null)
                throw new IllegalStateException();


            if(size == 1) {
                first = null;
                last = null;
            }
            else if(removalNode == first) {
                first = removalNode.next;
                first.previous = null;
            }
            else if(removalNode == last) {
                last = removalNode.previous;
                last.next = null;
            }
            else {
                removalNode.previous.next = removalNode.next;
                removalNode.next.previous = removalNode.previous;
            }
            removalNode = null;
            size--;
        }

    }

}