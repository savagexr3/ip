package voy.command;

import static voy.command.CommandType.BYE;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

/**
 * Represents a command that terminates the application.
 */
public class ByeCommand implements Command {

    /**
     * Displays a farewell message and saves the current task list.
     *
     * @param tasks Current task list.
     * @param storage Storage used to persist tasks.
     * @return a farewell message.
     * @throws OrbitException If saving to storage fails.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OrbitException {
        storage.save(tasks);
        return UiMessageFormatter.formatResponse("Bye. Hope to see you again soon!");
    }

    /**
     * Indicates that this command exits the application.
     *
     * @return true as this command terminates the program.
     */
    @Override
    public boolean isExit() {
        return true;
    }
    @Override
    public CommandType getCommandType() {
        return BYE;
    }
}
