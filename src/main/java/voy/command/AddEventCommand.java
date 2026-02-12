package voy.command;

import static voy.command.CommandType.EVENT;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.Event;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

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
     * @param tasks   Task list to add the event to.
     * @param storage Storage used to persist tasks.
     * @return a formatted feedback message describing the added event.
     * @throws OrbitException If saving to storage fails.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.addTask(event);
        storage.save(tasks);
        return UiMessageFormatter.formatTaskAdded(tasks.size(), event.toString());
    }
    @Override
    public CommandType getCommandType() {
        return EVENT;
    }
}
