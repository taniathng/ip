package hannah.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The Deadlines class represents a task with a specific deadline.
 */
public class Deadlines extends Task {
    String name;
    LocalDate deadline;

    /**
     * Initializes a Deadlines task with the specified name.
     *
     * @param name The name of the deadline task.
     */
    public Deadlines(String name){
        super(name);
    }

    /**
     * Returns a string representation of the deadline task, including its name and deadline.
     *
     * @return A string representing the deadline task.
     */
    @Override
    public String toString(){
        return("[D]" + super.toString() + " by " +  this.deadline);
    }

    /**
     * Sets the deadline for the task using the specified date string.
     *
     * @param deadline The deadline date in yyyy-MM-dd format.
     */
    @Override
    public void setDeadline(String deadline) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            this.deadline = LocalDate.parse(deadline, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    /**
     * Returns the deadline of the task.
     *
     * @return The deadline as a LocalDate object.
     */
    public LocalDate getDeadline(){
        return this.deadline;
    }

    /**
     * Returns a formatted string to save the task in a file.
     *
     * @return A formatted string representing the task for file storage.
     */
    @Override
    public String saveTaskFormat(){
        String line = "D | " + (isDone ? "1" : "0") + " | " + super.name + " | by " + this.deadline;
        return line + System.lineSeparator();
    }
}