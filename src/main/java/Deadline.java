import java.time.LocalDateTime;

public class Deadline extends Task{
    protected LocalDateTime deadline;

    public Deadline(String description,LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString(){
            return "[D]" + super.toString() + " (by: " + Parser.displayDateTime(deadline) + ")";
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
