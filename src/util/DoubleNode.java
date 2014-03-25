package util;

/**
 * Representation of nodes.
 */
class DoubleNode {

    Object value;
    DoubleNode previous, next;
        
    /**
     * Creates a new DoubleNode with the specified value and its pointers
     * set to null.
     * 
     * @param v - value the DoubleNode is set to
     */
    DoubleNode(Object v) {
        value = v;
        previous = null;
        next = null;
    }

}