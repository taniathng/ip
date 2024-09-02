package hannah;

import hannah.task.Deadlines;
import hannah.task.Events;
import hannah.task.ToDos;
import hannah.task.TaskList;
import hannah.task.Task;

import java.io.*;
import java.util.Scanner;

public class Storage {
    private String filePath;
    private TaskList taskList;
    private Task task;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.taskList = new TaskList();
    }
    public TaskList loadFile() throws FileNotFoundException {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] taskInfo = line.split(" \\| ");
                Task task = createTask(taskInfo);  // Parse each line into a hannah.task.Task object
                if (task != null) {
                    taskList.addTask(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return taskList;
    }
    public TaskList getTaskList() {
        return taskList;
    }


    private Task createTask(String[] taskInfo) {
        String type = taskInfo[0];
        boolean isDone = taskInfo[1].equals("1");

        switch (type) {
            case "T":
                ToDos todoTask = new ToDos(taskInfo[2]);
                if (isDone) {
                todoTask.setDone();
                }
                return todoTask;
            case "D":
                Deadlines deadlineTask = new Deadlines(taskInfo[2]);
                if (isDone) {
                    deadlineTask.setDone();
                }
                deadlineTask.setDeadline(taskInfo[3].substring(3));
                return deadlineTask;
            case "E":
                Events eventTask = new Events(taskInfo[2]);
                if (isDone) {
                    eventTask.setDone();
                }
                String duration = taskInfo[3];
                int fromIndex = duration.indexOf("from ");
                int toIndex = duration.indexOf("to ");
                eventTask.setDuration(duration.substring(fromIndex + 5, toIndex-1), duration.substring(toIndex + 3));
                return eventTask;
            default:
                return null;  // Handle any unexpected task types
        }
    }
    public void save(Task task) throws IOException {
        this.task = task;
        try (FileWriter fw = new FileWriter(filePath, true)) {  // 'true' enables append mode
            fw.write(task.saveTaskFormat());
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

//    public void updateDoneStatus(hannah.task.Task task) throws IOException {
//        File file = new File(filePath);
//        File tempFile = new File("tempFile.txt");
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file));
//             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
//
//            String currentLine;
//            while ((currentLine = reader.readLine()) != null) {
//                String[] taskInfo = currentLine.split(" \\| ");
//                if (taskInfo[2].equals(task.getName())) { // Match by task name
//                    writer.write(task.saveTaskStatus());
//                } else {
//                    writer.write(currentLine);
//                }
//                writer.newLine();
//            }
//        }
//    }
    public void rewriteFile() throws IOException {
        try (FileWriter fw = new FileWriter(filePath, false)) {  // 'false' overwrites the file
            for (Task task : taskList.getTasks()) {
                fw.write(task.saveTaskFormat());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}


