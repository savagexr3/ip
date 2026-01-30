import java.util.ArrayList;

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

    public int size(){
        return this.tasks.size();
    }
}
