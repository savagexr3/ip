public class ParsedInput {
    private final Command command;
    private final String args;

    public ParsedInput(Command command, String args) {
        this.command = command;
        this.args = args;
    }

    public Command getCommand() {
        return command;
    }

    public String getArgs() {
        return args;
    }
}
