package main.java;

public class ProtoUnexpectedField extends ProtoException {
    public ProtoUnexpectedField(String fieldName) {
        super("Unexpected field \"" + fieldName + "\"");
    }
}
