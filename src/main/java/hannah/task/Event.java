package hannah.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    String name;
    LocalDate startDuration;
    LocalDate endDuration;
    public Event(String name){
        super(name);

    }
    @Override
    public String toString(){
        return("[E]" + super.toString() + " from " +  this.startDuration + " to " + this.endDuration);
    }

    @Override
    public void setDuration(String startDuration, String endDuration){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            this.startDuration = LocalDate.parse(startDuration, formatter);
            this.endDuration = LocalDate.parse(endDuration, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format for event duration. Please use yyyy-MM-dd.");
        }
    }

    @Override
    public String saveTaskFormat(){
        String line = "E | " + (isDone ? "1" : "0") + " | " + super.name + " | from " + this.startDuration + " to " + this.endDuration;
        return line + System.lineSeparator();

    }
}
