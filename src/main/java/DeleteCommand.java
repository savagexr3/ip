public class DeleteCommand implements Command {
    private final int index;

    public DeleteCommand(int index) {
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

        tasks.remove(index);
        ConsoleUI.deleteTaskBox(tasks.size(), t.toString());
        storage.save(tasks);
    }
}
