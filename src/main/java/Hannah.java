import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hannah {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Hannah");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        List<Task> list = new ArrayList<>();
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                // Exit the program
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list")) {
                System.out.println("Tasks on your list!");
                for (int i = 0; i < list.size(); i ++) {
                    System.out.println(i + 1 + ". " + list.get(i).toString());
                }
            } else if (userInput.split(" ")[0].equals("delete")) {
                System.out.println("Okay will remove the task: ");
                char taskNumberChar = userInput.charAt(7);
                int taskNumber = taskNumberChar - '0';
                list.remove(taskNumber -1);
                Task task = list.get(taskNumber - 1);
                System.out.println(task.toString());
                System.out.println("You now have " + list.size() + " tasks in your list.");
            } else if (userInput.split(" ")[0].equals("unmark")) {
                char taskNumberChar = userInput.charAt(7);
                int taskNumber = taskNumberChar - '0';
                Task task = list.get(taskNumber - 1);
                if (task.isDone) {
                    task.setDone();
                }
                System.out.println("Okay, task unmarked!");
                System.out.println(task.toString());
            } else if (userInput.split(" ")[0].equals("mark")) {
                char taskNumberChar = userInput.charAt(5);
                int taskNumber = taskNumberChar - '0';
                Task task = list.get(taskNumber - 1);
                if (!task.isDone) {
                    task.setDone();
                }
                System.out.println("GoodJob! Task Marked! ");
                System.out.println(task.toString());
            }  else if (userInput.split(" ")[0].equals("todo")) {
                if (userInput.length() <= 4) {
                    System.out.println(" please add a task todo");
                } else {
                    System.out.println("Added todo task!");
                    ToDos task = new ToDos(userInput);
                    list.add(task);
                    System.out.println(task.toString());
                    System.out.println("You now have " + list.size() + " tasks in your list.");
                }
            } else if (userInput.split(" ")[0].equals("deadline")) {
                if (userInput.length() <= 8) {
                    System.out.println(" please add a task after deadline");
                } else {
                    System.out.println("Added deadline task!");
                    int slashIndex = userInput.indexOf("/");
                    String taskName = userInput.substring(9, slashIndex);
                    Deadlines task = new Deadlines(taskName);
                    task.setDeadline(userInput.substring(slashIndex));
                    list.add(task);
                    System.out.println(task.toString());
                    System.out.println("You now have " + list.size() + " tasks in your list.");
                }
            } else if (userInput.split(" ")[0].equals("event")) {
                if (userInput.length() <= 5) {
                    System.out.println(" please add a task after event");
                } else {
                    System.out.println("Added event task!");
                    int fromIndex = userInput.indexOf("/from");
                    int toIndex = userInput.indexOf("/to");
                    String taskName = userInput.substring(6, fromIndex);
                    Events task = new Events(taskName);
                    task.setDuration(userInput.substring(fromIndex + 1, toIndex), userInput.substring(toIndex + 1));
                    list.add(task);
                    System.out.println(task.toString());
                    System.out.println("You now have " + list.size() + " tasks in your list.");
                }
            } else {
                // invalid input
                Task task = new Task(userInput);
                System.out.println("____________________________________________________________");
                System.out.println("Sorry i'm not too sure what this task is, " +
                        "please state either 'todo', 'deadline' or 'event' before your task");
                System.out.println("Commands: delete, mark, unmark");
            }
            System.out.println("____________________________________________________________");
        }
    }
}