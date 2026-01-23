import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Orbit {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Storage storage = new Storage();

        System.out.println("──────────────────────────────────────────────────────────────────");
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
            } else if (userInput.startsWith("mark")) {
                int index = Integer.parseInt(userInput.split(" ")[1])-1;
                handleMark(storage.getTask(index));
            } else if (userInput.startsWith("unmark")) {
                int index = Integer.parseInt(userInput.split(" ")[1])-1;
                handleUnmark(storage.getTask(index));
            } else if (userInput.startsWith("todo")) {
                String todoTask = userInput.split(" ",2)[1];
                handleTodo(tasks, new ToDo(todoTask));
            } else if (userInput.startsWith("deadline")) {
                String deadlineTask = userInput.split(" ",2)[1];
                String deadlineName = deadlineTask.split(" /by ")[0];
                String deadlineTime = deadlineTask.split(" /by ")[1];
                handleDeadline(tasks,new Deadline(deadlineName, deadlineTime));
            } else if (userInput.startsWith("event")) {
                String eventTask = userInput.split(" ",2)[1];
                String eventName = eventTask.split(" /from ")[0];
                String eventDate = eventTask.split(" /from ")[1];
                String eventStart = eventDate.split(" /to ")[0];
                String eventEnd = eventDate.split(" /to ")[1];
                handleEvent(tasks,new Event(eventName,eventStart,eventEnd));
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

    private static void handleUnmark(Task undoneTask) {
        StringBuilder sb = new StringBuilder("OK, I've marked this task as not done yet:\n");
        undoneTask.markAsNotDone();
        sb.append("  " + undoneTask.toString()).append("\n");
        ConsoleUI.responseBox(sb.toString().trim());
    }

    private static void handleMark(Task doneTask) {
        StringBuilder sb = new StringBuilder("Nice! I've marked this task as done:\n");
        doneTask.markAsDone();
        sb.append("  " + doneTask.toString()).append("\n");
        ConsoleUI.responseBox(sb.toString().trim());
    }

    private static void handleTodo(ArrayList<Task> tasks, ToDo newToDoTask) {
        tasks.add(newToDoTask);
        ConsoleUI.newTaskBox(tasks.size(),newToDoTask.toString());

    }

    private static void handleDeadline(ArrayList<Task> tasks, Deadline newDeadlineTask) {
        tasks.add(newDeadlineTask);
        ConsoleUI.newTaskBox(tasks.size(),newDeadlineTask.toString());
    }

    private static void handleEvent(ArrayList<Task> tasks, Event newEventTask) {
        tasks.add(newEventTask);
        ConsoleUI.newTaskBox(tasks.size(),newEventTask.toString());
    }
}


