package proto.command.types;

import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;
import proto.exception.ProtoException;
import proto.exception.ProtoUnknownCommand;
import proto.ui.DialogBox;

public class Unknown extends Command {
    private final String name;

    public Unknown(String name) {
        this.name = name;
    }

    @Override
    public List<Response> execute(Context context) {
        ProtoException e = new ProtoUnknownCommand(this.name);
        DialogBox dialogBox = context.ui.showProtoException(e);
        return List.of(new Response(dialogBox));
    }
}
