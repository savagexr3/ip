package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.Deadline;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

public class AddDeadlineCommand implements Command {
    private final Deadline deadline;

    public AddDeadlineCommand(Deadline deadline) {
        this.deadline = deadline;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.add(deadline);
        ConsoleUI.newTaskBox(tasks.size(), deadline.toString());
        storage.save(tasks);
    }
}
