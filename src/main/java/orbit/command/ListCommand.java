package orbit.command;

import orbit.storage.Storage;
import orbit.task.Task;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

import java.util.ArrayList;

/**
 * Represents a command that lists all tasks to the user.
 */
public class ListCommand implements Command {

    /**
     * Displays all tasks in the task list.
     * If the list is empty, a corresponding message is shown.
     *
     * @param tasks Task list to display.
     * @param storage Unused but required by the Command interface.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) {
        ArrayList<Task> list = tasks.getTasks();
        if (list.isEmpty()) {
            ConsoleUI.responseBox("No tasks yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        ConsoleUI.responseBox(sb.toString().trim());
    }
}


