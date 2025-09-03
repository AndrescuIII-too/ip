package main.java.proto.exception;

public class ProtoDuplicateField extends ProtoException {
    public ProtoDuplicateField(String fieldName) {
        super("Duplicate field \"" + fieldName + "\"");
    }
}
