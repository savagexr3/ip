package orbit.task;

import java.time.LocalDateTime;

import orbit.parser.Parser;

/**
 * Represents a task that occurs within a specific time period.
 */
public class Event extends Task {
    protected LocalDateTime endDate;
    protected LocalDateTime startDate;

    /**
     * Constructs an Event task.
     *
     * @param description Description of the event.
     * @param startDate Start date and time.
     * @param endDate End date and time.
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + Parser.displayDateTime(startDate)
                + " to: " + Parser.displayDateTime(endDate) + ")";
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EVENT;
    }

    @Override
    public String toFileString() {
        return "E" + super.toFileString() + " | " + startDate + " | " + endDate;
    }
}
