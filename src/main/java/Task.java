public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType taskType;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "["+ this.getStatusIcon() + "] " + this.description;
    }

    public TaskType getTaskType() {
        return null;
    }

    public String toFileString() {
        return " | "+ (this.isDone? "1" : "0") + " | " + this.description;
    }
}
