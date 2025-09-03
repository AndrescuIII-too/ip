package main.java.proto.task;

import main.java.proto.exception.ProtoInvalidData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getTaskIcon() {
        return "T";
    }

    public String serialize() {
        return this.getTaskIcon() + " | " + (this.isDone ? "1" : "0") + " | " +
                Task.encodeString(this.description);
    }

    private static final Pattern rx_deserialize = Pattern.compile("^T \\| ([01]) \\| ((?:[^|]|\\\\\\|)*)$");

    public static Todo deserialize(String data) throws ProtoInvalidData {
        Matcher match = rx_deserialize.matcher(data);

        if (match.find()) {
            Todo todo = new Todo(Task.decodeString(match.group(2)));
            todo.isDone = match.group(1).equals("1");
            return todo;
        } else {
            throw new ProtoInvalidData("Error parsing todo data");
        }
    }
}
