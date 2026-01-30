package orbit;

import orbit.command.Command;
import orbit.exception.OrbitException;
import orbit.parser.Parser;
import orbit.storage.Storage;
import orbit.task.TaskList;
import orbit.ui.ConsoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Orbit {
    public static void main(String[] args) throws IOException, OrbitException {
        try{
            new Orbit().run();
        } catch (OrbitException e) {
            ConsoleUI.responseBox(e.getMessage());
        }
    }

    public void run() throws IOException, OrbitException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ConsoleUI.showWelcome();

        Storage storage = new Storage("./data/orbit.txt");
        TaskList taskList = storage.load();

        boolean isExit = false;

        while (!isExit) {
            String fullCommand = reader.readLine();
            Command c = Parser.parse(fullCommand);
            c.execute(taskList, storage);
            isExit = c.isExit();
        }
    }
}
