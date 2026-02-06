package voy.task;
import java.time.LocalDateTime;

import voy.parser.Parser;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    protected LocalDateTime deadline;

    /**
     * Constructs a Deadline task.
     *
     * @param description Description of the task.
     * @param deadline Deadline date and time.
     */
    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[ 𝐃 ]" + super.toString() + " (by: " + Parser.displayDateTime(deadline) + ")";
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.DEADLINE;
    }

    @Override
    public String toFileString() {
        return "D" + super.toFileString() + " | " + deadline;
    }
}
