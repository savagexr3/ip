package voy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import voy.command.Command;
import voy.exception.OrbitException;
import voy.parser.Parser;
import voy.storage.Storage;
import voy.task.TaskList;
import voy.ui.format.UiMessageFormatter;

/**
 * Entry point of the Orbit application.
 * Handles program startup and command execution loop.
 */
public class VoyCli {

    /**
     * Starts the Orbit application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) throws IOException, OrbitException {
        try {
            new VoyCli().run();
        } catch (OrbitException e) {
            UiMessageFormatter.formatResponse(e.getMessage());
        }
    }

    /**
     * Runs the main program loop by reading user input,
     * executing commands, and handling errors.
     */
    public void run() throws IOException, OrbitException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        UiMessageFormatter.getWelcomeMessage();

        Storage storage = new Storage("./data/orbit.txt");
        TaskList taskList = storage.load();

        boolean isExit = false;

        while (!isExit) {
            String fullCommand = reader.readLine();

            try {
                Command c = Parser.parse(fullCommand);
                c.execute(taskList, storage);
                isExit = c.isExit();
            } catch (OrbitException e) {
                UiMessageFormatter.formatResponse(e.getMessage());
            }
        }
    }
}
