package voy.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import voy.exception.OrbitException;
import voy.task.Deadline;
import voy.task.Event;
import voy.task.ToDo;

/**
 * Parses task-related arguments (e.g., todo/deadline/event) into task objects.
 */
public class TaskParser {

    private static final String DEADLINE_SEPARATOR = " /by ";
    private static final String EVENT_FROM_SEPARATOR = " /from ";
    private static final String EVENT_TO_SEPARATOR = " /to ";

    private static final DateTimeFormatter INPUT_DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private TaskParser() {
        // Utility class: prevent instantiation.
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
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Invalid deadline task. Please include task name.");
        }

        String[] parts = splitDeadline(args);
        LocalDateTime by = parseDateTime(parts[1]);

        return new Deadline(parts[0].trim(), by);
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
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Invalid event task. Please include task name.");
        }

        String[] nameAndFrom = splitEventFrom(args);
        String eventName = nameAndFrom[0];

        String[] fromAndTo = splitEventTo(nameAndFrom[1]);
        LocalDateTime start = parseDateTime(fromAndTo[0]);
        LocalDateTime end = parseDateTime(fromAndTo[1]);

        if (end.isBefore(start)) {
            throw new OrbitException("Event end time cannot be before start time.");
        }

        return new Event(eventName, start, end);
    }

    /**
     * Parses a date-time string into a {@link LocalDateTime} object.
     *
     * @param dateTime the date-time string in {@code yyyy-MM-dd HH:mm} format
     * @return the parsed {@code LocalDateTime}
     * @throws OrbitException if the string cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateTime) throws OrbitException {
        try {
            return LocalDateTime.parse(dateTime.trim(), INPUT_DATE_TIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new OrbitException(
                    "Invalid date and/or time format.\n"
                            + "Please follow: [YYYY-MM-DD HH:MM]"
            );
        }
    }

    /**
     * Splits and validates a deadline argument string.
     * <p>
     * Expected format:
     * {@code [task name] /by [deadline date/time]}
     *
     * @param args the argument string following the {@code deadline} command
     * @return a two-element array: [description, deadlineDateTimeString]
     * @throws OrbitException if the format is invalid or required parts are missing
     */
    private static String[] splitDeadline(String args) throws OrbitException {
        String[] parts = args.split(DEADLINE_SEPARATOR, 2);

        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new OrbitException(
                    "Invalid deadline task description.\n"
                            + "Please include \"deadline [task name] /by [deadline date/time]\"."
            );
        }
        return parts;
    }

    private static String[] splitEventFrom(String args) throws OrbitException {
        String[] parts = args.split(EVENT_FROM_SEPARATOR, 2);

        if (parts.length != 2 || parts[0].trim().isEmpty()) {
            throw new OrbitException(
                    "Invalid event task description. Please include\n"
                            + "\"event [task name] /from [start date/time] /to [end date/time]\"."
            );
        }

        return new String[] { parts[0].trim(), parts[1] };
    }

    private static String[] splitEventTo(String remainder) throws OrbitException {
        String[] parts = remainder.split(EVENT_TO_SEPARATOR, 2);

        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new OrbitException(
                    "Invalid event task description. Please include\n"
                            + "\"event [task name] /from [start date/time] /to [end date/time]\"."
            );
        }

        return new String[] { parts[0].trim(), parts[1].trim() };
    }
}
