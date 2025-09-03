package main.java.proto.exception;

public class ProtoUnknownCommand extends ProtoException{
    public ProtoUnknownCommand(String command) {
        super("Unknown command \"" + command + "\"");
    }
}
