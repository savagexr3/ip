package orbit.storage;

import orbit.exception.OrbitException;

import orbit.task.TaskList;
import orbit.task.Task;
import orbit.task.ToDo;
import orbit.task.Deadline;
import orbit.task.Event;

import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;

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

        boolean isDone = parts[1].equals("1");

        switch (parts[0]) {
        case "T":
            Task t = new ToDo(parts[2]);
            if (isDone) t.markAsDone();
            return t;

        case "D":
            Task d = new Deadline(parts[2], LocalDateTime.parse(parts[3]));
            if (isDone) d.markAsDone();
            return d;

        case "E":
            Task e = new Event(parts[2], LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
            if (isDone) e.markAsDone();
            return e;

        default:
            throw new IllegalArgumentException("Unknown task type");
        }
    }
}
