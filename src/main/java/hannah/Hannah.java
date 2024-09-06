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
    public String run(String userInput) {
        TaskList list = (TaskList) this.storage.getTaskList();
        String result;
        Command command = parser.parse(userInput);
        Task task = null;
        String commandName = command.getCommandType();

        try {
            if (commandName.equals("bye")) {
                result = ui.showGoodbyeMessage();
            } else if (commandName.equals("list")) {
                result = ui.showTasks(list);
            } else if (commandName.equals("delete")) {
                int taskNumber = ui.getTaskNumber(userInput);
                task = list.getTask(taskNumber);
                list.deleteTask(taskNumber);
                int taskCount = list.size();
                result = ui.showTaskDeleted(taskCount, task);
            } else if (commandName.equals("unmark")) {
                int taskNumber = ui.getTaskNumber(userInput);
                task = list.getTask(taskNumber);
                list.unmarkTask(task);
                result = ui.showTaskUnmarked(task);
            } else if (commandName.equals("mark")) {
                int taskNumber = ui.getTaskNumber(userInput);
                task = list.getTask(taskNumber);
                list.markTask(task);
                result = ui.showTaskMarked(task);
            } else if (commandName.equals("todo")) {
                if (userInput.length() <= 4) {
                    result = "Please add a task todo";
                } else {
                    task = new ToDos(userInput.substring(5));
                    list.addTask(task);
                    int taskCount = list.size();
                    result = ui.showTaskAdded(task, taskCount);
                }
            } else if (commandName.equals("deadline")) {
                if (userInput.length() <= 8) {
                    result = "Please add a task after deadline";
                } else {
                    int slashIndex = userInput.indexOf("/");
                    if (!validateDate(userInput.substring(slashIndex + 4))) {
                        result = "Invalid date format. Please use yyyy-MM-dd.";
                    } else {
                        String taskName = userInput.substring(9, slashIndex - 1);
                        task = new Deadline(taskName);
                        task.setDeadline(userInput.substring(slashIndex + 4));
                        list.addTask(task);
                        int taskCount = list.size();
                        result = ui.showTaskAdded(task, taskCount);
                    }
                }
            } else if (commandName.equals("event")) {
                if (userInput.length() <= 5) {
                    result = "Please add a task after event";
                } else {
                    int fromIndex = userInput.indexOf("/from");
                    int toIndex = userInput.indexOf("/to");
                    if (!validateDate(userInput.substring(fromIndex + 6, toIndex - 1))) {
                        result = "Invalid date format for 'from'. Please use yyyy-MM-dd.";
                    } else if (!validateDate(userInput.substring(toIndex + 4))) {
                        result = "Invalid date format for 'to'. Please use yyyy-MM-dd.";
                    } else {
                        String taskName = userInput.substring(6, fromIndex - 1);
                        task = new Event(taskName);
                        task.setDuration(userInput.substring(fromIndex + 6, toIndex - 1),
                                userInput.substring(toIndex + 4));
                        list.addTask(task);
                        int taskCount = list.size();
                        result = ui.showTaskAdded(task, taskCount);
                    }
                }
            } else if (commandName.equals("find")) {
                String keyword = ui.getKeyword(userInput);
                result = ui.showFindResults(list, keyword);
            } else {
                result = "____________________________________________________________\n"
                        + "Commands: todo, deadline, event, delete, mark, unmark\n"
                        + "____________________________________________________________";
            }

            storage.rewriteFile();

        } catch (IOException e) {
            result = "Error while rewriting the file: " + e.getMessage();
        }

        return result;
    }

    /**
     * Main method to start the Hannah application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Hannah hannah = new Hannah();
        Scanner scanner = new Scanner(System.in);
        System.out.println(hannah.ui.showWelcomeMessage());

        while (true) {
            String userInput = scanner.nextLine();
            String response = hannah.run(userInput);
            System.out.println(response);

            if (userInput.equals("bye")) {
                break;
            }
        }
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
            return false;
        }
    }

    public String getResponse(String input) {
        return "Hannah heard: " + input;
    }
}
