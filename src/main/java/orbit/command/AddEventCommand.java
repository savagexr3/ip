package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.Event;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

public class AddEventCommand implements Command {
    private final Event event;

    public AddEventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.add(event);
        ConsoleUI.newTaskBox(tasks.size(), event.toString());
        storage.save(tasks);
    }
}