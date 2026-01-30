package orbit.task;

/**
 * Represents a simple to-do task.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the given description.
     *
     * @param description Description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString(){
        return "[T]" + super.toString();
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.TODO;
    }

    @Override
    public String toFileString() {
        return "T" + super.toFileString();
    }
}
