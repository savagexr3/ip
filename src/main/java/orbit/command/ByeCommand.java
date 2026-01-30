package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

/**
 * Represents a command that terminates the application.
 */
public class ByeCommand implements Command {

    /**
     * Displays a farewell message and saves the current task list.
     *
     * @param tasks Current task list.
     * @param storage Storage used to persist tasks.
     * @throws OrbitException If saving to storage fails.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        ConsoleUI.responseBox("Bye. Hope to see you again soon!");
        storage.save(tasks);
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
}
