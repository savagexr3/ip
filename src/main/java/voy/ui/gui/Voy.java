package voy.ui.gui;

import voy.command.Command;
import voy.command.CommandType;
import voy.exception.OrbitException;
import voy.parser.Parser;
import voy.storage.Storage;
import voy.task.TaskList;

/**
 * The {@code Voy} class represents the core logic handler of the Voy application.
 * It is responsible for parsing user input, executing commands, and returning
 * responses to be displayed on the user interface.
 */
public class Voy {

    /** Handles loading and saving of tasks to persistent storage. */
    private final Storage storage;

    /** Stores the current list of tasks managed by the application. */
    private final TaskList taskList;

    /** Tracks the type of the most recently executed command. */
    private CommandType lastCommandType = null;

    /**
     * Constructs a {@code Voy} instance and loads existing tasks from storage.
     *
     * @param filePath the file path used for storing and loading task data
     * @throws OrbitException if an error occurs while loading data from storage
     */
    public Voy(String filePath) throws OrbitException {
        this.storage = new Storage(filePath);
        this.taskList = storage.load();
    }

    /**
     * Processes a full user command and returns Voy's response message.
     *
     * @param input the raw command input entered by the user
     * @return the response message generated after executing the command
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            String response = c.execute(taskList, storage);
            lastCommandType = c.getCommandType();
            return response;
        } catch (OrbitException e) {
            lastCommandType = CommandType.ERROR;
            return "⚠ " + e.getMessage() + "\nTip: check command format!";
        }
    }

    /**
     * Returns the type of the most recently executed command.
     *
     * @return the last executed {@link CommandType}, or {@code null} if no command has been executed
     */
    public CommandType getCommandType() {
        return lastCommandType;
    }

    /**
     * Checks whether the given user input represents an exit command.
     *
     * @param input the raw command input entered by the user
     * @return {@code true} if the command is an exit command, {@code false} otherwise
     * @throws OrbitException if the input cannot be parsed into a valid command
     */
    public boolean isExitCommand(String input) throws OrbitException {
        return Parser.parse(input).isExit();
    }
}
