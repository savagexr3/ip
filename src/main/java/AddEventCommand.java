public class AddEventCommand implements Command {
    private final Event event;

    public AddEventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void  execute(TaskList tasks, Storage storage) throws OrbitException {
        tasks.add(event);
        ConsoleUI.newTaskBox(tasks.size(), event.toString());
        storage.save(tasks);
    }
}