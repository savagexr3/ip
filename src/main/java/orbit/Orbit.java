package orbit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import orbit.command.Command;
import orbit.exception.OrbitException;
import orbit.parser.Parser;
import orbit.storage.Storage;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

/**
 * Entry point of the Orbit application.
 * Handles program startup and command execution loop.
 */
public class Orbit {

    /**
     * Starts the Orbit application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) throws IOException, OrbitException {
        try {
            new Orbit().run();
        } catch (OrbitException e) {
            ConsoleUI.responseBox(e.getMessage());
        }
    }

    /**
     * Runs the main program loop by reading user input,
     * executing commands, and handling errors.
     */
    public void run() throws IOException, OrbitException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ConsoleUI.showWelcome();

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
                ConsoleUI.responseBox(e.getMessage());
            }
        }
    }
}
