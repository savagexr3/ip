import java.util.ArrayList;

public class ListCommand implements Command {
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

