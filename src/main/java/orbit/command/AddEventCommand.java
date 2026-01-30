package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.Event;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

/**
 * Represents a command that adds an {@link Event} task to the task list.
 */
public class AddEventCommand implements Command {
    private final Event event;

    /**
     * Constructs an AddEventCommand with the specified event task.
     *
     * @param event Event task to be added.
     */
    public AddEventCommand(Event event) {
        this.event = event;
    }

    /**
     * Adds the event task to the task list, displays confirmation to the user,
     * and saves the updated task list.
     *
     * @param tasks Task list to add the event to.
     * @param storage Storage used to persist tasks.
     * @throws OrbitException If saving to storage fails.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.add(event);
        ConsoleUI.newTaskBox(tasks.size(), event.toString());
        storage.save(tasks);
    }
}
