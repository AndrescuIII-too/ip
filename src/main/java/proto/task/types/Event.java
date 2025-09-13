package proto.task.types;

import proto.exception.ProtoInvalidData;
import proto.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task {
    private final LocalDate startTime;
    private final LocalDate endTime;

    public Event(String description, LocalDate startTime, LocalDate endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = LocalDate.parse(startTime);
        this.endTime = LocalDate.parse(endTime);
    }

    @Override
    public String getTaskIcon() {
        return "E";
    }

    @Override
    public String getDisplayString() {
        return super.getDisplayString() +
                " (from: " + this.getStartTimeString() + " to: " + this.getEndTimeString() + ")";
    }

    public String getStartTimeString() {
        return this.startTime.format(Task.dateTimeFormatter);
    }

    public String getEndTimeString() {
        return this.endTime.format(Task.dateTimeFormatter);
    }

    public String serialize() {
        return this.getTaskIcon() + " | " + (this.isDone ? "1" : "0") + " | " +
                Task.encodeString(this.description) + " | " +
                this.getStartTimeString() + " | " +
                this.getEndTimeString();
    }

    private static final Pattern RX_DESERIALIZE = Pattern.compile(
            "^E \\| ([01]) \\| ((?:[^|]|\\\\\\|)*) \\| ((?:[^|]|\\\\\\|)*) \\| ((?:[^|]|\\\\\\|)*)$");


    public static Event deserialize(String data) throws ProtoInvalidData, DateTimeParseException {
        Matcher match = RX_DESERIALIZE.matcher(data);

        if (match.find()) {
            Event event = new Event(
                    Task.decodeString(match.group(2)),
                    LocalDate.parse(match.group(3), Task.dateTimeFormatter),
                    LocalDate.parse(match.group(4), Task.dateTimeFormatter));
            event.isDone = match.group(1).equals("1");
            return event;
        } else {
            throw new ProtoInvalidData("Error parsing event data");
        }
    }
}
