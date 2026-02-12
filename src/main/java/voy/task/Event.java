package voy.task;

import java.time.LocalDateTime;

import voy.parser.Parser;

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
        assert startDate != null : "Event start must not be null";
        assert endDate != null : "Event end must not be null";
        assert !endDate.isBefore(startDate) : "Event end must not be before start";

        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[ 𝐄 ]" + super.toString()
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
