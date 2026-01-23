public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString(){
        return "[T]" + super.toString();
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.TODO;
    }
}
