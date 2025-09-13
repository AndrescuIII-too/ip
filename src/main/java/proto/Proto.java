package proto;

import proto.command.Command;
import proto.command.Context;
import proto.command.Parser;
import proto.command.Response;
import proto.exception.ProtoInvalidData;
import proto.task.TaskList;
import proto.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Proto {
    private static final Path DEFAULT_PATH = Paths.get(".", "data", "tasks.txt");
    private final Context context;
    private Status status;

    private Proto(Context context, Status status) {
        this.context = context;
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public enum Status {
        OK,
        FATAL,
    }

    public static Proto initialize(File file) {
        Ui ui = new Ui();
        Storage storage = new Storage(file);
        TaskList taskList = null;
        Status status = Status.OK;

        try {
            taskList = storage.load();
        } catch (IOException e) {
//            ui.showLoadingError(e);
            e.printStackTrace();
            status = Status.FATAL;
        } catch (ProtoInvalidData e) {
//            ui.showProtoException(e);
            e.printStackTrace();
            status = Status.FATAL;
        }

        Context context = new Context(ui, storage, taskList);
        return new Proto(context, status);
    }

    public static Proto initialize() {
        return Proto.initialize(Proto.DEFAULT_PATH.toFile());
    }

    public List<Response> getResponse(String input) {
        Command command = Parser.parseCommand(input);
        return command.execute(this.context);
    }
}
