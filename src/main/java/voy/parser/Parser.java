package voy.parser;

import voy.command.AddDeadlineCommand;
import voy.command.AddEventCommand;
import voy.command.AddTodoCommand;
import voy.command.ByeCommand;
import voy.command.Command;
import voy.command.CommandType;
import voy.command.DeleteCommand;
import voy.command.FindCommand;
import voy.command.FreeCommand;
import voy.command.ListCommand;
import voy.command.MarkCommand;
import voy.command.UnmarkCommand;
import voy.exception.OrbitException;

/**
 * Parses raw user input into executable {@link Command} objects.
 */
public class Parser {

    /**
     * Parses a raw user input string into an executable {@link Command}.
     *
     * @param userInput the raw input entered by the user
     * @return the corresponding {@code Command}
     * @throws OrbitException if the input is null, empty, or invalid
     */
    public static Command parse(String userInput) throws OrbitException {
        validateInput(userInput);

        String normalized = userInput.trim().replaceAll("\\s+", " ");
        String[] tokens = normalized.split(" ", 2);

        CommandType commandType = parseCommandType(tokens[0]);
        String args = (tokens.length == 2) ? tokens[1] : "";

        return buildCommand(commandType, args);
    }

    private static void validateInput(String userInput) throws OrbitException {
        if (userInput == null) {
            throw new OrbitException("Input cannot be null.");
        }
        if (userInput.trim().isEmpty()) {
            throw new OrbitException("Invalid input: empty command.");
        }
    }

    private static CommandType parseCommandType(String keyword) throws OrbitException {
        try {
            return CommandType.valueOf(keyword.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OrbitException(
                    "Unknown command: \"" + keyword + "\". \nTry: list, todo, deadline, event, mark, unmark, delete, find, free, bye."
            );
        }
    }

    private static void requireNoArgs(CommandType cmd, String args) throws OrbitException {
        if (!args.isBlank()) {
            throw new OrbitException(cmd.name().toLowerCase() + " does not take any arguments.");
        }
    }

    private static Command buildCommand(CommandType cmd, String args) throws OrbitException {
        switch (cmd) {
        case LIST:
            requireNoArgs(cmd, args);
            return new ListCommand();
        case BYE:
            requireNoArgs(cmd, args);
            return new ByeCommand();
        case TODO:
            return new AddTodoCommand(TaskParser.parseTodo(args));
        case MARK:
            return new MarkCommand(TaskParser.parseTaskIndex(args));
        case UNMARK:
            return new UnmarkCommand(TaskParser.parseTaskIndex(args));
        case DELETE:
            return new DeleteCommand(TaskParser.parseTaskIndex(args));
        case DEADLINE:
            return new AddDeadlineCommand(TaskParser.parseDeadline(args));
        case EVENT:
            return new AddEventCommand(TaskParser.parseEvent(args));
        case FIND:
            if (args.isBlank()) {
                throw new OrbitException("Find requires a keyword. Example: find book");
            }
            return new FindCommand(args);
        case FREE:
            return new FreeCommand(TaskParser.parseFreeTime(args));
        default:
            throw new OrbitException("Invalid command type.");
        }
    }
}
