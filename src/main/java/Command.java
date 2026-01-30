interface Command {
    void execute(TaskList tasks, Storage storage) throws OrbitException;

    public default boolean isExit() {
        return false;
    }
}
