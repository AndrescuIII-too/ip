package proto.exception;

public class ProtoInvalidData extends ProtoException {
    private int errorIndex = -1;

    public ProtoInvalidData(String description) {
        super(description);
    }

    @Override
    public String getMessage() {
        if (errorIndex != -1) {
            return "[Line #" + this.errorIndex + ": " + super.getMessage() + " ]";
        } else {
            return "[Error: " + super.getMessage() + " ]";
        }
    }

    public void setErrorIndex(int errorIndex) {
        this.errorIndex = errorIndex;
    }
}
