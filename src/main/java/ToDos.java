public class ToDos extends Task{
    String name;
    public ToDos(String name){
        super(name);
    }
    @Override
    public String toString(){
        return("[T]" + super.toString());
    }

    @Override
    public String saveTaskFormat(){
        String line =  "T | " + (isDone ? "1" : "0") + " | " + super.name;
        return line + System.lineSeparator();
    }
}
