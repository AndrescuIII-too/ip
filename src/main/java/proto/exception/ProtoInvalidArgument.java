package main.java.proto.exception;

public class ProtoInvalidArgument extends ProtoException {
    public ProtoInvalidArgument(String errorDescription) {
        super("Invalid argument: " + errorDescription);
    }
}
