import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Chatgpt {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("──────────────────────────────────────────────────────────────────────────────────────");
        System.out.println(" ██████╗ ██╗  ██╗ █████╗ ████████╗ ██████╗ ██████╗ ████████╗");
        System.out.println("██╔════╝ ██║  ██║██╔══██╗╚══██╔══╝██╔════╝ ██╔══██╗╚══██╔══╝");
        System.out.println("██║  ███╗███████║███████║   ██║   ██║  ███╗██████╔╝   ██║   ");
        System.out.println("██║   ██║██╔══██║██╔══██║   ██║   ██║   ██║██╔═══╝    ██║   ");
        System.out.println("╚██████╔╝██║  ██║██║  ██║   ██║   ╚██████╔╝██║        ██║   ");
        System.out.println(" ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝        ╚═╝   ");

        ConsoleUI.responseBox("Hello! I'm ChatGPT", "What can I do for you?");
        String userInput = reader.readLine();
        while (!userInput.equalsIgnoreCase("bye")) {
            ConsoleUI.responseBox(userInput);
            userInput = reader.readLine();
        }
        ConsoleUI.responseBox("Bye. Hope to see you again soon!");
    }
}

