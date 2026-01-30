package orbit.command;

import java.util.ArrayList;

import orbit.exception.OrbitException;
import orbit.storage.Storage;
import orbit.task.Task;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

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
     * @throws OrbitException Not thrown by this command but declared for consistency.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        ArrayList<Task> list = tasks.filter(keyword);

        if (list.isEmpty()) {
            ConsoleUI.responseBox(
                    "No tasks found with keyword \"" + keyword.toUpperCase() + "\"."
            );
            return;
        }

        StringBuilder sb = new StringBuilder(
                "Here are the tasks with keyword \"" + keyword.toUpperCase() + "\" in your list:\n"
        );
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        ConsoleUI.responseBox(sb.toString().trim());
    }
}
