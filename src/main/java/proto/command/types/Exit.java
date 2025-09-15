package proto.command.types;

import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;

public class Exit extends Command {
    @Override
    public List<Response> execute(Context context) {
        return List.of(Response.exit());
    }
}
