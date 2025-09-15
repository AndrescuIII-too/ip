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
import java.util.ArrayList;
import java.util.List;

public class Proto {
    private static final Path DEFAULT_PATH = Paths.get(".", "data", "tasks.txt");
    private final Context context;
    private Status status;
    public final List<Response> initialResponses;

    private Proto(Context context, Status status, List<Response> initialResponses) {
        this.context = context;
        this.status = status;
        this.initialResponses = initialResponses;
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
        Status status;
        ArrayList<Response> initialResponses = new ArrayList<>();

        try {
            taskList = storage.load();
            initialResponses.add(
                    new Response(ui.showWelcome())
            );
            status = Status.OK;
        } catch (IOException e) {
            initialResponses.add(
                    new Response(ui.showLoadingError(e))
            );
            status = Status.FATAL;
        } catch (ProtoInvalidData e) {
            initialResponses.add(
                    new Response(ui.showProtoException(e))
            );
            status = Status.FATAL;
        }

        if (status == Status.FATAL) {
            initialResponses.add(
                    new Response(ui.showFatalExit())
            );
        }
        Context context = new Context(ui, storage, taskList);
        return new Proto(context, status, initialResponses);
    }

    public static Proto initialize() {
        return Proto.initialize(Proto.DEFAULT_PATH.toFile());
    }

    /**
     * Returns responses to user input.
     * Should not be called when Proto status is FATAL.
     *
     * @param input User string input.
     * @return List of responses.
     */
    public List<Response> getResponses(String input) {
        assert this.context.taskList != null;
        Command command = Parser.parseCommand(input);
        return command.execute(this.context);
    }
}
