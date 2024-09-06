package hannah;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import hannah.command.Command;
import hannah.task.Deadline;
import hannah.task.Event;
import hannah.task.Task;
import hannah.task.TaskList;
import hannah.task.ToDos;

/**
 * Represents the main class for the Hannah application.
 * The Hannah class manages tasks by interacting with the user through commands
 * such as adding, deleting, marking tasks, and finding tasks. It stores and retrieves
 * tasks using the Storage class, and processes user input using the Parser class.
 * The application runs in a loop until the user inputs the "bye" command.
 */
public class Hannah {
    private static final String FILE_PATH = "src/data/Hannah.txt";
    private Storage storage;
    private TaskList list;
    private Parser parser;
    private Ui ui;

    /**
     * The Hannah class represents the main application for managing tasks.
     * It interacts with the user through the command line and processes tasks
     * such as ToDos, Deadlines, and Events.
     */
    public Hannah() {
        this.ui = new Ui();
        this.storage = new Storage(FILE_PATH);
        this.parser = new Parser();
        try {
            this.list = storage.loadFile();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            this.list = new TaskList(); // Initialize to an empty list if the file isn't found
        }
    }

    /**
     * Runs the Hannah application, handling user input and managing tasks.
     * The application continues running until the user types "bye".
     */
    public void run() {
        ui.showWelcomeMessage();
        Scanner scanner = new Scanner(System.in);
        TaskList list = (TaskList) this.storage.getTaskList();
        while (true) {
            String userInput = ui.readCommand();
            Command command = parser.parse(userInput);
            Task task = null;
            String commandName = command.getCommandType();
            if (commandName == "bye") {
                ui.showGoodbyeMessage();
                break;
            } else if (commandName == "list") {
                ui.showTasks(list);
            } else if (commandName == "delete") {
                int taskNumber = ui.getTaskNumber(userInput);
                task = list.getTask(taskNumber);
                list.deleteTask(taskNumber);
                int taskCount = list.size();
                ui.showTaskDeleted(taskCount, task);
            } else if (commandName == "unmark") {
                int taskNumber = ui.getTaskNumber(userInput);
                task = list.getTask(taskNumber);
                list.unmarkTask(task);
                ui.showTaskUnmarked(task);
            } else if (commandName == "mark") {
                int taskNumber = ui.getTaskNumber(userInput);
                task = list.getTask(taskNumber);
                list.markTask(task);
                ui.showTaskMarked(task);
            } else if (commandName == "todo") {
                if (userInput.length() <= 4) {
                    System.out.println(" Please add a task todo");
                } else {
                    task = new ToDos(userInput.substring(5));
                    list.addTask(task);
                    int taskCount = list.size();
                    ui.showTaskAdded(task, taskCount);
                }
            } else if (commandName == "deadline") {
                if (userInput.length() <= 8) {
                    System.out.println(" Please add a task after deadline");
                } else {
                    int slashIndex = userInput.indexOf("/");
                    if (!validateDate(userInput.substring(slashIndex + 4))) {
                        continue;
                    }

                    String taskName = userInput.substring(9, slashIndex - 1);
                    task = new Deadline(taskName);
                    task.setDeadline(userInput.substring(slashIndex + 4));
                    list.addTask(task);
                    int taskCount = list.size();
                    ui.showTaskAdded(task, taskCount);
                }
            } else if (commandName == "event") {
                if (userInput.length() <= 5) {
                    System.out.println(" Please add a task after event");
                } else {
                    int fromIndex = userInput.indexOf("/from");
                    int toIndex = userInput.indexOf("/to");
                    if (!validateDate(userInput.substring(fromIndex + 6, toIndex - 1))) {
                        System.out.println(userInput.substring(fromIndex + 6));
                        continue;
                    }
                    if (!validateDate(userInput.substring(toIndex + 4))) {
                        continue;
                    }
                    String taskName = userInput.substring(6, fromIndex - 1);
                    task = new Event(taskName);
                    task.setDuration(userInput.substring(fromIndex + 6, toIndex - 1), userInput.substring(toIndex + 4));
                    list.addTask(task);
                    int taskCount = list.size();
                    ui.showTaskAdded(task, taskCount);
                }
            } else if (commandName == "find") {
                System.out.println("finding...");
                String keyword = ui.getKeyword(userInput);
                ui.showFindResults(list, keyword);
            } else {
                // invalid input
                System.out.println("____________________________________________________________");
                System.out.println("Commands: todo, deadline, event, delete, mark, unmark");
            }
            System.out.println("____________________________________________________________");
        }
        try {
            storage.rewriteFile();
        } catch (IOException e) {
            System.out.println("Error while rewriting the file: " + e.getMessage());
        }
    }

    /**
     * Main method to start the Hannah application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Hannah().run();
    }

    /**
     * Validates that a date string is in the format yyyy-MM-dd.
     *
     * @param date The date string to validate.
     * @return true if the date is valid, false otherwise.
     */
    private boolean validateDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            System.out.println("____________________________________________________________");
            return false;
        }
    }
    public String getResponse(String input) {
        return "Hannah heard: " + input;
    }
}
