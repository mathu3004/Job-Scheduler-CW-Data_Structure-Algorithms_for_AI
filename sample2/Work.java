package Coursework.sample2;

public class Work {
    private int workID; // Unique identifier for the work
    private String workName; // Name of the work
    private String workDescription; // Description of the work
    private boolean workStatus; // Status indicating if the work is completed
    private WorkLinkedList dependents; // List of dependent works

    // Constructor to initialize a Work object
    public Work(int workID, String workName, String workDescription, boolean workStatus) {
        this.workID = workID; // Set the work ID
        this.workName = workName; // Set the work name
        this.workDescription = workDescription; // Set the work description
        this.workStatus = workStatus ? true : false; // Set the work status, default to false if invalid
        this.dependents = new WorkLinkedList(); // Initialize the linked list for dependents
    }

    // Getter for work ID
    public int getWorkID() {
        return workID;
    }

    // Getter for work name
    public String getWorkName() {
        return workName;
    }

    // Getter for work description
    public String getWorkDescription() {
        return workDescription;
    }

    // Getter for work status
    public boolean isWorkStatus() {
        return workStatus;
    }

    // Setter for work status
    public void setWorkStatus(boolean workStatus) {
        this.workStatus = workStatus;
    }

    // Method to add a dependent work to the list
    public void addDependent(Work dependent) {
        dependents.add(dependent); // Add the dependent work to the linked list
    }

    // Method to check if a specific work is a dependent
    public boolean hasDependent(Work work) {
        return dependents.find(work.getWorkID()) != null; // Check if the work ID exists in the dependents list
    }

    // Method to get an iterable of dependents
    public Iterable<Work> getDependents() {
        return dependents.iterate(); // Return an iterator for the dependents list
    }

    @Override
    public String toString() {
        return "Work{" +
                "workID=" + workID +
                ", workName='" + workName + '\'' +
                ", workDescription='" + workDescription + '\'' +
                ", workStatus=" + (workStatus ? "Work is Completed" : "Work is not Completed") +
                '}'; // Provide a string representation of the Work object
    }

    // Method to remove a dependent work from the list
    public void removeDependent(Work dependentWork) {
        if (dependentWork != null) {
            dependents.remove(dependentWork.getWorkID()); // Remove the dependent work from the linked list
        }
    }
}
