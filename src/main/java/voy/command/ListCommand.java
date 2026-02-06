package voy.command;

import static voy.command.CommandType.LIST;

import java.util.ArrayList;

import voy.storage.Storage;
import voy.task.Task;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

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
     * @return a formatted list of all tasks.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        ArrayList<Task> list = tasks.getTasks();
        if (list.isEmpty()) {
            return UiMessageFormatter.formatResponse("No tasks yet.");
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        return UiMessageFormatter.formatResponse(sb.toString().trim());
    }
    @Override
    public CommandType getCommandType() {
        return LIST;
    }
}


