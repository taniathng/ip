public class Deadlines extends Task{
    String name;
    String deadline;
    public Deadlines(String name){
        super(name);
    }
    @Override
    public String toString(){
        return("[D]" + super.toString() + " " +  this.deadline);
    }

    @Override
    public void setDeadline(String deadline){
        this.deadline = deadline;
    }
    @Override
    public String saveTaskFormat(){
        String line = "D | " + (isDone ? "1" : "0") + " | " + super.name + " | " + this.deadline;
        return line + System.lineSeparator();
    }
}