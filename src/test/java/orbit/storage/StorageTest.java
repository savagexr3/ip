package orbit.storage;

import orbit.exception.OrbitException;
import orbit.task.TaskList;
import orbit.task.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void load_fileDoesNotExist_returnsEmptyTaskList() throws OrbitException {
        Storage storage = new Storage(tempDir.resolve("orbit.txt").toString());

        TaskList list = storage.load();

        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void saveAndLoad_tasksPersistCorrectly() throws OrbitException {
        Storage storage = new Storage(tempDir.resolve("orbit.txt").toString());

        TaskList original = new TaskList();
        original.add(new ToDo("read book"));
        original.add(new ToDo("write tests"));

        storage.save(original);
        TaskList loaded = storage.load();

        assertEquals(2, loaded.size());
        assertTrue(loaded.getTask(0).toString().contains("read book"));
        assertTrue(loaded.getTask(1).toString().contains("write tests"));
    }

    @Test
    public void load_corruptedLine_isSkipped() throws Exception {
        Path file = tempDir.resolve("orbit.txt");

        // Write corrupted content manually
        java.nio.file.Files.writeString(file,
                "T | 1 | read book\n" +
                        "THIS IS CORRUPTED\n" +
                        "T | 0 | write tests\n"
        );

        Storage storage = new Storage(file.toString());
        TaskList list = storage.load();

        // corrupted line skipped, valid ones loaded
        assertEquals(2, list.size());
        assertTrue(list.getTask(0).toString().contains("read book"));
        assertTrue(list.getTask(1).toString().contains("write tests"));
    }
}
