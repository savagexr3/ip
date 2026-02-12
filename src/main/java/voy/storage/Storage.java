package voy.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import voy.exception.OrbitException;
import voy.task.Deadline;
import voy.task.Event;
import voy.task.Task;
import voy.task.TaskList;
import voy.task.ToDo;


/**
 * Handles loading and saving of task data to persistent storage.
 */
public class Storage {

    private final File file;

    /**
     * Constructs a Storage object using the specified file path.
     *
     * @param filePath Path to the data file.
     */
    public Storage(String filePath) {
        assert filePath != null : "Storage file path must not be null";
        assert !filePath.isBlank() : "Storage file path must not be blank";
        this.file = new File(filePath);
    }

    /**
     * Loads tasks from storage.
     *
     * @return A TaskList containing loaded tasks.
     * @throws OrbitException If loading fails.
     */
    public TaskList load() throws OrbitException {
        ensureFileExists();

        TaskList taskList = new TaskList();

        // always calls br.close() at the end even if an exception occurs to prevent resource leakage
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Task task = parseTask(line);
                    taskList.add(task);
                } catch (Exception e) {
                    // stretch goal: corrupted line → skip
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            throw new OrbitException("Error reading save file.");
        }

        return taskList;
    }

    /**
     * Saves the given task list to storage.
     *
     * @param taskList Task list to be saved.
     * @throws OrbitException If saving fails.
     */
    public void save(TaskList taskList) throws OrbitException {
        assert taskList != null : "TaskList must not be null when saving";
        assert taskList.getTasks() != null : "Internal task list must not be null";
        ensureFileExists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Task task : taskList.getTasks()) {
                bw.write(task.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new OrbitException("Error saving tasks.");
        }
    }

    /**
     * Checks if the file exists.
     * If not, creates new directory and new file.
     *
     * @throws OrbitException If saving fails.
     */
    private void ensureFileExists() throws OrbitException {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new OrbitException("Unable to create save file.");
        }
    }

    /**
     * Parses file input into task object.
     *
     * @param line File input containing task description.
     * @return Task object.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "Corrupted save line: " + line;

        boolean isDone = parts[1].equals("1");

        switch (parts[0]) {
        case "T":
            Task toDo = new ToDo(parts[2]);
            if (isDone) {
                toDo.markAsDone();
            }
            return toDo;

        case "D":
            assert parts.length == 4 : "Deadline format must have 4 parts";
            Task deadline = new Deadline(parts[2], LocalDateTime.parse(parts[3]));
            if (isDone) {
                deadline.markAsDone();
            }
            return deadline;

        case "E":
            assert parts.length == 5 : "Event format must have 5 parts";
            Task event = new Event(parts[2], LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
            if (isDone) {
                event.markAsDone();
            }
            return event;

        default:
            throw new IllegalArgumentException("Unknown task type");
        }
    }
}
