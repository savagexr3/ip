package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.TaskList;
import orbit.task.ToDo;
import orbit.ui.ConsoleUI;

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
     * @throws OrbitException If saving to storage fails.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.add(todo);
        ConsoleUI.newTaskBox(tasks.size(), todo.toString());
        storage.save(tasks);
    }
}

