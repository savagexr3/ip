import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Orbit {
    public static void main(String[] args) throws IOException, OrbitException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TaskList taskList = new TaskList();

        System.out.println("  ____   ____  ____  ___ _____");
        System.out.println(" / __ \\ / __ \\|  _ \\|_ _|_   _|");
        System.out.println("| |  | | |  | | |_) || |  | |");
        System.out.println("| |  | | |  | |  _ < | |  | |");
        System.out.println("| |__| | |__| | |_) || |  | |");
        System.out.println(" \\____/ \\____/|____/|___| |_|");

        ConsoleUI.responseBox("Hello! I'm Orbit", "What can I do for you?");

        Storage storage = new Storage("./data/orbit.txt");
        taskList = storage.load();

        String userInput;

        while ((userInput = reader.readLine()) != null) {
            try {
                ParsedInput parsed = Parser.parse(userInput);
                Command command = parsed.getCommand();
                String argsText = parsed.getArgs();

                switch (command) {
                case LIST -> handleList(taskList.getTasks());

                case MARK -> {
                    int index = Parser.parseTaskIndex(argsText);
                    handleMark(taskList.getTask(index));
                }

                case UNMARK -> {
                    int index = Parser.parseTaskIndex(argsText);
                    handleUnmark(taskList.getTask(index));
                }

                case DELETE -> {
                    int index = Parser.parseTaskIndex(argsText);
                    handleDelete(taskList, index);
                }

                case TODO -> handleTodo(taskList, Parser.parseTodo(argsText));

                case DEADLINE -> handleDeadline(taskList, Parser.parseDeadline(argsText));

                case EVENT -> handleEvent(taskList, Parser.parseEvent(argsText));

                case BYE -> {
                    ConsoleUI.responseBox("Bye. Hope to see you again soon!");
                    storage.save(taskList);
                    return; // exits loop + program properly
                }
                }

            } catch (OrbitException e) {
                ConsoleUI.responseBox(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                // because taskList.getTask(index) can throw this
                ConsoleUI.responseBox("Invalid task number.");
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
                sb.append(index).append(". ").append(task).append("\n");
                index++;
            }
            ConsoleUI.responseBox(sb.toString().trim());
        }
    }

    private static void handleUnmark(Task undoneTask) {
        undoneTask.markAsNotDone();
        String msg = "OK, I've marked this task as not done yet:\n  " + undoneTask;
        ConsoleUI.responseBox(msg);
    }

    private static void handleMark(Task doneTask) {
        doneTask.markAsDone();
        String msg = "Nice! I've marked this task as done:\n  " + doneTask;
        ConsoleUI.responseBox(msg);
    }

    private static void handleTodo(TaskList taskList, ToDo newToDoTask) {
        taskList.add(newToDoTask);
        ConsoleUI.newTaskBox(taskList.size(), newToDoTask.toString());
    }

    private static void handleDeadline(TaskList taskList, Deadline newDeadlineTask) {
        taskList.add(newDeadlineTask);
        ConsoleUI.newTaskBox(taskList.size(), newDeadlineTask.toString());
    }

    private static void handleEvent(TaskList taskList, Event newEventTask) {
        taskList.add(newEventTask);
        ConsoleUI.newTaskBox(taskList.size(), newEventTask.toString());
    }

    private static void handleDelete(TaskList taskList, int index) {
        Task t = taskList.getTask(index);
        taskList.remove(index);
        ConsoleUI.deleteTaskBox(taskList.size(), t.toString());
    }
}
