package voy.task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        assert deadline != null : "Deadline datetime must not be null";
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[ 𝐃 ]" + super.toString() + " (by: " + displayDateTime(deadline) + ")";
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.DEADLINE;
    }

    @Override
    public String toFileString() {
        return "D" + super.toFileString() + " | " + deadline;
    }

    /**
     * Formats a {@link LocalDateTime} for display to the user.
     *
     * @param dateTime the date-time to format
     * @return a human-readable date-time string
     */
    public static String displayDateTime(LocalDateTime dateTime) {
        return dateTime.format(
                DateTimeFormatter.ofPattern("MMM d yyyy hh:mma")
        );
    }
}
