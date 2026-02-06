package voy.command;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.TaskList;

/**
 * Represents an executable user command.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @param tasks Task list affected by the command.
     * @param storage Storage used to persist tasks.
     * @return a formatted message describing the result of the command.
     * @throws OrbitException If command execution fails.
     */
    String execute(TaskList tasks, Storage storage) throws OrbitException;

    /**
     * Indicates whether the command should terminate the application.
     *
     * @return true if the command exits the application, false otherwise.
     */
    public default boolean isExit() {
        return false;
    }

    CommandType getCommandType();
}

