package main.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task{
    private final String startTime;
    private final String endTime;

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getTaskIcon() {
        return "E";
    }

    @Override
    public String getDisplayString() {
        return super.getDisplayString() +
                " (from: " + this.startTime + " to: " + this.endTime + ")";
    }

    public String serialize() {
        return this.getTaskIcon() + " | " + (this.isDone ? "1" : "0") + " | " +
                Task.encodeString(this.description) + " | " +
                Task.encodeString(this.startTime) + " | " +
                Task.encodeString(this.endTime);
    }

    private static final Pattern rx_deserialize = Pattern.compile(
            "^E \\| ([01]) \\| ((?:[^|]|\\\\\\|)*) \\| ((?:[^|]|\\\\\\|)*) \\| ((?:[^|]|\\\\\\|)*)$");


    public static Event deserialize(String data) throws ProtoInvalidData {
        Matcher match = rx_deserialize.matcher(data);

        if (match.find()) {
            Event event = new Event(
                    Task.decodeString(match.group(2)),
                    Task.decodeString(match.group(3)),
                    Task.decodeString(match.group(4)));
            event.isDone = match.group(1).equals("1");
            return event;
        } else {
            throw new ProtoInvalidData("Error parsing event data");
        }
    }
}
