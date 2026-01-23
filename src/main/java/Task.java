import java.util.ArrayList;

public class Task {
    private final ArrayList<String> tasks = new ArrayList<>();

    public void addTasks(String task) {
        this.tasks.add(task);
    }

    public ArrayList<String> getTasks() {
        return this.tasks;
    }

}
