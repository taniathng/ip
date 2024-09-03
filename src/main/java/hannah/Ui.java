package hannah;

import java.util.List;
import java.util.Scanner;
import hannah.task.Task;
import hannah.task.TaskList;

/**
 * The Ui class handles all interactions with the user, including displaying messages and reading input.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Initializes a new Ui object and sets up the scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a welcome message when the program starts.
     */
    public void showWelcomeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Hannah.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a goodbye message when the program ends.
     */
    public void showGoodbyeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
    }

    /**
     * Displays all tasks currently in the list.
     *
     * @param tasks The TaskList containing the tasks to be displayed.
     */
    public void showTasks(TaskList tasks) {
        System.out.println("Tasks on your list!");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + ". " + tasks.getTasks().get(i).toString());
        }
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task      The task that was added.
     * @param taskCount The current number of tasks in the list.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Added task!");
        System.out.println(task.toString());
        System.out.println("You now have " + taskCount + " tasks in your list.");
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param taskCount The current number of tasks in the list.
     * @param task      The task that was deleted.
     */
    public void showTaskDeleted(int taskCount, Task task) {
        System.out.println("Okay will remove the task: ");
        System.out.println(task.toString());
        System.out.println("You now have " + taskCount + " tasks in your list.");
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Good job! hannah.task.Task Marked!");
        System.out.println(task.toString());
    }

    /**
     * Displays a message indicating that a task has been unmarked (marked as not done).
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("Okay, task unmarked!");
        System.out.println(task.toString());
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showErrorMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads the user's command from the input.
     *
     * @return The user's input as a String.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Extracts the task number from the user's input.
     *
     * @param userInput The user's input string.
     * @return The task number extracted from the input.
     */
    public int getTaskNumber(String userInput){
        String[] parts = userInput.split(" ");
        int taskNumber = 0;
        if (parts.length > 1) {
            taskNumber = Integer.parseInt(parts[1]);
        }
        return taskNumber;
    }

    public String getKeyword(String userInput){
        String[] parts = userInput.split(" ");
        return parts[1];
    }

    public void showFindResults(TaskList tasks, String keyword) {
        System.out.println("Here are the matching tasks in your list:");
        List<Task> matchingTasks = tasks.findTasks(keyword);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + matchingTasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

}
