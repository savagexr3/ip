package voy.command;

import static voy.command.CommandType.DELETE;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.Task;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

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
     * @return a formatted feedback message describing the deleted task.
     * @throws OrbitException If the task index is invalid or saving fails.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OrbitException {
        try {
            assert index >= 0 && index < tasks.size() : "Index should be validated before execute()";
            Task removedTask = tasks.removeTask(index);
            storage.save(tasks);
            return UiMessageFormatter.formatTaskDeleted(tasks.size(), removedTask.toString());
        } catch (IndexOutOfBoundsException e) {
            throw new OrbitException("Invalid task number.");
        }
    }

    @Override
    public CommandType getCommandType() {
        return DELETE;
    }
}

