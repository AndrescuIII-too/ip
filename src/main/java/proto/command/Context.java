package proto.command;

import java.io.IOException;
import java.util.List;

import proto.Storage;
import proto.task.TaskList;
import proto.ui.Ui;

public class Context {
    public final Ui ui;
    private final Storage storage;
    public final TaskList taskList;

    public Context(Ui ui, Storage storage, TaskList taskList) {
        this.ui = ui;
        this.storage = storage;
        this.taskList = taskList;
    }

    public List<Response> saveTaskList() {
        try {
            this.storage.save(this.taskList);
            return List.of();
        } catch (IOException e) {
            return List.of(
                    new Response(this.ui.showSavingError(e)));
        }
    }
}
