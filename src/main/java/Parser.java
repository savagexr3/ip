public class Parser {
    
    public static ParsedInput parse(String userInput) throws OrbitException {
        if (userInput == null) {
            throw new OrbitException("Input cannot be null.");
        }

        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            throw new OrbitException("Invalid input: empty command.");
        }

        String[] inputs = trimmed.split(" ", 2);

        Command command;
        try {
            command = Command.valueOf(inputs[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OrbitException("Invalid input: " + inputs[0]);
        }

        String args = (inputs.length == 2) ? inputs[1] : "";
        return new ParsedInput(command, args);
    }

    public static int parseTaskIndex(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Please specify a task number.");
        }

        try {
            return Integer.parseInt(args.trim()) - 1; // convert to 0-based
        } catch (NumberFormatException e) {
            throw new OrbitException("Task number must be a number.");
        }
    }

    public static Deadline parseDeadline(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Invalid deadline task. Please include task name.");
        }

        String[] parts = args.split(" /by ", 2);
        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new OrbitException(
                    "Invalid deadline task description.\n" +
                            "Please include \"deadline [task name] /by [deadline date/time]\"."
            );
        }

        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    public static Event parseEvent(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Invalid event task. Please include task name.");
        }

        String[] fromSplit = args.split(" /from ", 2);
        if (fromSplit.length != 2 || fromSplit[0].trim().isEmpty()) {
            throw new OrbitException(
                    "Invalid event task description. Please include\n" +
                            "\"event [task name] /from [start date/time] /to [end date/time]\"."
            );
        }

        String eventName = fromSplit[0].trim();
        String remainder = fromSplit[1];

        String[] toSplit = remainder.split(" /to ", 2);
        if (toSplit.length != 2 || toSplit[0].trim().isEmpty() || toSplit[1].trim().isEmpty()) {
            throw new OrbitException(
                    "Invalid event task description. Please include\n" +
                            "\"event [task name] /from [start date/time] /to [end date/time]\"."
            );
        }

        return new Event(eventName, toSplit[0].trim(), toSplit[1].trim());
    }

    public static ToDo parseTodo(String args) throws OrbitException {
        if (args == null || args.trim().isEmpty()) {
            throw new OrbitException("Invalid todo task. Please include task name.");
        }
        return new ToDo(args.trim());
    }
}
