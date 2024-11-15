package Coursework.sample2;

/**
 * A custom implementation of a Set data structure IMPLEMENTED using a singly linked list.
 * This set does not allow duplicate set of elements.
 */
public class WorkSet<Circle> {
    private Node<Circle> head; // The head node of the linked list
    private int size;     // The number of elements in the set

    public WorkSet() {
        this.head = null; // Initialize head to null (empty set)
        this.size = 0;    // Initialize size to 0
    }

    /**
     * Adds an element to the set if it is not already present.
     * @param element The element to be added.
     */
    public void add(Circle element) {
        if (!contains(element)) { // Check if the element is already in the set if it does not already exit
            head = new Node<>(element, head); // Create a new node and set it as the new head
            size++; // Increment the size of the set
        }
    }

    /**
     * Checks if the set contains the specified element.
     * @param element The element to be checked.
     * @return True if the element is in the set, otherwise false.
     */
    public boolean contains(Circle element) { //ensure the element is not already in the set
        Node<Circle> current = head; // Start with the head of the linked list
        while (current != null) { // Traverse the list
            if (current.value.equals(element)) { // Check if the current node's value matches the element
                return true; // Element found
            }
            current = current.next; // Move to the next node
        }
        return false; // Element not found
    }

    /**
     * Removes the specified element from the set if it is present.
     * @param element The element to be removed.
     */
    public void remove(Circle element) {
        if (head == null) {
            return; // The set is empty
        }

        // If the element to remove is the head
        if (head.value.equals(element)) {
            head = head.next; // Move the head to the next node
            size--; // Decrement the size of the set
            return;
        }

        Node<Circle> current = head;
        while (current.next != null) {
            if (current.next.value.equals(element)) {
                current.next = current.next.next; // Remove the node
                size--; // Decrement the size of the set
                return;
            }
            current = current.next; // Move to the next node
        }
    }

    /**
     * Returns the number of elements in the set.
     * @return The size of the set.
     */
    public int size() { //(number of elements currently in the set)
        return size; // Return the size of the set
    }

    /**
     * A private static inner class representing a node in the linked list.
     * @param <Circle> The type of the value stored in the node.
     * Represents a node in the singly linked list.
     */
    private static class Node<Circle> {
        Circle value; // The value stored in the node
        Node<Circle> next; // Reference to the next node in the list

        /**
         * Constructs a node with the specified value and next node.
         * @param value The value to be stored in the node.
         * @param next The next node in the list.
         * Job as node and dependency as edges of the graph.
         */
        Node(Circle value, Node<Circle> next) {
            this.value = value; // Set the value of the node
            this.next = next; // Set the next node
        }
    }
}
