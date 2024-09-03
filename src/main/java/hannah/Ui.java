package hannah;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import hannah.task.Task;
import hannah.task.TaskList;


public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcomeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Hannah.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public void showGoodbyeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
    }

    public void showTasks(TaskList tasks) {
        System.out.println("Tasks on your list!");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + ". " + tasks.getTasks().get(i).toString());
        }
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Added task!");
        System.out.println(task.toString());
        System.out.println("You now have " + taskCount + " tasks in your list.");
    }

    public void showTaskDeleted(int taskCount, Task task) {
        System.out.println("Okay will remove the task: ");
        System.out.println(task.toString());
        System.out.println("You now have " + taskCount + " tasks in your list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println("Good job! hannah.task.Task Marked!");
        System.out.println(task.toString());
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("Okay, task unmarked!");
        System.out.println(task.toString());
    }

    public void showErrorMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }
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

    /**
     * Displays the tasks that match the specified keyword.
     *
     * This method searches through the given list of tasks and finds those
     * that contain the specified keyword in their names. The matching tasks
     * are then printed to the console with their respective task numbers.
     *
     * @param tasks   The list of tasks to search through.
     * @param keyword The keyword to search for within the task names.
     */
    public void showFindResults(TaskList tasks, String keyword) {
        List<Task> matchingTasks = tasks.findTasks(keyword);
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println((i + 1) + "." + matchingTasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }
}
