public class ByeCommand implements Command {
    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        ConsoleUI.responseBox("Bye. Hope to see you again soon!");
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
