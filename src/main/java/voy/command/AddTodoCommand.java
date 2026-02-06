package voy.command;

import static voy.command.CommandType.TODO;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.TaskList;
import voy.task.ToDo;
import voy.ui.format.UiMessageFormatter;

/**
 * Represents a command that adds a {@link ToDo} task to the task list.
 */
public class AddTodoCommand implements Command {
    private final ToDo todo;

    /**
     * Constructs an AddTodoCommand with the specified to-do task.
     *
     * @param todo To-do task to be added.
     */
    public AddTodoCommand(ToDo todo) {
        this.todo = todo;
    }

    /**
     * Adds the to-do task to the task list, displays confirmation,
     * and saves the updated list.
     *
     * @param tasks Task list to add the task to.
     * @param storage Storage used to persist tasks.
     * @return a formatted feedback message describing the added task.
     * @throws OrbitException If saving to storage fails.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.add(todo);
        storage.save(tasks);
        return UiMessageFormatter.formatTaskAdded(tasks.size(), todo.toString());
    }
    @Override
    public CommandType getCommandType() {
        return TODO;
    }
}

