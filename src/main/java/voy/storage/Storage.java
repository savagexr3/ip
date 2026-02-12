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
    private static final String DELIMITER_REGEX = " \\| ";
    private static final String TODO_TYPE = "T";
    private static final String DEADLINE_TYPE = "D";
    private static final String EVENT_TYPE = "E";

    private static final int IDX_TYPE = 0;
    private static final int IDX_DONE = 1;
    private static final int IDX_DESCRIPTION = 2;
    private static final int IDX_DEADLINE = 3;
    private static final int IDX_EVENT_FROM = 3;
    private static final int IDX_EVENT_TO = 4;

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

        loadStorageTask(taskList);

        return taskList;
    }

    private void loadStorageTask(TaskList taskList) throws OrbitException {
        // always calls br.close() at the end even if an exception occurs to prevent resource leakage
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                addTaskToList(line, taskList);
            }
        } catch (IOException e) {
            throw new OrbitException("Error reading save file.");
        }
    }

    private void addTaskToList(String line, TaskList taskList) {
        try {
            Task task = parseTask(line);
            taskList.addTask(task);
        } catch (Exception e) {
            // stretch goal: corrupted line → skip
            System.out.println("Skipping corrupted line: " + line);
        }
    }

    /**
     * Saves the given task list to storage.
     *
     * @param taskList Task list to be saved.
     * @throws OrbitException If saving fails.
     */
    public void save(TaskList taskList) throws OrbitException {
        assert taskList != null : "TaskList must not be null";
        assert taskList.getTasks() != null : "Internal task list must not be null";
        ensureFileExists();

        saveStorageTask(taskList);
    }

    private void saveStorageTask(TaskList taskList) throws OrbitException {
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
        assert line != null : "Line from file should not be null";

        String[] parts = line.split(DELIMITER_REGEX);

        assert parts.length >= 3 : "Saved task line format corrupted: " + line;

        return getTask(parts);
    }

    private static Task getTask(String[] parts) {
        boolean isDone = parts[IDX_DONE].equals("1");

        switch (parts[IDX_TYPE]) {
        case TODO_TYPE:
            Task toDo = new ToDo(parts[IDX_DESCRIPTION]);
            if (isDone) {
                toDo.markAsDone();
            }
            return toDo;

        case DEADLINE_TYPE:
            assert parts.length == 4 : "Deadline format should have 4 parts";
            Task deadline = new Deadline(parts[IDX_DESCRIPTION], LocalDateTime.parse(parts[IDX_DEADLINE]));
            if (isDone) {
                deadline.markAsDone();
            }
            return deadline;

        case EVENT_TYPE:
            assert parts.length == 5 : "Event format should have 5 parts";
            Task event = new Event(parts[IDX_DESCRIPTION], LocalDateTime.parse(parts[IDX_EVENT_FROM]),
                    LocalDateTime.parse(parts[IDX_EVENT_TO]));
            if (isDone) {
                event.markAsDone();
            }
            return event;

        default:
            assert false : "Unrecognized task type: " + parts[1];
            throw new IllegalArgumentException("Unknown task type");
        }
    }
}
