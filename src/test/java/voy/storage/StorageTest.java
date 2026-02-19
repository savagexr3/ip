package voy.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import voy.task.TaskList;
import voy.task.ToDo;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void saveAndLoad_tasksPersisted() throws Exception {
        Path filePath = tempDir.resolve("voy.txt");

        Storage storage = new Storage(filePath.toString());

        TaskList list = new TaskList();
        list.add(new ToDo("read"));

        storage.save(list);

        TaskList loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("read", loaded.getTask(0).getDescription());
    }

    @Test
    public void load_deadlineTask_parsedCorrectly() throws Exception {
        Path file = tempDir.resolve("voy.txt");

        java.nio.file.Files.writeString(file,
                "D | 1 | submit report | 2025-01-01T13:00\n");

        Storage storage = new Storage(file.toString());
        TaskList list = storage.load();

        assertEquals(1, list.size());
    }

    @Test
    public void load_eventTask_parsedCorrectly() throws Exception {
        Path file = tempDir.resolve("voy.txt");

        java.nio.file.Files.writeString(file,
                "E | 0 | meeting | 2025-01-01T10:00 | 2025-01-01T12:00\n");

        Storage storage = new Storage(file.toString());
        TaskList list = storage.load();

        assertEquals(1, list.size());
    }

}
