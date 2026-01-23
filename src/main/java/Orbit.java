import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Orbit {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Task task = new Task();

        System.out.println("──────────────────────────────────────────────────────────────────────────────────────");
        System.out.println(" ██████╗ ██╗  ██╗ █████╗ ████████╗ ██████╗ ██████╗ ████████╗");
        System.out.println("██╔════╝ ██║  ██║██╔══██╗╚══██╔══╝██╔════╝ ██╔══██╗╚══██╔══╝");
        System.out.println("██║  ███╗███████║███████║   ██║   ██║  ███╗██████╔╝   ██║   ");
        System.out.println("██║   ██║██╔══██║██╔══██║   ██║   ██║   ██║██╔═══╝    ██║   ");
        System.out.println("╚██████╔╝██║  ██║██║  ██║   ██║   ╚██████╔╝██║        ██║   ");
        System.out.println(" ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝        ╚═╝   ");

        ConsoleUI.responseBox("Hello! I'm ChatGPT", "What can I do for you?");

        String userInput;

        while ((userInput = reader.readLine()) != null) {
            if (userInput.equalsIgnoreCase("bye")) break;

            if (userInput.equalsIgnoreCase("list")) {
                if (task.getTasks().isEmpty()) {
                    ConsoleUI.responseBox("No tasks yet.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int list = 1; list <= task.getTasks().size(); list++) {
                        sb.append(list + ". " + task.getTasks().get(list-1)).append("\n");
                    }
                    ConsoleUI.responseBox(sb.toString().trim());
                }
            } else {
                task.addTasks(userInput);
                ConsoleUI.responseBox("added: " + userInput);
            }
        }
        ConsoleUI.responseBox("Bye. Hope to see you again soon!");
    }
}

