package proto.task;

import proto.exception.ProtoInvalidData;

import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected final String description;
    protected boolean isDone = false;
    protected static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public abstract String getTaskIcon();

    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    public String getDisplayString() {
        return "[" + this.getTaskIcon() + "]" +
                "[" + this.getStatusIcon() + "] " + this.description;
    }

    public abstract String serialize();

    public static Task deserialize(String data) throws ProtoInvalidData {
        char type;
        switch (type = data.charAt(0)) {
        case 'T' -> {
            return Todo.deserialize(data);
        }
        case 'D' -> {
            return Deadline.deserialize(data);
        }
        case 'E' -> {
            return Event.deserialize(data);
        }
        default -> {
            throw new ProtoInvalidData("Unknown task type \"" + type + "\"");
        }
        }
    }

    protected static String encodeString(String input) {
        return input.replaceAll("\\|", "\\\\|");
    }

    protected static String decodeString(String input) {
        return input.replaceAll("\\\\\\|", "|");
    }
}
