package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.TaskList;

public interface Command {
    void execute(TaskList tasks, Storage storage) throws OrbitException;

    public default boolean isExit() {
        return false;
    }

}
