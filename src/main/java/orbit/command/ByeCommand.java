package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

public class ByeCommand implements Command {
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        ConsoleUI.responseBox("Bye. Hope to see you again soon!");
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
