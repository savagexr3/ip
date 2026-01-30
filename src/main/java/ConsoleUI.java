public class ConsoleUI {
    private static final String LINE_SEPARATOR = "──────────────────────────────────────────────────────────────────";
    private static final boolean USE_COLOR = false; // false when redirected
    private static final String ANSI_RESET = USE_COLOR ? "\u001B[0m" : "";
    private static final String ANSI_BLUE  = USE_COLOR ? "\u001B[34m" : "";

    public static void showWelcome() {
        System.out.println("  ____   ____  ____  ___ _____");
        System.out.println(" / __ \\ / __ \\|  _ \\|_ _|_   _|");
        System.out.println("| |  | | |  | | |_) || |  | |");
        System.out.println("| |  | | |  | |  _ < | |  | |");
        System.out.println("| |__| | |__| | |_) || |  | |");
        System.out.println(" \\____/ \\____/|____/|___| |_|");

        responseBox("Hello! I'm Orbit", "What can I do for you?");
    }

    public static void responseBox(String... input) {
        System.out.println(LINE_SEPARATOR);
        for (String block : input) {
            for (String line : block.split("\n")) {
                System.out.println(ANSI_BLUE + " " + line + ANSI_RESET);
            }
        }
        System.out.println(LINE_SEPARATOR);
    }

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

    public static void deleteTaskBox(int size, String... input) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Noted. I've removed this task:");
        for (String block : input) {
            for (String line : block.split("\n")) {
                System.out.println(ANSI_BLUE + " " + line + ANSI_RESET);
            }
        }
        System.out.println("Now you have " + size + " task" + (size > 1? "s" : "") + " in the list.");
        System.out.println(LINE_SEPARATOR);
    }
}
