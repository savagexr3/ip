public class ConsoleUI {
    private static final String LINE_SEPARATOR = "──────────────────────────────────────────────────────────────────";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";


    public static void responseBox(String... input) {
        System.out.println(LINE_SEPARATOR);
        for (String line : input) {
            System.out.println(ANSI_BLUE + line + ANSI_RESET);
        }
        System.out.println(LINE_SEPARATOR);
    }


}
