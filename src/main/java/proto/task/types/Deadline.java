package proto.task.types;

import proto.exception.ProtoInvalidData;
import proto.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {
    private final LocalDate endTime;

    public Deadline(String description, LocalDate endTime) {
        super(description);
        this.endTime = endTime;
    }

    public Deadline(String description, String endTime) throws DateTimeParseException {
        super(description);
        this.endTime = LocalDate.parse(endTime);
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }

    @Override
    public String getDisplayString() {
        return super.getDisplayString() +
                " (by: " + this.getEndTimeString() + ")";
    }

    public String getEndTimeString() {
        return this.endTime.format(Task.dateTimeFormatter);
    }

    public String serialize() {
        return this.getTaskIcon() + " | " + (this.isDone ? "1" : "0") + " | " +
                Task.encodeString(this.description) + " | " +
                this.getEndTimeString();
    }

    private static final Pattern RX_DESERIALIZE = Pattern.compile(
            "^D \\| ([01]) \\| ((?:[^|]|\\\\\\|)*) \\| ((?:[^|]|\\\\\\|)*)$");


    public static Deadline deserialize(String data) throws ProtoInvalidData, DateTimeParseException {
        Matcher match = RX_DESERIALIZE.matcher(data);

        if (match.find()) {
            Deadline deadline = new Deadline(
                    Task.decodeString(match.group(2)),
                    LocalDate.parse(match.group(3), Task.dateTimeFormatter));
            deadline.isDone = match.group(1).equals("1");
            return deadline;
        } else {
            throw new ProtoInvalidData("Error parsing deadline data");
        }
    }
}
