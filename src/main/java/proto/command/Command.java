package proto.command;

import java.util.List;

public abstract class Command {
    public abstract List<Response> execute(Context context);
}
