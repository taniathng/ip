package hannah;

import hannah.command.Command;
import hannah.task.Task;
import hannah.task.TaskList;
import hannah.task.ToDos;
import hannah.task.Deadlines;
import hannah.task.Events;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.io.*;

public class Hannah {
    private Storage storage;
    private TaskList list;
    private Parser parser;
    private static final String FILE_PATH = "src/data/Hannah.txt";
    private Ui ui;

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
                    if (!validateDate(userInput.substring(slashIndex + 4))){
                        continue;
                    }

                    String taskName = userInput.substring(9, slashIndex -1);
                    task = new Deadlines(taskName);
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
                    if (!validateDate(userInput.substring(fromIndex + 6, toIndex -1))){
                        System.out.println(userInput.substring(fromIndex + 6));
                        continue;
                    }
                    if (!validateDate(userInput.substring(toIndex + 4))){
                        continue;
                    }
                    String taskName = userInput.substring(6, fromIndex -1);
                    task = new Events(taskName);
                    task.setDuration(userInput.substring(fromIndex + 6, toIndex-1), userInput.substring(toIndex + 4));
                    list.addTask(task);
                    int taskCount = list.size();
                    ui.showTaskAdded(task, taskCount);
                }
            } else {
                // invalid input
                System.out.println("____________________________________________________________");
                System.out.println("Commands: todo, deadline, event, delete, mark, unmark");
            }
            System.out.println("____________________________________________________________");
        }
        try {
            storage.rewriteFile();  // Call to method that might throw IOException
        } catch (IOException e) {
            System.out.println("Error while rewriting the file: " + e.getMessage());
        }    }
    public static void main(String[] args) {
        new Hannah().run();
    }

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
}