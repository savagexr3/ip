package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.Deadline;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

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
     * @throws OrbitException If an error occurs while saving tasks.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.add(deadline);
        ConsoleUI.newTaskBox(tasks.size(), deadline.toString());
        storage.save(tasks);
    }
}
