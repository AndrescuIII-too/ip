package main.java;

public class ProtoMissingField extends ProtoException{
    public ProtoMissingField(String fieldName) {
        super("Missing field \"" + fieldName + "\"");
    }
}
