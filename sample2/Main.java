package Coursework.sample2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WorkScheduler scheduler = new WorkScheduler(5);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter works and dependencies (Press 6 to exit):");
        while (true) {
            System.out.println();
            System.out.println("1. Add Work");
            System.out.println("2. Add Dependency");
            System.out.println("3. Remove Work");
            System.out.println("4. Remove Dependency");
            System.out.println("5. Execute Work");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            System.out.println();

            switch (choice) {
                case "1":
                    scheduler.addWork();
                    break;
                case "2":
                    scheduler.addDependency();
                    break;
                case "3":
                    if (scheduler.size() < 1) {
                        System.out.println("Cannot remove work. There must be at least 1 work in the scheduler.");
                        break;
                    }
                    removeWork(scheduler, scanner);
                    break;
                case "4":
                    scheduler.removeDependency();
                    break;
                case "5":
                    if (scheduler.size() < 1) {
                        System.out.println("Cannot execute work. There must be at least 1 work in the scheduler.");
                        break;
                    }
                    System.out.println();
                    System.out.println("Current jobs:");
                    for (Work job : scheduler.iterate()) {
                        System.out.println(job); // Print each job in the list
                    }
                    System.out.println();
                    scheduler.rearrangeByDependencies();
                    executeJobs(scheduler);
                    break;
                case "6":
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Handles the removal of a work by its ID.
     * @param scheduler The WorkScheduler instance.
     * @param scanner The Scanner for user input.
     */
    private static void removeWork(WorkScheduler scheduler, Scanner scanner) {
        System.out.print("Enter the ID of the work to remove: ");
        try {
        int workID = Integer.parseInt(scanner.nextLine());
        if (workID < 1) {
            System.out.println("Invalid ID. Please try again.");
            return;
        }
        boolean success = scheduler.removeWork(workID);
        if (success) {
            System.out.println("Work removed successfully.");
        } else {
            System.out.println("Work not found.");
        }}
        catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please try again.");
        }
    }

    /**
     * Executes jobs from the scheduler until no more jobs are ready.
     * @param scheduler The WorkScheduler instance.
     */
    private static void executeJobs(WorkScheduler scheduler) {
        System.out.println("Current jobs:");
        for (Work job : scheduler.iterate()) {
            System.out.println(job); // Print each job in the list
        }
        System.out.println();
        System.out.println("Executing jobs:");
        Work nextJob;
        while ((nextJob = scheduler.getNextWorkToExecute()) != null) {
            int jobId = nextJob.getWorkID();
            String jobName = nextJob.getWorkName();
            System.out.println();
            System.out.println("Next job to execute: Job " + jobId + " (" + jobName + ")");
            scheduler.executeWork(jobId); // Execute the current job
            System.out.println();
        }
        System.out.println();
        System.out.println("Current jobs:");
        for (Work job : scheduler.iterate()) {
            System.out.println(job); // Print each job in the list
        }
        System.out.println();
    }
}