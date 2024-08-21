public class Events extends Task{
    String name;
    String startDuration;
    String endDuration;
    public Events(String name){
        super(name);

    }
    @Override
    public String toString(){
        return("[E]" + super.toString() + this.startDuration +  this.endDuration);
    }

    public void setDuration(String startDuration, String endDuration){
        this.startDuration = startDuration;
        this.endDuration = endDuration;
    }
}
