package Coursework.sample2;

import java.util.Scanner;
/**
 * The scheduler allows adding works, setting dependencies, checking work readiness,
 * executing works, and managing dependencies.
 */
public class WorkScheduler {
    private Work[] works; // Array to store the works
    private int capacity; // Maximum capacity of the scheduler
    private int size;     // Current number of works in the scheduler
    boolean isCase2Success = false; // Flag to indicate if dependencies were successfully added

    public WorkScheduler(int initialCapacity) {
        this.capacity = initialCapacity; // Set the maximum capacity
        this.size = 0;  // Initialize size to 0
        this.works = new Work[initialCapacity]; // Initialize the array with the given capacity
    }

    /**
     * Adds a new work to the scheduler with error handling for invalid input.
     */
    public void addWork() {
        Scanner scanner = new Scanner(System.in);

        try {
            if (isFull()) {
                System.out.println("Cannot add more works. The scheduler is full.");
                return;  // Exit if the scheduler is full
            }
            System.out.print("Enter Work ID: ");
            int workID = scanner.nextInt();
            if (workID < 1) {
                System.out.println("Invalid Work ID");
                return;  // Exit if the Work ID is invalid
            }
            scanner.nextLine(); // Consume newline
            System.out.print("Enter Work Name: ");
            String workName = scanner.nextLine();
            System.out.print("Enter Work Description: ");
            String workDescription = scanner.nextLine();
            System.out.println("Enter Work Status (true/false): ");
            boolean workStatus = scanner.nextBoolean();
            // Capitalize first letter of name and each sentence of description
            workName = StringUtils.capitalizeFirstLetter(workName);
            workDescription = StringUtils.capitalizeSentences(workDescription);

            // Check if a work with the same ID already exists
            if (find(workID) != null) {
                System.out.println("Work with ID " + workID + " already exists. Cannot add duplicate work.");
                return;
            }

            // Add the new work to the array
            works[size++] = new Work(workID, workName, workDescription, workStatus);
            System.out.println("Work with ID " + workID + " (Name: " + workName + ", ");
            System.out.println("Description: " + workDescription);
            System.out.println("workStatus: " + (workStatus ? "Work is Completed" : "Work is not Completed") + ") added to the scheduler.");
        } catch (Exception e) {
            System.out.println("Invalid Work ID. Please try again.");
        }
    }

    /**
     * Adds a dependency between two works with error handling.
     */
    public void addDependency() {
        Scanner scanner = new Scanner(System.in);

        try {
            if (size < 2) {
                System.out.println("Cannot add dependencies. At least 2 works are required.");
                return;  // Exit if there are fewer than 2 works
            }
            System.out.print("Enter Work ID: ");
            int workID = scanner.nextInt();
            System.out.print("Enter Dependent Work ID: ");
            int dependentWorkID = scanner.nextInt();

            if (workID < 1 || dependentWorkID < 1) {
                System.out.println("Invalid Work ID. Please try again.");
                return;
            }

            Work work = find(workID);
            Work dependentWork = find(dependentWorkID);
            if (work == null || dependentWork == null) {
                System.out.println("Failed to add dependency. Work " + workID + " or Work " + dependentWorkID + " does not exist.");
                return; // Exit if either work does not exist
            }
            if (workID == dependentWorkID) {
                System.out.println("Cannot add dependency to itself. Work " + workID + " cannot depend on itself.");
                return; // Exit if a work is set to depend on itself
            }
            if (work.hasDependent(dependentWork)) {
                System.out.println("Dependency already exists: Work " + workID + " already depends on Work " + dependentWorkID + ".");
                return;  // Exit if the dependency already exists
            }

            // Temporarily add the dependency for cycle checking
            work.addDependent(dependentWork);

            // Check for circular dependencies
            if (hasCircularDependency(work)) {
                // Remove dependency if it creates a cycle
                work.removeDependent(dependentWork);
                System.out.println("Cannot add dependency. It would create a circular dependency.");
                return;
            }
            isCase2Success = true;
            System.out.println("Dependency added: Work " + workID + " depends on Work " + dependentWorkID + ".");
        } catch (Exception e) {
            System.out.println("Invalid Work ID. Please try again.");
        }
    }

    /**
     * Checks if a work is ready to execute based on its dependencies.
     * @param work The work to be checked.
     * @return True if the work is ready to execute, otherwise false.
     */
    public boolean isWorkReady(Work work) {
        for (Work dependent : work.getDependents()) {
            if (!dependent.isWorkStatus()) {
                System.out.println("Work " + work.getWorkID() + " is not ready to be executed. Depends on Work " + dependent.getWorkID() + " which is not completed yet.");
                return false; // Work is not ready if any dependent is not completed
            }
        }
        return true;  // Work is ready if all dependencies are completed
    }

    /**
     * Gets the next work that is ready to execute.
     * @return The next work to execute, or null if none are ready.
     */
    public Work getNextWorkToExecute() {
        try {
            for (int i = 0; i < size; i++) {
                if (!works[i].isWorkStatus() && isWorkReady(works[i])) {
                    return works[i]; // Return the next work that is not completed but ready to execute
                }
            }
            System.out.println("No works are ready to execute at the moment.");
            return null; // No work is ready to execute
        } catch (Exception e) {
            System.out.println("Error occurred while getting next work to execute: " + e.getMessage());
            return null;
        }
    }

    /**
     * Executes a work by its ID if it is ready.
     * @param workID The ID of the work to execute.
     */
    public void executeWork(int workID) {
        try {
            Work work = find(workID);
            if (work != null && isWorkReady(work)) {
                work.setWorkStatus(true);
                System.out.println("Work " + work.getWorkID() + " is ready to be executed.");
                System.out.println("Executing Work " + workID + " and marking as completed.");
            } else {
                System.out.println("Work " + workID + " is not ready to be executed. Waiting for dependencies or already completed.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while executing work: " + e.getMessage());
        }
    }

    /**
     * Finds a work by its ID in the array.
     * @param workID The ID of the work to find.
     * @return The work with the specified ID, or null if not found.
     */
    public Work find(int workID) {
        for (int i = 0; i < size; i++) {
            if (works[i].getWorkID() == workID) {
                return works[i]; // Return the work with the specified ID
            }
        }
        return null; // Work not found
    }

    /**
     * Checks if the scheduler has reached its full capacity.
     * @return True if the scheduler is full, otherwise false.
     */
    public boolean isFull() {
        return size >= capacity; // Return true if the number of works is equal to or exceeds capacity
    }

    /**
     * Provides an iterable for the works in the scheduler.
     * @return An Iterable of works.
     */
    public Iterable<Work> iterate() {
        return new Iterable<Work>() {
            @Override
            public java.util.Iterator<Work> iterator() {
                return new java.util.Iterator<Work>() {
                    private int currentIndex = 0; // Index of the current element

                    @Override
                    public boolean hasNext() {
                        return currentIndex < size; // Return true if there are more elements
                    }

                    @Override
                    public Work next() {
                        if (!hasNext()) {
                            throw new java.util.NoSuchElementException("No more elements in the list");
                        }
                        return works[currentIndex++]; // Return the current element and move to the next
                    }
                };
            }
        };
    }

    /**
     * Rearranges the works array based on the number of dependencies using merge sort.
     */
    public void rearrangeByDependencies() {
        if (size <= 1) return; // No need to rearrange if there's 1 or fewer works

        mergeSort(works, 0, size - 1);
    }

    /**
     * Merge Sort algorithm to sort the array based on dependencies count.
     *
     * @param array The array to be sorted.
     * @param left The starting index of the array segment to be sorted.
     * @param right The ending index of the array segment to be sorted.
     */
    private void mergeSort(Work[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Recursively sort the first and second halves
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            // Merge the sorted halves
            merge(array, left, mid, right);
        }
    }

    /**
     * Merges two sorted halves of the array.
     *
     * @param array The array to be sorted.
     * @param left The starting index of the left half.
     * @param mid The ending index of the left half and the starting index of the right half.
     * @param right The ending index of the right half.
     */
    private void merge(Work[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        Work[] leftArray = new Work[n1];
        Work[] rightArray = new Work[n2];

        // Copy data to temporary arrays
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        // Merge the temporary arrays back into the original array
        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (getDependentsCount(leftArray[i]) <= getDependentsCount(rightArray[j])) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        // Copy the remaining elements of leftArray[], if any
        while (i < n1) {
            array[k++] = leftArray[i++];
        }

        // Copy the remaining elements of rightArray[], if any
        while (j < n2) {
            array[k++] = rightArray[j++];
        }
    }

    /**
     * Counts the number of dependents a work has.
     * @param work The work whose dependents are to be counted.
     * @return The number of dependents for the given work.
     */
    private int getDependentsCount(Work work) {
        int count = 0;
        for (Work dependent : work.getDependents()) {
            count++;
        }
        return count;
    }

    /**
     * Checks if the scheduler has a circular dependency starting from the given work.
     * @param startWork The work to check for circular dependencies.
     * @return True if a circular dependency is detected, otherwise false.
     */
    public boolean hasCircularDependency(Work startWork) {
        WorkSet<Integer> visited = new WorkSet<>();
        //keeps track of nodes that have been completely processed.
        WorkSet<Integer> processingSet = new WorkSet<>();
        // keeps track of nodes that are currently being processed in the current path of the DFS.
        // Initializes the visited and processingSet sets and
        // starts the recursive check for circular dependencies from startWork.
        return hasCircularDependencyHelper(startWork, visited, processingSet);
    }

    /**
     * Helper method to recursively check for circular dependencies.
     * @param current The current work to check.
     * @param visited Set of visited work IDs.
     * @param processingSet Set of work IDs currently being processed.
     * @return True if a circular dependency is found, otherwise false.
     */

    //Here it will find the circular dependency
    private boolean hasCircularDependencyHelper(Work current, WorkSet<Integer> visited, WorkSet<Integer> processingSet) {
        if (processingSet.contains(current.getWorkID())) {
            //Node is still being processed, indicating a cycle
            //If current work is in processingSet, a cycle is detected
            //because we are revisiting a node that is still being processed.
            return true;
        }
        if (visited.contains(current.getWorkID())) {
            // Node has been fully processed, no need to check again
            //If current work is in visited, it means this node and
            // its dependents have already been fully processed and
            // no cycle was detected from it.
            return false;
        }
        // Mark the current node as being processed. Add current to processingSet
        processingSet.add(current.getWorkID());

        // Get the dependents of the current work
        //For each dependent of current, recursively check for circular dependencies.
        for (Work dependent : current.getDependents()) {
            if (hasCircularDependencyHelper(dependent, visited, processingSet)) {
                return true;//have circular dependency
            }
        }
        processingSet.remove(current.getWorkID()); //current node is removed from processingSet
        visited.add(current.getWorkID()); //current node is added to visited
        return false; //have not circular dependency
    }

    /**
     * Returns the number of works in the scheduler.
     * @return The number of works.
     */
    public int size() {
        return size; // Return the size of the scheduler
    }

    /**
     * Removes a work from the scheduler by its ID.
     * @param workID The ID of the work to be removed.
     * @return True if the work was successfully removed, otherwise false.
     */
    public boolean removeWork(int workID) {
        for (int i = 0; i < size; i++) {
            if (works[i].getWorkID() == workID) {
                for (int j = i; j < size - 1; j++) {
                    works[j] = works[j + 1]; // Shift elements left
                }
                works[size - 1] = null; // Remove reference to the last work
                size--; // Decrement the size
                return true; // Work successfully removed
            }
        }
        return false; // Work not found
    }

    /**
     * Removes a dependency between two works.
     */
    public void removeDependency() {
        Scanner scanner = new Scanner(System.in);
        try {
            if(!isCase2Success){
                    System.out.println("Cannot remove dependencies. At least one dependency relationship is required.");
                    return;
            }
            System.out.print("Enter Work ID: ");
            int workID = scanner.nextInt();
            System.out.print("Enter Dependent Work ID: ");
            int dependentWorkID = scanner.nextInt();

            if (workID < 1 || dependentWorkID < 1) {
                System.out.println("Invalid ID. Please try again.");
                return;
            }
            Work work = find(workID);
            Work dependentWork = find(dependentWorkID);
            if (work == null || dependentWork == null) {
                System.out.println("Failed to remove dependency. Work " + workID + " or Work " + dependentWorkID + " does not exist.");
                return; // Exit if either work does not exist
            }
            if (!work.hasDependent(dependentWork)) {
                System.out.println("No such dependency exists: Work " + workID + " does not depend on Work " + dependentWorkID + ".");
                return;  // Exit if the dependency does not exist
            }
            work.removeDependent(dependentWork);
            System.out.println("Dependency removed: Work " + workID + " no longer depends on Work " + dependentWorkID + ".");
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }
}
