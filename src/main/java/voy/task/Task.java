package voy.task;

import java.time.LocalDateTime;

/**
 * Represents a generic task in the Orbit application.
 * A task has a description, completion status, and a task type.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType taskType;

    /**
     * Constructs a task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task for display purposes.
     *
     * @return "X" if the task is completed, otherwise a blank space.
     */
    public String getStatusIcon() {
        return isDone ? " ✅" : " ❌";
    }

    /**
     * Returns the description of the task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks whether the task is completed.
     *
     * @return true if the task is marked as done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the string representation of the task for display to the user.
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        return " " + getStatusIcon() + " " + description;
    }

    /**
     * Returns the type of the task.
     *
     * @return Task type (TODO, EVENT, or DEADLINE).
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * Returns the string representation of the task for file storage.
     *
     * @return File-formatted task string.
     */
    public String toFileString() {
        return " | " + (isDone ? "1" : "0") + " | " + description;
    }

    public LocalDateTime getEndDate() {
        return null;
    }

    public LocalDateTime getStartDate() {
        return null;
    }
}
