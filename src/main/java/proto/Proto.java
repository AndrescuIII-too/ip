package proto;

import proto.command.Command;
import proto.command.Context;
import proto.command.Parser;
import proto.command.Response;
import proto.exception.ProtoInvalidData;
import proto.task.TaskList;
import proto.ui.Ui;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Proto {
    protected static final Path DATA_PATH = Paths.get(".", "data");
    private final Context context;
    private final Config config;
    private Status status;
    public final List<Response> initialResponses;

    private Proto(Context context, Config config, Status status, List<Response> initialResponses) {
        this.context = context;
        this.config = config;
        this.status = status;
        this.initialResponses = initialResponses;
    }

    private static Proto fatalInit(Ui ui, List<Response> initialResponses) {
        initialResponses.add(
                new Response(ui.showFatalExit())
        );
        Context context = new Context(ui, null, null);
        return new Proto(context, null, Status.FATAL, initialResponses);
    }

    public Status getStatus() {
        return this.status;
    }

    public enum Status {
        OK,
        FATAL,
    }

    public static Proto initialize() {
        Ui ui = new Ui();
        ArrayList<Response> initialResponses = new ArrayList<>();

        try {
            Config config = Config.initialize();
            Storage storage = new Storage(config);
            TaskList taskList = storage.loadFrom(config.getSavePath(), true);

            initialResponses.add(
                    new Response(ui.showWelcome())
            );

            Context context = new Context(ui, storage, taskList);
            return new Proto(context, config, Status.OK, initialResponses);
        } catch (IOException e) {
            initialResponses.add(
                    new Response(ui.showLoadingError(e))
            );
            return Proto.fatalInit(ui, initialResponses);
        } catch (ProtoInvalidData e) {
            initialResponses.add(
                    new Response(ui.showProtoException(e))
            );
            return Proto.fatalInit(ui, initialResponses);
        }
    }

    /**
     * Returns responses to user input.
     * Should not be called when Proto status is FATAL.
     *
     * @param input User string input.
     * @return List of responses.
     */
    public List<Response> getResponses(String input) {
        assert this.context.getTaskList() != null;
        Command command = Parser.parseCommand(input);
        return command.execute(this.context);
    }
}
