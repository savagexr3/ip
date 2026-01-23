import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Orbit {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Storage storage = new Storage();

        System.out.println("  ____   ____  ____  ___ _____");
        System.out.println(" / __ \\ / __ \\|  _ \\|_ _|_   _|");
        System.out.println("| |  | | |  | | |_) || |  | |");
        System.out.println("| |  | | |  | |  _ < | |  | |");
        System.out.println("| |__| | |__| | |_) || |  | |");
        System.out.println(" \\____/ \\____/|____/|___| |_|");

        ConsoleUI.responseBox("Hello! I'm Orbit", "What can I do for you?");

        String userInput;

        while ((userInput = reader.readLine()) != null) {
            if (userInput.equalsIgnoreCase("bye")) break;

            if (userInput.equalsIgnoreCase("list")) {
                handleList(storage.getTasks());
            } else if (userInput.startsWith("mark")) {
                int index = Integer.parseInt(userInput.split(" ")[1])-1;
                handleMark(storage.getTask(index));
            } else if (userInput.startsWith("unmark")) {
                int index = Integer.parseInt(userInput.split(" ")[1])-1;
                handleUnmark(storage.getTask(index));
            } else if (userInput.startsWith("todo")) {
                String todoTask = userInput.split(" ",2)[1];
                handleTodo(storage, new ToDo(todoTask));
            } else if (userInput.startsWith("deadline")) {
                String deadlineTask = userInput.split(" ",2)[1];
                String deadlineName = deadlineTask.split(" /by ")[0];
                String deadlineTime = deadlineTask.split(" /by ")[1];
                handleDeadline(storage,new Deadline(deadlineName, deadlineTime));
            } else if (userInput.startsWith("event")) {
                String eventTask = userInput.split(" ",2)[1];
                String eventName = eventTask.split(" /from ")[0];
                String eventDate = eventTask.split(" /from ")[1];
                String eventStart = eventDate.split(" /to ")[0];
                String eventEnd = eventDate.split(" /to ")[1];
                handleEvent(storage,new Event(eventName,eventStart,eventEnd));
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

    private static void handleTodo(Storage storage, ToDo newToDoTask) {
        storage.add(newToDoTask);
        ConsoleUI.newTaskBox(storage.size(),newToDoTask.toString());

    }

    private static void handleDeadline(Storage storage, Deadline newDeadlineTask) {
        storage.add(newDeadlineTask);
        ConsoleUI.newTaskBox(storage.size(),newDeadlineTask.toString());
    }

    private static void handleEvent(Storage storage, Event newEventTask) {
        storage.add(newEventTask);
        ConsoleUI.newTaskBox(storage.size(),newEventTask.toString());
    }
}


