public class Task {
    boolean isDone = false;
    String name = "";

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public void setDone() {
        this.isDone = !this.isDone;
    }
}
