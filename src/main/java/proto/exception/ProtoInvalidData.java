package proto.exception;

public class ProtoInvalidData extends ProtoException {
    private int errorIndex = -1;

    public ProtoInvalidData(String description) {
        super(description);
    }

    @Override
    public String getMessage() {
        return "[Line #" + this.errorIndex + ": " + super.getMessage() + " ]";
    }

    public void setErrorIndex(int errorIndex) {
        this.errorIndex = errorIndex;
    }
}
