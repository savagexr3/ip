public class UnmarkCommand implements Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws OrbitException {
        Task t;
        try {
            t = tasks.getTask(index);
        } catch (IndexOutOfBoundsException e) {
            throw new OrbitException("Invalid task number.");
        }

        t.markAsNotDone();
        ConsoleUI.responseBox("OK, I've marked this task as not done yet:\n  " + t);
        storage.save(tasks);
    }
}
