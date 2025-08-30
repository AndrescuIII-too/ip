package main.java;

public class ProtoUnknownCommand extends ProtoException{
    public ProtoUnknownCommand(String command) {
        super("Unknown command \"" + command + "\"");
    }
}
