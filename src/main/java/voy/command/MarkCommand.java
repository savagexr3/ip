package voy.command;

import static voy.command.CommandType.MARK;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.Task;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

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
     * @return a formatted feedback message describing the updated task.
     * @throws OrbitException If the task index is invalid or saving fails.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OrbitException {
        Task task;
        try {
            task = tasks.getTask(index);
        } catch (IndexOutOfBoundsException e) {
            throw new OrbitException("Invalid task number.");
        }

        task.markAsDone();
        storage.save(tasks);
        return UiMessageFormatter.formatResponse("Nice! I've marked this task as done:\n  " + task);
    }
    @Override
    public CommandType getCommandType() {
        return MARK;
    }
}

