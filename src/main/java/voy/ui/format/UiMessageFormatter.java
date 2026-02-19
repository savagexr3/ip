package voy.ui.format;

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
public class UiMessageFormatter {

    /** Horizontal separator used to visually group console output. */
    private static final String LINE_SEPARATOR =
            "──────────────────────────────────────────────────────────────────";
    /**
     * Displays the Voy welcome banner and greeting message.
     * <p>
     * This method prints the ASCII art logo followed by a friendly
     * greeting enclosed in a formatted response box.
     */
    public static void getWelcomeMessage() {
        String banner = "      ██████╗ ██████╗ ██████╗ ██╗████████╗\n"
                        + "     ██╔═══██╗██╔══██╗██╔══██╗██║╚══██╔══╝\n"
                        + "     ██║   ██║██████╔╝██████╔╝██║   ██║\n"
                        + "     ██║   ██║██╔══██╗██╔══██╗██║   ██║\n"
                        + "     ╚██████╔╝██║  ██║██████╔╝██║   ██║\n"
                        + "      ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚═╝   ╚═╝\n";
        System.out.println(banner);

        formatResponse("Hello! I'm Orbit.", "What can I do for you?");
    }


    /**
     * Builds a formatted response box containing one or more messages.
     *
     * @param input one or more strings to display in the response box
     * @return the formatted response box as a string
     */
    public static String formatResponse(String... input) {
        StringBuilder sb = new StringBuilder();

        for (String block : input) {
            for (String line : block.split("\n")) {
                sb.append(" ").append(line).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Builds feedback text after a task has been successfully added.
     *
     * @param size the total number of tasks after addition
     * @param input the task description to display
     * @return the formatted message as a string
     */
    public static String formatTaskAdded(int size, String... input) {
        StringBuilder sb = new StringBuilder();
        sb.append("Got it. I've added this task:\n");

        for (String block : input) {
            for (String line : block.split("\n")) {
                sb.append(" ").append(line).append("\n");
            }
        }

        sb.append("Now you have ").append(size).append(" task").append(size > 1 ? "s" : "")
                .append(" in the list.\n");
        return sb.toString();
    }

    /**
     * Builds feedback text after a task has been removed.
     *
     * @param size the total number of tasks after deletion
     * @param input the task description that was removed
     * @return the formatted message as a string
     */
    public static String formatTaskDeleted(int size, String... input) {
        StringBuilder sb = new StringBuilder();
        sb.append("Noted. I've removed this task:\n");

        for (String block : input) {
            for (String line : block.split("\n")) {
                sb.append(" ").append(line).append("\n");
            }
        }

        sb.append("Now you have ").append(size).append(" task").append(size > 1 ? "s" : "")
                .append(" in the list.\n");
        return sb.toString();
    }
}
