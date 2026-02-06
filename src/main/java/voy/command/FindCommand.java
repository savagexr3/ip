package voy.command;

import static voy.command.CommandType.FIND;

import java.util.ArrayList;

import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.Task;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

/**
 * Represents a command that searches for tasks containing a given keyword.
 */
public class FindCommand implements Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified search keyword.
     *
     * @param keyword Keyword used to filter tasks.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Searches the task list for tasks whose descriptions contain the keyword
     * and displays the matching tasks to the user.
     *
     * @param tasks Task list to search from.
     * @param storage Unused but required by the Command interface.
     * @return a formatted list of matching tasks.
     * @throws OrbitException Not thrown by this command but declared for consistency.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws OrbitException {
        ArrayList<Task> list = tasks.filter(keyword);

        if (list.isEmpty()) {
            return UiMessageFormatter.formatResponse(
                    "No tasks found with keyword \"" + keyword.toUpperCase() + "\"."
            );
        }

        StringBuilder sb = new StringBuilder(
                "Here are the tasks with keyword \"" + keyword.toUpperCase() + "\" in your list:\n"
        );
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        return UiMessageFormatter.formatResponse(sb.toString().trim());
    }
    @Override
    public CommandType getCommandType() {
        return FIND;
    }
}
