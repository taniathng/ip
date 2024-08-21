public class Deadlines extends Task{
    String name;
    String deadline;
    public Deadlines(String name){
        super(name);
    }
    @Override
    public String toString(){
        return("[D]" + super.toString() + this.deadline);
    }

    public void setDeadline(String deadline){
        this.deadline = deadline.substring(1);
    }
}