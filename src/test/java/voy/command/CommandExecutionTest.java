package voy.command;

import org.junit.jupiter.api.Test;
import voy.exception.OrbitException;
import voy.storage.Storage;
import voy.task.TaskList;
import voy.task.ToDo;

import static org.junit.jupiter.api.Assertions.*;

public class CommandExecutionTest {

    @Test
    public void addTodo_execute_addsTask() throws OrbitException {
        TaskList list = new TaskList();
        Storage storage = new Storage("data/test.txt");

        AddTodoCommand cmd = new AddTodoCommand(new ToDo("read"));
        cmd.execute(list, storage);

        assertEquals(1, list.size());
    }

    @Test
    public void mark_execute_marksTask() throws OrbitException {
        TaskList list = new TaskList();
        Storage storage = new Storage("data/test.txt");

        list.add(new ToDo("read"));
        MarkCommand cmd = new MarkCommand(0);
        cmd.execute(list, storage);

        assertTrue(list.getTask(0).isDone());
    }

    @Test
    public void unmark_execute_unmarksTask() throws OrbitException {
        TaskList list = new TaskList();
        Storage storage = new Storage("data/test.txt");

        ToDo t = new ToDo("read");
        t.markAsDone();
        list.add(t);

        UnmarkCommand cmd = new UnmarkCommand(0);
        cmd.execute(list, storage);

        assertFalse(list.getTask(0).isDone());
    }

    @Test
    public void delete_execute_removesTask() throws OrbitException {
        TaskList list = new TaskList();
        Storage storage = new Storage("data/test.txt");

        list.add(new ToDo("read"));
        DeleteCommand cmd = new DeleteCommand(0);
        cmd.execute(list, storage);

        assertEquals(0, list.size());
    }

    @Test
    public void list_execute_doesNotThrow() throws OrbitException {
        TaskList list = new TaskList();
        Storage storage = new Storage("data/test.txt");

        ListCommand cmd = new ListCommand();
        assertNotNull(cmd.execute(list, storage));
    }

    @Test
    public void byeCommand_isExit_returnsTrue() {
        ByeCommand cmd = new ByeCommand();
        assertTrue(cmd.isExit());
    }
}
