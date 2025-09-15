package proto.exception;

public class ProtoMissingField extends ProtoException {
    public ProtoMissingField(String fieldName) {
        super("[Field \"" + fieldName + "\" is missing.]");
    }
}
