package proto.exception;

public class ProtoUnexpectedField extends ProtoException {
    public ProtoUnexpectedField(String fieldName) {
        super("[Field \"" + fieldName + "\" unexpected.]");
    }
}
