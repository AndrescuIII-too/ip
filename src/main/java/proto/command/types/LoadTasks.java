package proto.command.types;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;
import proto.exception.ProtoException;

public class LoadTasks extends Command {
    private final String pathString;

    public LoadTasks(String pathString) {
        this.pathString = pathString;
    }

    @Override
    public List<Response> execute(Context context) {
        try {
            context.loadFrom(Path.of(this.pathString), false);
            return List.of(
                    new Response(context.ui.showLoadingSuccess())
            );
        } catch (IOException e) {
            return List.of(
                    new Response(context.ui.showLoadingError(e))
            );
        } catch (ProtoException e) {
            return List.of(
                    new Response(context.ui.showProtoException(e))
            );
        }
    }
}
