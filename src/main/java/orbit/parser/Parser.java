package orbit.parser;

import orbit.command.Command;
import orbit.command.CommandType;

import orbit.command.FindCommand;
import orbit.command.ListCommand;
import orbit.command.AddDeadlineCommand;
import orbit.command.AddEventCommand;
import orbit.command.AddTodoCommand;
import orbit.command.ByeCommand;
import orbit.command.DeleteCommand;
import orbit.command.MarkCommand;
import orbit.command.UnmarkCommand;

import orbit.exception.OrbitException;
import orbit.task.Deadline;
import orbit.task.Event;
import orbit.task.ToDo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input into executable {@link Command} objects.
 */
public class Parser {

    /**
     * Parses the user input string and returns the corresponding command.
     *
     * @param userInput Raw user input.
     * @return Command to be executed.
     * @throws OrbitException If the input is invalid or unsupported.
     */
    public static Command parse(String userInput) throws OrbitException {
        if (userInput == null) {
            throw new OrbitException("Input cannot be null.");
        }

        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            throw new OrbitException("Invalid input: empty command.");
        }

        String[] inputs = trimmed.split(" ", 2);
        CommandType cmd;

        try {
            cmd = CommandType.valueOf(inputs[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OrbitException("Unknown command: " + inputs[0]);
        }
        String args = (inputs.length == 2) ? inputs[1] : "";

        switch (cmd) {
        case LIST:
            return new ListCommand();

        case BYE:
            return new ByeCommand();

        case TODO:
            return new AddTodoCommand(parseTodo(args));

        case MARK:
            return new MarkCommand(parseTaskIndex(args));

        case UNMARK:
            return new UnmarkCommand(parseTaskIndex(args));

        case DELETE:
            return new DeleteCommand(parseTaskIndex(args));

        case DEADLINE:
            return new AddDeadlineCommand(parseDeadline(args));

        case EVENT:
            return new AddEventCommand(parseEvent(args));

        case FIND:
            return new FindCommand(args);

        default:
            throw new OrbitException("Invalid input: " + inputs[0]);
        }
    }

    public static int parseTaskIndex(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Please specify a task number.");
        }

        try {
            return Integer.parseInt(args.trim()) - 1; // convert to 0-based
        } catch (NumberFormatException e) {
            throw new OrbitException("Task number must be a number.");
        }
    }

    /**
     * Parses the input string and constructs a {@link Deadline} task.
     * The expected format is:
     * {@code deadline [task name] /by [YYYY-MM-DD HH:MM]}.
     *
     * @param args User input containing the deadline description.
     * @return A Deadline task created from the input.
     * @throws OrbitException If the input format or date/time is invalid.
     */
    public static Deadline parseDeadline(String args) throws OrbitException {
        try {
            if (args == null || args.trim().isEmpty()) {
                throw new OrbitException("Invalid deadline task. Please include task name.");
            }
            String[] parts = args.split(" /by ", 2);
            if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new OrbitException(
                    "Invalid deadline task description.\n"
                            + "Please include \"deadline [task name] /by [deadline date/time]\"."
                );
            }
            LocalDateTime deadlineDateTime = parseDateTime(parts[1]);
            return new Deadline(parts[0].trim(),deadlineDateTime);
        } catch (DateTimeParseException e) {
            throw new OrbitException("Invalid deadline date and/or time format. \n"
                    + "Please follow: [YYYY-MM-DD HH:MM]\n");
        }
    }

    /**
     * Parses the input string and constructs an {@link Event} task.
     * The expected format is:
     * {@code event [task name] /from [YYYY-MM-DD HH:MM] /to [YYYY-MM-DD HH:MM]}.
     *
     * @param args User input containing the event description.
     * @return An Event task created from the input.
     * @throws OrbitException If the input format or date/time is invalid.
     */
    public static Event parseEvent(String args) throws OrbitException {
        try {
            if (args == null || args.trim().isEmpty()) {
                throw new OrbitException("Invalid event task. Please include task name.");
            }

            String[] fromSplit = args.split(" /from ", 2);
            if (fromSplit.length != 2 || fromSplit[0].trim().isEmpty()) {
                throw new OrbitException(
                    "Invalid event task description. Please include\n" +
                            "\"event [task name] /from [start date/time] /to [end date/time]\"."
                );
            }

            String eventName = fromSplit[0].trim();
            String remainder = fromSplit[1];

            String[] toSplit = remainder.split(" /to ", 2);
            if (toSplit.length != 2 || toSplit[0].trim().isEmpty() || toSplit[1].trim().isEmpty()) {
                throw new OrbitException(
                    "Invalid event task description. Please include\n" +
                            "\"event [task name] /from [start date/time] /to [end date/time]\"."
                );
            }

            LocalDateTime startDateTime = parseDateTime(toSplit[0]);
            LocalDateTime endDateTime = parseDateTime(toSplit[1]);
            return new Event(eventName, startDateTime, endDateTime);
        } catch (DateTimeParseException e) {
            throw new OrbitException("Invalid event date and/or time format. \n"
                    + "Please follow: [YYYY-MM-DD HH:MM]\n");
        }
    }

    /**
     * Parses the input string and constructs a {@link ToDo} task.
     *
     * @param args User input containing the to-do description.
     * @return A ToDo task created from the input.
     * @throws OrbitException If the task description is empty.
     */
    public static ToDo parseTodo(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Invalid todo task. Please include task name.");
        }
        return new ToDo(args.trim());
    }

    /**
     * Parses a date-time string into a {@link LocalDateTime} object.
     * The expected format is {@code yyyy-MM-dd HH:mm}.
     *
     * @param dateTime Date-time string to parse.
     * @return Parsed LocalDateTime object.
     * @throws DateTimeParseException If the format is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * Formats a {@link LocalDateTime} into a user-friendly display string.
     *
     * @param dateTime Date-time to format.
     * @return Formatted date-time string.
     */
    public static String displayDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mma"));
    }
}
