package proto;

import proto.exception.ProtoInvalidData;
import proto.task.Task;
import proto.task.TaskList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Storage {
    private final Config config;

    public Storage(Config config) {
        this.config = config;
    }

    private File getSaveFile() {
        return this.config.getSavePath().toFile();
    }

    /**
     * Save task list to file.
     *
     * @param taskList Task list.
     * @throws IOException If there was an error writing to the specified file.
     */
    public void save(TaskList taskList) throws IOException {
        try (FileWriter writer = new FileWriter(this.getSaveFile())) {
            for (Task task : taskList.getTasks()) {
                writer.write(task.serialize() + "\n");
            }
        }
    }

    /**
     * Load task list from file.
     *
     * @param path Path to save file.
     * @param force Whether to create the file if it does not exist.
     * @return Task list.
     * @throws IOException      If there was an error reading from the specified file.
     * @throws ProtoInvalidData If there was a deserialization error.
     */
    public TaskList loadFrom(Path path, boolean force) throws IOException, ProtoInvalidData {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = path.toFile();

        // Ensure path and file exists
        if (force && !file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } else if (!file.exists()) {
            throw new ProtoInvalidData("cannot find file " + path.toString());
        } else if (file.isDirectory()) {
            throw new ProtoInvalidData("path " + path.toString() + " is a directory");
        }

        this.config.setSavePath(path);

        try (FileReader reader = new FileReader(file)) {
            BufferedReader readStream = new BufferedReader(reader);
            String line;
            int lineNumber = 0;

            while ((line = readStream.readLine()) != null) {
                lineNumber++;
                try {
                    tasks.add(Task.deserialize(line));
                } catch (ProtoInvalidData e) {
                    e.setErrorIndex(lineNumber);
                    throw e;
                }
            }

            return new TaskList(tasks);
        }
    }
}
