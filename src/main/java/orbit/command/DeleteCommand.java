package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.Task;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

/**
 * Represents a command that deletes a task from the task list.
 */
public class DeleteCommand implements Command {
    private final int index;

    /**
     * Constructs a DeleteCommand for the specified task index.
     *
     * @param index Index of the task to delete (0-based).
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Removes the specified task from the task list, displays confirmation,
     * and saves the updated list.
     *
     * @param tasks Task list to delete the task from.
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

        tasks.remove(index);
        ConsoleUI.deleteTaskBox(tasks.size(), t.toString());
        storage.save(tasks);
    }
}

