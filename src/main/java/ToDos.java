public class ToDos extends Task{
    String name;
    public ToDos(String name){
        super(name.substring(5));
    }
    @Override
    public String toString(){
        return("[T]" + super.toString());
    }
}