package main.java;

public class ProtoInvalidArgument extends ProtoException {
    public ProtoInvalidArgument(String errorDescription) {
        super("Invalid argument: " + errorDescription);
    }
}
