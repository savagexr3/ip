package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.TaskList;

/**
 * Represents an executable user command.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @param tasks Task list affected by the command.
     * @param storage Storage used to persist tasks.
     * @throws OrbitException If command execution fails.
     */
    void execute(TaskList tasks, Storage storage) throws OrbitException;

    /**
     * Indicates whether the command should terminate the application.
     *
     * @return true if the command exits the application, false otherwise.
     */
    public default boolean isExit() {
        return false;
    }
}

