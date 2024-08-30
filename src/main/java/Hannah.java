import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Hannah {
    private Storage storage;
    private List<Task> list;
    private static final String FILE_PATH = "src/data/Hannah.txt";

    public Hannah() {
        this.storage = new Storage(FILE_PATH);
        try {
            this.list = new ArrayList<Task>(storage.loadFile());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            this.list = new ArrayList<>(); // Initialize to an empty list if the file isn't found
        }
    }

    public void run() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Hannah");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        List<Task> list = this.storage.getTaskList();
        while (true) {
            String userInput = scanner.nextLine();
            Task task = null;
            if (userInput.equals("bye")) {
                // Exit the program
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list")) {
                System.out.println("Tasks on your list!");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i + 1 + ". " + list.get(i).toString());
                }
            } else if (userInput.split(" ")[0].equals("delete")) {
                System.out.println("Okay will remove the task: ");
                char taskNumberChar = userInput.charAt(7);
                int taskNumber = taskNumberChar - '0';
                list.remove(taskNumber - 1);
                task = list.get(taskNumber - 1);
                System.out.println(task.toString());
                System.out.println("You now have " + list.size() + " tasks in your list.");
            } else if (userInput.split(" ")[0].equals("unmark")) {
                String[] parts = userInput.split(" ");
                int taskNumber = 0;
                if (parts.length > 1) {
                    taskNumber = Integer.parseInt(parts[1]);
                }
                System.out.println("taskNumber:" + taskNumber);
                task = list.get(taskNumber - 1);
                if (task.isDone) {
                    task.setDone();
                }
                System.out.println("Okay, task unmarked!");
                System.out.println(task.toString());
            } else if (userInput.split(" ")[0].equals("mark")) {
                String[] parts = userInput.split(" ");
                int taskNumber = 0;
                if (parts.length > 1) {
                    taskNumber = Integer.parseInt(parts[1]);
                }
                System.out.println("taskNumber:" + taskNumber);
                task = list.get(taskNumber - 1);
                if (!task.isDone) {
                    task.setDone();
                }
                System.out.println("GoodJob! Task Marked! ");
                System.out.println(task.toString());
            } else if (userInput.split(" ")[0].equals("todo")) {
                if (userInput.length() <= 4) {
                    System.out.println(" please add a task todo");
                } else {
                    System.out.println("Added todo task!");
                    task = new ToDos(userInput.substring(5));
                    list.add(task);
                    System.out.println(task.toString());
                    System.out.println("You now have " + list.size() + " tasks in your list.");
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
                    System.out.println("Added deadline task!");

                    String taskName = userInput.substring(9, slashIndex -1);
                    task = new Deadlines(taskName);
                    task.setDeadline(userInput.substring(slashIndex + 4));
                    list.add(task);
                    System.out.println(task.toString());
                    System.out.println("You now have " + list.size() + " tasks in your list.");
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
                    System.out.println("Added event task!");
                    String taskName = userInput.substring(6, fromIndex -1);
                    task = new Events(taskName);
                    task.setDuration(userInput.substring(fromIndex + 6, toIndex-1), userInput.substring(toIndex + 4));
                    list.add(task);
                    System.out.println(task.toString());
                    System.out.println("You now have " + list.size() + " tasks in your list.");
                    try {
                        storage.save(task);  // Save all tasks after the loop iteration
                        System.out.println("Tasks saved successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving tasks: " + e.getMessage());
                    }
                }
            } else {
                // invalid input
                task = new Task(userInput);
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