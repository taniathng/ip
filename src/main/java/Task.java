public class Task {
    boolean isDone;
    String name;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public void setDone() {
        this.isDone = !this.isDone;
    }

    public String toString() {
        String done = this.isDone ? "X" : " ";
        return("[" + done + "] " + this.name);
    }
}
