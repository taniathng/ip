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
                for (Task task : list) {
                    String done = task.isDone ? "X" : " ";
                    System.out.println("[" + done + "]" + task.name);
                }
            } else if (userInput.contains("unmark")) {
                System.out.println("Okay, task unmarked!");
                char taskNumberChar = userInput.charAt(7);
                int taskNumber = taskNumberChar - '0';
                Task task = list.get(taskNumber - 1);
                if (task.isDone) {
                    task.setDone();
                }
                String done = task.isDone ? "X" : " ";
                System.out.println("[" + done + "]" + task.name);
            } else if (userInput.contains("mark")) {
                System.out.println("GoodJob! Task Marked! ");
                char taskNumberChar = userInput.charAt(5);
                int taskNumber = taskNumberChar - '0';
                Task task = list.get(taskNumber - 1);
                if (!task.isDone) {
                    task.setDone();
                }
                String done = task.isDone ? "X" : " ";
                System.out.println("[" + done + "]" + task.name);
            } else {
                // Echo the user input
                Task task = new Task(userInput);
                list.add(task);
                System.out.println("____________________________________________________________");
                System.out.println("added: " + task.name);
            }
            System.out.println("____________________________________________________________");
        }
    }
}