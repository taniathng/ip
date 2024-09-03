package hannah.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    String name;
    LocalDate deadline;
    public Deadline(String name){
        super(name);
    }
    @Override
    public String toString(){
        return("[D]" + super.toString() + " by " +  this.deadline);
    }

    @Override
    public void setDeadline(String deadline) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            this.deadline = LocalDate.parse(deadline, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }
    public LocalDate getDeadline(){
        return this.deadline;
    }
    @Override
    public String saveTaskFormat(){
        String line = "D | " + (isDone ? "1" : "0") + " | " + super.name + " | by " + this.deadline;
        return line + System.lineSeparator();
    }
}