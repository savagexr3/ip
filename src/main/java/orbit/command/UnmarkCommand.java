package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.Task;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

/**
 * Represents a command that marks a task as not done.
 */
public class UnmarkCommand implements Command {
    private final int index;

    /**
     * Constructs an UnmarkCommand for the specified task index.
     *
     * @param index Index of the task to unmark (0-based).
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the specified task as not completed and saves the task list.
     *
     * @param tasks Task list containing the task.
     * @param storage Storage used to persist tasks.
     * @throws OrbitException If the task index is invalid or saving fails.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        Task t;
        try {
            t = tasks.getTask(index);
        } catch (IndexOutOfBoundsException e) {
            throw new OrbitException("Invalid task number.");
        }

        t.markAsNotDone();
        ConsoleUI.responseBox("OK, I've marked this task as not done yet:\n  " + t);
        storage.save(tasks);
    }
}
