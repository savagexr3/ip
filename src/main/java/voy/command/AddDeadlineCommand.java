package voy.command;

import static voy.command.CommandType.DEADLINE;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.Deadline;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

/**
 * Represents a command that adds a {@link Deadline} task to the task list.
 */
public class AddDeadlineCommand implements Command {
    private final Deadline deadline;

    /**
     * Creates an AddDeadlineCommand with the specified deadline task.
     *
     * @param deadline Deadline task to be added.
     */
    public AddDeadlineCommand(Deadline deadline) {
        this.deadline = deadline;
    }

    /**
     * Adds the deadline task to the task list, displays feedback to the user,
     * and saves the updated task list to storage.
     *
     * @param tasks Task list to add the deadline to.
     * @param storage Storage used to persist tasks.
     * @return a formatted feedback message describing the added task.
     * @throws OrbitException If an error occurs while saving tasks.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.addTask(deadline);
        storage.save(tasks);
        return UiMessageFormatter.formatTaskAdded(tasks.size(), deadline.toString());
    }
    @Override
    public CommandType getCommandType() {
        return DEADLINE;
    }
}
