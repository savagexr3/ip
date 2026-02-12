package voy.command;

import static voy.command.CommandType.UNMARK;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.Task;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

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
     * @return a formatted feedback message describing the updated task.
     * @throws OrbitException If the task index is invalid or saving fails.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OrbitException {
        try {
            assert index >= 0 && index < tasks.size() : "Index should be validated before execute()";
            Task task = tasks.getTask(index);
            task.markAsNotDone();
            storage.save(tasks);
            return UiMessageFormatter.formatResponse("OK, I've marked this task as not done yet:\n  " + task);
        } catch (IndexOutOfBoundsException e) {
            throw new OrbitException("Invalid task number.");
        }
    }
    @Override
    public CommandType getCommandType() {
        return UNMARK;
    }
}
