import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Orbit {
    public static void main(String[] args) throws IOException, OrbitException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Storage storage = new Storage();

        System.out.println("  ____   ____  ____  ___ _____");
        System.out.println(" / __ \\ / __ \\|  _ \\|_ _|_   _|");
        System.out.println("| |  | | |_| | |_) || |  | |");
        System.out.println("| |  | | |  | |  _ < | |  | |");
        System.out.println("| |__| | |__| | |_) || |  | |");
        System.out.println(" \\____/ \\____/|____/|___| |_|");

        ConsoleUI.responseBox("Hello! I'm Orbit", "What can I do for you?");

        String userInput;

        while ((userInput = reader.readLine()) != null) {
            try {
                String[] inputs = userInput.split(" ",2);
                Command command;
                try {
                    command = Command.valueOf(inputs[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new OrbitException("Invalid input: " + inputs[0]);
                }

                switch (command) {
                    case LIST -> handleList(storage.getTasks());

                    case MARK -> {;
                        if (inputs.length != 2) throw new OrbitException("Please specify a task number to mark.");
                        int index;
                        try {
                            index = Integer.parseInt(inputs[1]) - 1;
                            handleMark(storage.getTask(index));
                        } catch (NumberFormatException e) {
                            throw new OrbitException("Task number must be a number.");
                        } catch (IndexOutOfBoundsException e) {
                            throw new OrbitException("Invalid task number.");
                        }
                    }

                    case UNMARK -> {
                        if (inputs.length != 2) throw new OrbitException("Please specify a task number to unmark.");
                        int index;
                        try {
                            index = Integer.parseInt(inputs[1]) - 1;
                            handleUnmark(storage.getTask(index));
                        } catch (NumberFormatException e) {
                            throw new OrbitException("Task number must be a number.");
                        } catch (IndexOutOfBoundsException e) {
                            throw new OrbitException("Invalid task number.");
                        }
                    }

                    case DELETE -> {
                        if (inputs.length != 2) throw new OrbitException("Please specify a task number to delete.");
                        int index;
                        try {
                            index = Integer.parseInt(inputs[1]) - 1;
                            handleDelete(storage, index);
                        } catch (NumberFormatException e) {
                            throw new OrbitException("Task number must be a number.");
                        } catch (IndexOutOfBoundsException e) {
                            throw new OrbitException("Invalid task number.");
                        }
                    }

                    case TODO -> {
                        if (inputs.length != 2)
                            throw new OrbitException("Invalid todo task. Please include task name.");
                        String todoTask = inputs[1];
                        handleTodo(storage, new ToDo(todoTask));
                    }

                    case DEADLINE -> {
                        if (inputs.length != 2)
                            throw new OrbitException("Invalid deadline task. Please include task name.");
                        String deadlineTask = inputs[1];

                        String[] deadlineTaskDescription = deadlineTask.split(" /by ");
                        if (deadlineTaskDescription.length != 2)
                            throw new OrbitException("Invalid deadline task description.\nPlease include \"deadline [task name] /by [deadline date/time]\".");
                        String deadlineName = deadlineTaskDescription[0];
                        String deadlineTime = deadlineTaskDescription[1];
                        handleDeadline(storage, new Deadline(deadlineName, deadlineTime));
                    }

                    case EVENT -> {
                        if (inputs.length != 2)
                            throw new OrbitException("Invalid event task. Please include task name.");
                        String eventTask = inputs[1];

                        String[] eventTaskDescription = eventTask.split(" /from ");
                        if (eventTaskDescription.length != 2)
                            throw new OrbitException("Invalid event task description. Please include\n\"event [task name] /from [start date/time] /to [end date/time]\".");
                        String eventName = eventTask.split(" /from ")[0];
                        String eventDate = eventTask.split(" /from ")[1];

                        String[] eventTimeDescription = eventDate.split(" /to ", 2);
                        if (eventTimeDescription.length != 2)
                            throw new OrbitException("Invalid event task description. Please include\n\"event [task name] /from [start date/time] /to [end date/time]\".");
                        String eventStart = eventTimeDescription[0];
                        String eventEnd = eventTimeDescription[1];
                        handleEvent(storage, new Event(eventName, eventStart, eventEnd));
                    }

                    case BYE -> {
                        ConsoleUI.responseBox("Bye. Hope to see you again soon!");
                        break; // breaks out of switch, not loop
                    }

                }
            } catch (OrbitException e) {
                ConsoleUI.responseBox(e.getMessage());
            }

        }
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
        ConsoleUI.newTaskBox(storage.size(), newToDoTask.toString());

    }

    private static void handleDeadline(Storage storage, Deadline newDeadlineTask) {
        storage.add(newDeadlineTask);
        ConsoleUI.newTaskBox(storage.size(), newDeadlineTask.toString());
    }

    private static void handleEvent(Storage storage, Event newEventTask) {
        storage.add(newEventTask);
        ConsoleUI.newTaskBox(storage.size(), newEventTask.toString());
    }

    private static void handleDelete(Storage storage, int index) {
        Task t = storage.getTask(index);
        storage.remove(index);
        ConsoleUI.deleteTaskBox(storage.size(), t.toString());
    }
}


