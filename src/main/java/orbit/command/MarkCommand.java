package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.Task;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

/**
 * Represents a command that marks a task as done.
 */
public class MarkCommand implements Command {
    private final int index;

    /**
     * Constructs a MarkCommand for the specified task index.
     *
     * @param index Index of the task to mark as done (0-based).
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the specified task as completed and saves the task list.
     *
     * @param tasks Task list containing the task.
     * @param storage Storage used to persist tasks.
     * @throws OrbitException If the task index is invalid or saving fails.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        Task task;
        try {
            task = tasks.getTask(index);
        } catch (IndexOutOfBoundsException e) {
            throw new OrbitException("Invalid task number.");
        }

        task.markAsDone();
        ConsoleUI.responseBox("Nice! I've marked this task as done:\n  " + task);
        storage.save(tasks);
    }
}

