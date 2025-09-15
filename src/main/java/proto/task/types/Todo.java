package proto.task.types;

import proto.exception.ProtoInvalidData;
import proto.task.Task;

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

    private static final Pattern RX_DESERIALIZE = Pattern.compile("^T \\| ([01]) \\| ((?:[^|]|\\\\\\|)*)$");

    public static Todo deserialize(String data) throws ProtoInvalidData {
        Matcher match = RX_DESERIALIZE.matcher(data);

        if (match.find()) {
            Todo todo = new Todo(Task.decodeString(match.group(2)));
            todo.isDone = match.group(1).equals("1");
            return todo;
        } else {
            throw new ProtoInvalidData("Error parsing todo data");
        }
    }
}
