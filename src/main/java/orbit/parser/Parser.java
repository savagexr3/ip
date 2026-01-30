package orbit.parser;

import orbit.command.Command;
import orbit.command.CommandType;

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

public class Parser {
    
    public static Command parse(String userInput) throws OrbitException {
        if (userInput == null) {
            throw new OrbitException("Input cannot be null.");
        }

        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            throw new OrbitException("Invalid input: empty command.");
        }

        String[] inputs = trimmed.split(" ", 2);
        CommandType cmd = CommandType.valueOf(inputs[0].toUpperCase());;
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

    public static ToDo parseTodo(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Invalid todo task. Please include task name.");
        }
        return new ToDo(args.trim());
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static String displayDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mma"));
    }
}
