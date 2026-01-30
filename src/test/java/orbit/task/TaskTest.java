package orbit.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    public void newTask_notDoneInitially() {
        Task task = new Task("read novel");
        assertFalse(task.isDone);
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void markAsDone_changeStatus() {
        Task task = new Task("read novel");
        assertFalse(task.isDone());
        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void markAsNotDone_resetStatus() {
        Task task = new Task("read novel");
        task.markAsDone();
        assertTrue(task.isDone());
        task.markAsNotDone();
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void toFileString_reflectsCompletionStatus() {
        Task task = new Task("read book");
        assertEquals(" | 0 | read book", task.toFileString());
        assertFalse(task.isDone());
        task.markAsDone();
        assertTrue(task.isDone());
        assertEquals(" | 1 | read book", task.toFileString());
    }
}
