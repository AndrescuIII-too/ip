package proto;

import proto.exception.ProtoInvalidData;
import proto.task.Task;
import proto.task.TaskList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private final File file;

    public Storage(File file) {
        this.file = file;
    }

    public void save(TaskList taskList) throws IOException {
        try (FileWriter writer = new FileWriter(this.file)) {
            for (Task task : taskList.getTasks()) {
                writer.write(task.serialize() + "\n");
            }
        }
    }

    public TaskList load() throws IOException, ProtoInvalidData {
        ArrayList<Task> tasks = new ArrayList<>();

        // Ensure path and file exists
        this.file.getParentFile().mkdirs();
        this.file.createNewFile();

        try (FileReader reader = new FileReader(this.file)) {
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
