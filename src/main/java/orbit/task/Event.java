package orbit.task;

import orbit.parser.Parser;

import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime endDate;
    protected LocalDateTime startDate;

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + Parser.displayDateTime(startDate) + " to: " + Parser.displayDateTime(endDate) + ")";
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
