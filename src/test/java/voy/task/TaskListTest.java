package voy.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void addTask_increasesSize() {
        TaskList list = new TaskList();
        list.add(new ToDo("read"));
        assertEquals(1, list.size());
    }

    @Test
    public void removeTask_decreasesSize() {
        TaskList list = new TaskList();
        list.add(new ToDo("read"));
        list.remove(0);
        assertEquals(0, list.size());
    }

    @Test
    public void filter_returnsMatchingTasks() {
        TaskList list = new TaskList();
        list.add(new ToDo("read book"));
        list.add(new ToDo("buy milk"));

        assertEquals(1, list.filter("book").size());
    }

    @Test
    public void removeTask_returnsDeletedTask() {
        TaskList list = new TaskList();
        ToDo t = new ToDo("read");
        list.add(t);

        assertEquals(t, list.removeTask(0));
    }
}

