package proto.command;

import java.util.List;

public abstract class Command {
    /**
     * Executes command and generates responses.
     *
     * @param context Data needed by the command.
     * @return List of responses.
     */
    public abstract List<Response> execute(Context context);
}
