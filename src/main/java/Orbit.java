import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Orbit {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Storage storage = new Storage();

        System.out.println("──────────────────────────────────────────────────────────────────────────────────────");
        System.out.println(" ██████╗ ██╗  ██╗ █████╗ ████████╗ ██████╗ ██████╗ ████████╗");
        System.out.println("██╔════╝ ██║  ██║██╔══██╗╚══██╔══╝██╔════╝ ██╔══██╗╚══██╔══╝");
        System.out.println("██║  ███╗███████║███████║   ██║   ██║  ███╗██████╔╝   ██║   ");
        System.out.println("██║   ██║██╔══██║██╔══██║   ██║   ██║   ██║██╔═══╝    ██║   ");
        System.out.println("╚██████╔╝██║  ██║██║  ██║   ██║   ╚██████╔╝██║        ██║   ");
        System.out.println(" ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝        ╚═╝   ");

        ConsoleUI.responseBox("Hello! I'm ChatGPT", "What can I do for you?");

        String userInput;

        while ((userInput = reader.readLine()) != null) {
            ArrayList<Task> tasks = storage.getTasks();
            if (userInput.equalsIgnoreCase("bye")) break;

            if (userInput.equalsIgnoreCase("list")) {
                handleList(tasks);
            } else if (userInput.startsWith("unmark")) {
                handleUnmark(tasks, Integer.parseInt(userInput.split(" ")[1])-1);
            } else if (userInput.startsWith("mark")) {
                handleMark(tasks, Integer.parseInt(userInput.split(" ")[1])-1);
            } else {
                Task task = new Task(userInput);
                storage.add(task);
                ConsoleUI.responseBox("added: " + userInput);
            }
        }
        ConsoleUI.responseBox("Bye. Hope to see you again soon!");
    }

    private static void handleList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            ConsoleUI.responseBox("No tasks yet.");
        } else {
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            int index = 1;

            for (Task task : tasks) {
                sb.append(index + ". " + task.toString() + "\n");
                index++;
            }
            ConsoleUI.responseBox(sb.toString().trim());
        }
    }

    private static void handleUnmark(ArrayList<Task> tasks, int index) {
        StringBuilder sb = new StringBuilder("OK, I've marked this task as not done yet:\n");
        Task undoneTask = tasks.get(index);
        undoneTask.markAsNotDone();
        sb.append("  " + undoneTask.toString()).append("\n");
        ConsoleUI.responseBox(sb.toString().trim());
    }

    private static void handleMark(ArrayList<Task> tasks, int index) {
        StringBuilder sb = new StringBuilder("Nice! I've marked this task as done:\n");
        Task doneTask = tasks.get(index);
        doneTask.markAsDone();
        sb.append("  " + doneTask.toString()).append("\n");
        ConsoleUI.responseBox(sb.toString().trim());
    }
}


