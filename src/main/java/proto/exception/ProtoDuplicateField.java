package proto.exception;

public class ProtoDuplicateField extends ProtoException {
    public ProtoDuplicateField(String fieldName) {
        super("[Field \"" + fieldName + "\" is duplicated.]");
    }
}
