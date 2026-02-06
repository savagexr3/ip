package voy.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import voy.command.AddDeadlineCommand;
import voy.command.AddEventCommand;
import voy.command.AddTodoCommand;
import voy.command.ByeCommand;
import voy.command.Command;
import voy.command.CommandType;
import voy.command.DeleteCommand;
import voy.command.FindCommand;
import voy.command.ListCommand;
import voy.command.MarkCommand;
import voy.command.UnmarkCommand;
import voy.exception.OrbitException;
import voy.task.Deadline;
import voy.task.Event;
import voy.task.ToDo;

/**
 * Parses raw user input into executable {@link Command} objects.
 * <p>
 * The {@code Parser} is responsible for:
 * <ul>
 *   <li>Validating user input syntax</li>
 *   <li>Extracting command arguments</li>
 *   <li>Constructing appropriate command and task objects</li>
 * </ul>
 * <p>
 * All parsing-related errors are reported using {@link OrbitException}.
 */
public class Parser {

    /**
     * Parses a user input string and returns the corresponding {@link Command}.
     *
     * @param userInput the raw input entered by the user
     * @return a {@code Command} representing the user's request
     * @throws OrbitException if the input is invalid or cannot be parsed
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
            throw new OrbitException("I'm sorry, I don't understand that command.");
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

    /**
     * Parses a task index from user input.
     * <p>
     * Converts a 1-based index provided by the user into a 0-based index
     * used internally by the application.
     *
     * @param args the input string containing the task number
     * @return the zero-based task index
     * @throws OrbitException if the index is missing or not a valid number
     */
    public static int parseTaskIndex(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Please specify a task number.");
        }

        try {
            return Integer.parseInt(args.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new OrbitException("Task number must be a number.");
        }
    }

    /**
     * Parses a deadline task from user input.
     * <p>
     * Expected format:
     * {@code deadline [task name] /by [YYYY-MM-DD HH:MM]}
     *
     * @param args the input string describing the deadline task
     * @return a {@link Deadline} task object
     * @throws OrbitException if the input format or date-time is invalid
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
            return new Deadline(parts[0].trim(), deadlineDateTime);
        } catch (DateTimeParseException e) {
            throw new OrbitException(
                    "Invalid deadline date and/or time format.\n"
                            + "Please follow: [YYYY-MM-DD HH:MM]"
            );
        }
    }

    /**
     * Parses an event task from user input.
     * <p>
     * Expected format:
     * {@code event [task name] /from [start date/time] /to [end date/time]}
     *
     * @param args the input string describing the event task
     * @return an {@link Event} task object
     * @throws OrbitException if the input format or date-time is invalid
     */
    public static Event parseEvent(String args) throws OrbitException {
        try {
            if (args == null || args.trim().isEmpty()) {
                throw new OrbitException("Invalid event task. Please include task name.");
            }

            String[] fromSplit = args.split(" /from ", 2);
            if (fromSplit.length != 2 || fromSplit[0].trim().isEmpty()) {
                throw new OrbitException(
                        "Invalid event task description. Please include\n"
                                + "\"event [task name] /from [start date/time] /to [end date/time]\"."
                );
            }

            String eventName = fromSplit[0].trim();
            String remainder = fromSplit[1];

            String[] toSplit = remainder.split(" /to ", 2);
            if (toSplit.length != 2 || toSplit[0].trim().isEmpty() || toSplit[1].trim().isEmpty()) {
                throw new OrbitException(
                        "Invalid event task description. Please include\n"
                                + "\"event [task name] /from [start date/time] /to [end date/time]\"."
                );
            }

            LocalDateTime startDateTime = parseDateTime(toSplit[0]);
            LocalDateTime endDateTime = parseDateTime(toSplit[1]);
            return new Event(eventName, startDateTime, endDateTime);
        } catch (DateTimeParseException e) {
            throw new OrbitException(
                    "Invalid event date and/or time format.\n"
                            + "Please follow: [YYYY-MM-DD HH:MM]"
            );
        }
    }

    /**
     * Parses a todo task from user input.
     *
     * @param args the input string describing the todo task
     * @return a {@link ToDo} task object
     * @throws OrbitException if the task description is missing
     */
    public static ToDo parseTodo(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Invalid todo task. Please include task name.");
        }
        return new ToDo(args.trim());
    }

    /**
     * Parses a date-time string into a {@link LocalDateTime} object.
     *
     * @param dateTime the date-time string in {@code yyyy-MM-dd HH:mm} format
     * @return the parsed {@code LocalDateTime}
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(
                dateTime.trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );
    }

    /**
     * Formats a {@link LocalDateTime} for display to the user.
     *
     * @param dateTime the date-time to format
     * @return a human-readable date-time string
     */
    public static String displayDateTime(LocalDateTime dateTime) {
        return dateTime.format(
                DateTimeFormatter.ofPattern("MMM d yyyy hh:mma")
        );
    }
}
