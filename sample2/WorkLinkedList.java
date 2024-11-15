package Coursework.sample2;

public class WorkLinkedList {
    private WorkNode head; // Head of the linked list
    private int size; // Number of elements in the linked list

    // Constructor to initialize an empty linked list
    public WorkLinkedList() {
        this.size = 0; // Set size to 0
        this.head = null; // Initialize head as null
    }

    // Method to add a work to the linked list in order
    public void add(Work work) {
        WorkNode newNode = new WorkNode(work); // Create a new node with the given work

        if (head == null) {
            head = newNode; // Set new node as head if the list is empty
        } else {
            WorkNode current = head;
            // Traverse to the end of the list
            while (current.nextInOrder != null) {
                current = current.nextInOrder;
            }
            current.nextInOrder = newNode; // Add the new node at the end of the list
        }
        size++; // Increment the size of the list
    }

    // Method to find a work by its ID
    public Work find(int workID) {
        WorkNode current = head;
        // Traverse the list to find the work with the given ID
        while (current != null) {
            if (current.work.getWorkID() == workID) {
                return current.work; // Return the work if found
            }
            current = current.nextInOrder;
        }
        return null; // Return null if the work is not found
    }

    // Method to check if the list contains a specific work
    public boolean contains(Work work) {
        WorkNode current = head;
        // Traverse the list to check if it contains the specified work
        while (current != null) {
            if (current.work.equals(work)) {
                return true; // Return true if the work is found
            }
            current = current.nextInOrder;
        }
        return false; // Return false if the work is not found
    }

    // Method to get the size of the linked list
    public int size() {
        return size; // Return the current size of the list
    }

    // Method to check if the linked list is empty
    public boolean isEmpty() {
        return size == 0; // Return true if the size is 0
    }

    // Method to remove a work by its ID
    public void remove(int workID) {
        if (head == null) return; // Return if the list is empty

        // If the work to be removed is at the head
        if (head.work.getWorkID() == workID) {
            head = head.nextInOrder; // Set the next node as the new head
            size--; // Decrement the size
            return;
        }

        WorkNode current = head;
        // Traverse the list to find the work with the given ID
        while (current.nextInOrder != null && current.nextInOrder.work.getWorkID() != workID) {
            current = current.nextInOrder;
        }

        // Remove the node if found
        if (current.nextInOrder != null) {
            current.nextInOrder = current.nextInOrder.nextInOrder; // Bypass the node to be removed
            size--; // Decrement the size
        }
    }

    // Custom Iterator class for iterating through the linked list
    private class WorkListIterator implements java.util.Iterator<Work> {
        private WorkNode current = head; // Start iteration from the head

        @Override
        public boolean hasNext() {
            return current != null; // Check if there are more elements
        }

        @Override
        public Work next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements in the list");
            }
            Work work = current.work; // Get the current work
            current = current.nextInOrder; // Move to the next node
            return work; // Return the current work
        }
    }

    // Method to get an iterable for the linked list using custom iterator
    public Iterable<Work> iterate() {
        return new Iterable<Work>() {
            @Override
            public java.util.Iterator<Work> iterator() {
                return new WorkListIterator(); // Return a new iterator for the list
            }
        };
    }

    // Inner class to represent a node in the linked list
    private class WorkNode {
        Work work; // The work stored in the node
        WorkNode nextInOrder; // Reference to the next node in the list

        WorkNode(Work work) {
            this.work = work; // Initialize the node with the given work
            this.nextInOrder = null; // Set the next node as null
        }
    }
}
