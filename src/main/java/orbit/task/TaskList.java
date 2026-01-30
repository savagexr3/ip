package orbit.task;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public void add(Task task) {
        this.tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public  Task getTask(int index){
        return this.tasks.get(index);
    }

    public void remove(int index){
        this.tasks.remove(index);
    }

    /**
     * Returns a list of tasks whose descriptions contain the specified keyword.
     *
     * @param keyword Keyword to search for in task descriptions.
     * @return A list of matching tasks; returns an empty list if no matches are found.
     */
    public ArrayList<Task> filter(String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int size(){
        return this.tasks.size();
    }
}
