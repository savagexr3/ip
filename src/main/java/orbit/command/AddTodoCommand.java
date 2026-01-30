package orbit.command;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.TaskList;
import orbit.task.ToDo;
import orbit.ui.ConsoleUI;

public class AddTodoCommand implements Command {
    private final ToDo todo;

    public AddTodoCommand(ToDo todo) {
        this.todo = todo;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.add(todo);
        ConsoleUI.newTaskBox(tasks.size(), todo.toString());
        storage.save(tasks);
    }
}
