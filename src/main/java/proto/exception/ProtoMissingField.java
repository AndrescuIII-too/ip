package main.java.proto.exception;

public class ProtoMissingField extends ProtoException{
    public ProtoMissingField(String fieldName) {
        super("Missing field \"" + fieldName + "\"");
    }
}
