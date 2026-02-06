package orbit.ui;

/**
 * Handles all console-based user interface output for the Orbit chatbot.
 * <p>
 * This class is responsible for:
 * <ul>
 *   <li>Displaying the startup banner</li>
 *   <li>Printing formatted response boxes</li>
 *   <li>Showing task creation and deletion feedback</li>
 * </ul>
 * <p>
 * All output is printed to standard output and is safe to use in
 * both interactive and redirected (non-colored) environments.
 */
public class ConsoleUI {

    /** Horizontal separator used to visually group console output. */
    private static final String LINE_SEPARATOR =
            "──────────────────────────────────────────────────────────────────";

    /** Flag to enable or disable ANSI color output. */
    private static final boolean USE_COLOR = false; // false when redirected

    /** ANSI escape sequence to reset console color formatting. */
    private static final String ANSI_RESET = USE_COLOR ? "\u001B[0m" : "";

    /** ANSI escape sequence for blue-colored text. */
    private static final String ANSI_BLUE = USE_COLOR ? "\u001B[34m" : "";

    /**
     * Displays the Orbit welcome banner and greeting message.
     * <p>
     * This method prints the ASCII art logo followed by a friendly
     * greeting enclosed in a formatted response box.
     */
    public static void showWelcome() {
        String banner = "      ██████╗ ██████╗ ██████╗ ██╗████████╗\n"
                        + "     ██╔═══██╗██╔══██╗██╔══██╗██║╚══██╔══╝\n"
                        + "     ██║   ██║██████╔╝██████╔╝██║   ██║\n"
                        + "     ██║   ██║██╔══██╗██╔══██╗██║   ██║\n"
                        + "     ╚██████╔╝██║  ██║██████╔╝██║   ██║\n"
                        + "      ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚═╝   ╚═╝\n";
        System.out.println(banner);

        responseBox("Hello! I'm Orbit.", "What can I do for you?");
    }

    /**
     * Prints a formatted response box containing one or more messages.
     * <p>
     * Each input string may span multiple lines. All lines are printed
     * within a bordered box for improved readability.
     *
     * @param input one or more strings to display in the response box
     */
    public static void responseBox(String... input) {
        System.out.println(LINE_SEPARATOR);
        for (String block : input) {
            for (String line : block.split("\n")) {
                System.out.println(ANSI_BLUE + " " + line + ANSI_RESET);
            }
        }
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Displays feedback after a task has been successfully added.
     * <p>
     * Shows the added task details and updates the user on the current
     * number of tasks in the list.
     *
     * @param size  the total number of tasks after addition
     * @param input the task description to display
     */
    public static void newTaskBox(int size, String... input) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Got it. I've added this task:");
        for (String block : input) {
            for (String line : block.split("\n")) {
                System.out.println(ANSI_BLUE + " " + line + ANSI_RESET);
            }
        }
        System.out.println("Now you have " + size + " task" + (size > 1 ? "s" : "") + " in the list.");
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Displays feedback after a task has been removed.
     * <p>
     * Shows the removed task details and updates the user on the remaining
     * number of tasks in the list.
     *
     * @param size  the total number of tasks after deletion
     * @param input the task description that was removed
     */
    public static void deleteTaskBox(int size, String... input) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Noted. I've removed this task:");
        for (String block : input) {
            for (String line : block.split("\n")) {
                System.out.println(ANSI_BLUE + " " + line + ANSI_RESET);
            }
        }
        System.out.println("Now you have " + size + " task" + (size > 1 ? "s" : "") + " in the list.");
        System.out.println(LINE_SEPARATOR);
    }
}
