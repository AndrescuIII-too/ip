package proto.exception;

public class ProtoUnknownCommand extends ProtoException {
    public ProtoUnknownCommand(String command) {
        super("[I don't know the command \"" + command + "\".]");
    }
}
