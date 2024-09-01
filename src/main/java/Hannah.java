import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Hannah {
    private Storage storage;
    private TaskList list;
    private static final String FILE_PATH = "src/data/Hannah.txt";
    private Ui ui;

    public Hannah() {
        this.ui = new Ui();
        this.storage = new Storage(FILE_PATH);
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
//            String userInput = scanner.nextLine();
            Task task = null;
            if (userInput.equals("bye")) {
                ui.showGoodbyeMessage();
                break;
            } else if (userInput.equals("list")) {
                ui.showTasks(list);
            } else if (userInput.split(" ")[0].equals("delete")) {
                int taskNumber = ui.getTaskNumber(userInput);
                int taskCount = list.size();
                list.deleteTask(taskNumber);
                ui.showTaskDeleted(taskNumber, taskCount, task);
            } else if (userInput.split(" ")[0].equals("unmark")) {
                int taskNumber = ui.getTaskNumber(userInput);
                task = list.getTask(taskNumber - 1);
                list.unmarkTask(task);
                ui.showTaskUnmarked(task);
            } else if (userInput.split(" ")[0].equals("mark")) {
                int taskNumber = ui.getTaskNumber(userInput);
                task = list.getTask(taskNumber - 1);
                list.markTask(task);
                ui.showTaskMarked(task);
            } else if (userInput.split(" ")[0].equals("todo")) {
                if (userInput.length() <= 4) {
                    System.out.println(" Please add a task todo");
                } else {
                    task = new ToDos(userInput.substring(5));
                    list.addTask(task);
                    int taskCount = list.size();
                    ui.showTaskAdded(task, taskCount);
                    try {
                        storage.save(task);  // Save all tasks after the loop iteration
                        System.out.println("Tasks saved successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving tasks: " + e.getMessage());
                    }
                }
            } else if (userInput.split(" ")[0].equals("deadline")) {
                if (userInput.length() <= 8) {
                    System.out.println(" please add a task after deadline");
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
                    try {
                        storage.save(task);  // Save all tasks after the loop iteration
                        System.out.println("Tasks saved successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving tasks: " + e.getMessage());
                    }
                }
            } else if (userInput.split(" ")[0].equals("event")) {
                if (userInput.length() <= 5) {
                    System.out.println(" please add a task after event");
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
                    try {
                        storage.save(task);  // Save all tasks after the loop iteration
                        System.out.println("Tasks saved successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving tasks: " + e.getMessage());
                    }
                }
            } else {
                // invalid input
                System.out.println("____________________________________________________________");
                System.out.println("Sorry i'm not too sure what this task is, " +
                        "please state either 'todo', 'deadline' or 'event' before your task");
                System.out.println("Commands: delete, mark, unmark");
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