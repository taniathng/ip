package hannah.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The Events class represents a task that occurs during a specific time frame.
 */
public class Events extends Task {
    String name;
    LocalDate startDuration;
    LocalDate endDuration;

    /**
     * Initializes an Events task with the specified name.
     *
     * @param name The name of the event task.
     */
    public Events(String name){
        super(name);

    }

    /**
     * Returns a string representation of the event task, including its name and duration.
     *
     * @return A string representing the event task.
     */
    @Override
    public String toString(){
        return("[E]" + super.toString() + " from " +  this.startDuration + " to " + this.endDuration);
    }

    /**
     * Sets the duration for the event using the specified start and end dates.
     *
     * @param startDuration The start date of the event in yyyy-MM-dd format.
     * @param endDuration   The end date of the event in yyyy-MM-dd format.
     */
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

    /**
     * Returns a formatted string to save the task in a file.
     *
     * @return A formatted string representing the task for file storage.
     */
    @Override
    public String saveTaskFormat(){
        String line = "E | " + (isDone ? "1" : "0") + " | " + super.name + " | from " + this.startDuration + " to " + this.endDuration;
        return line + System.lineSeparator();

    }
}
