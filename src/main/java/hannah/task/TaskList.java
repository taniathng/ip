package hannah.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int taskNumber) {
        tasks.remove(taskNumber - 1);
    }

    public Task getTask(int taskNumber) {
        return tasks.get(taskNumber - 1);
    }

    public int size() {
        return tasks.size();
    }

    public void markTask(Task task){
        if (!task.isDone) {
            task.setDone();
        }
    }

    public void unmarkTask(Task task){
        if (task.isDone) {
            task.setDone();
        }
    }

    /**
     * Finds and returns a list of tasks that contain the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of matching tasks.
     */
    public List<Task> findTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
