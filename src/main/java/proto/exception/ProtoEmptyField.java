package proto.exception;

public class ProtoEmptyField extends ProtoException {
    public ProtoEmptyField(String fieldName) {
        super("Field \"" + fieldName + "\" cannot be empty");
    }
}
