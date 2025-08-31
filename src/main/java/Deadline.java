package main.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {
    private final String endTime;

    public Deadline(String description, String endTime) {
        super(description);
        this.endTime = endTime;
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }

    @Override
    public String getDisplayString() {
        return super.getDisplayString() +
                " (by: " + this.endTime + ")";
    }

    public String serialize() {
        return this.getTaskIcon() + " | " + (this.isDone ? "1" : "0") + " | " +
                Task.encodeString(this.description) + " | " +
                Task.encodeString(this.endTime);
    }

    private static final Pattern rx_deserialize = Pattern.compile(
            "^D \\| ([01]) \\| ((?:[^|]|\\\\\\|)*) \\| ((?:[^|]|\\\\\\|)*)$");


    public static Deadline deserialize(String data) throws ProtoInvalidData {
        Matcher match = rx_deserialize.matcher(data);

        if (match.find()) {
            Deadline deadline = new Deadline(Task.decodeString(match.group(2)), Task.decodeString(match.group(3)));
            deadline.isDone = match.group(1).equals("1");
            return deadline;
        } else {
            throw new ProtoInvalidData("Error parsing deadline data");
        }
    }
}
