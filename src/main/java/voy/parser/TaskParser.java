package voy.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

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
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);


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
            throw new OrbitException("Missing task number. Example: mark 1");
        }

        String trimmed = args.trim();

        // reject if contains spaces (e.g., "1 2")
        if (trimmed.contains(" ")) {
            throw new OrbitException("Task number must be a single number. Example: delete 2");
        }

        try {
            int oneBased = Integer.parseInt(trimmed);
            if (oneBased <= 0) {
                throw new OrbitException("Task number must be a positive integer.");
            }
            return oneBased - 1;
        } catch (NumberFormatException e) {
            throw new OrbitException("Task number must be a number. Example: unmark 3");
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
        String normalized = args.trim().replaceAll("\\s+", " ");
        ensureSingleFlag(normalized, " /by ");
        String[] parts = splitDeadline(normalized);

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
        String normalized = args.trim().replaceAll("\\s+", " ");
        ensureSingleFlag(normalized, " /from ");
        ensureSingleFlag(normalized, " /to ");
        String[] nameAndFrom = splitEventFrom(normalized);
        String eventName = nameAndFrom[0];

        String[] fromAndTo = splitEventTo(nameAndFrom[1]);
        LocalDateTime startDateTime = parseDateTime(fromAndTo[0]);
        LocalDateTime endDateTime = parseDateTime(fromAndTo[1]);
        assert startDateTime != null : "Start datetime should not be null";
        assert endDateTime != null : "End datetime should not be null";
        if (!endDateTime.isAfter(startDateTime)) {
            throw new OrbitException("Event end must be after start.");
        }

        return new Event(eventName, startDateTime, endDateTime);
    }

    /**
     * Parses a date-time string into a {@link LocalDateTime} object.
     *
     * @param dateTime the date-time string in {@code yyyy-MM-dd HH:mm} format
     * @return the parsed {@code LocalDateTime}
     * @throws OrbitException if the string cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateTime) throws OrbitException {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            throw new OrbitException("Missing date/time. Use: YYYY-MM-DD HH:mm");
        }
        try {
            return LocalDateTime.parse(dateTime.trim(), INPUT_DATE_TIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new OrbitException(
                    "Invalid date and/or time format.\n"
                            + "Use: YYYY-MM-DD HH:mm (e.g., 2026-03-12 14:30)"
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

    private static void ensureSingleFlag(String args, String flag) throws OrbitException {
        int first = args.indexOf(flag);
        int last = args.lastIndexOf(flag);
        if (first != -1 && first != last) {
            throw new OrbitException("Duplicate parameter: " + flag.trim());
        }
    }

    private static String[] splitEventFrom(String args) throws OrbitException {
        String[] parts = args.split(EVENT_FROM_SEPARATOR, 2);

        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new OrbitException(
                    "Invalid event task description. "
                            + "\nUse: event [name] /from [YYYY-MM-DD HH:mm] /to [YYYY-MM-DD HH:mm]"
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
    /**
     * Parses the FREE command duration argument.
     *
     * <p>
     * Supported formats:
     * <ul>
     *     <li>{@code 2h} - 2 hours</li>
     *     <li>{@code 90m} - 90 minutes</li>
     * </ul>
     *
     * @param freeTime raw duration string
     * @return duration in minutes
     * @throws OrbitException if format is invalid
     */
    public static long parseFreeTime(String freeTime) throws OrbitException {
        if (freeTime == null || freeTime.trim().isEmpty()) {
            throw new OrbitException("Invalid free time. Use format: free 2h or free 90m.");
        }

        // Allow inputs like "2 h", "90 m" by removing whitespace
        String trimmed = freeTime.trim().toLowerCase().replaceAll("\\s+", "");

        try {
            long minutes;

            if (trimmed.endsWith("h")) {
                long hours = Long.parseLong(trimmed.substring(0, trimmed.length() - 1));
                minutes = hours * 60;
            } else if (trimmed.endsWith("m")) {
                minutes = Long.parseLong(trimmed.substring(0, trimmed.length() - 1));
            } else {
                // Default: treat as hours if no suffix
                long hours = Long.parseLong(trimmed);
                minutes = hours * 60;
            }

            if (minutes <= 0) {
                throw new OrbitException("Free time must be greater than 0. Example: free 2h");
            }

            return minutes;

        } catch (NumberFormatException e) {
            throw new OrbitException("Invalid free time format. Use: free 2h or free 90m.");
        }
    }
}
