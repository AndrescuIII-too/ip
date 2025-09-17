package proto.command.types;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Parameter;
import proto.command.Parser;
import proto.command.Response;
import proto.exception.ProtoException;
import proto.task.Task;
import proto.task.types.Event;

public class AddTaskEvent extends Command {
    private final String description;
    private final List<Parameter> parameters;
    private static final HashSet<String> FIELDS = new HashSet<>(List.of("from", "to"));

    public AddTaskEvent(String description, List<Parameter> parameters) {
        this.description = description;
        this.parameters = parameters;
    }

    @Override
    public List<Response> execute(Context context) {
        if (this.description.isEmpty()) {
            return List.of(
                    new Response(context.ui.showEmptyDescriptionError())
            );
        }

        HashMap<String, String> fields;
        try {
            fields = Parser.validateFields(this.parameters, AddTaskEvent.FIELDS);
        } catch (ProtoException e) {
            return List.of(
                    new Response(context.ui.showProtoException(e))
            );
        }

        assert fields.containsKey("from");
        assert fields.containsKey("to");
        Task task;
        try {
            task = new Event(this.description, fields.get("from"), fields.get("to"));
        } catch (DateTimeParseException e) {
            return List.of(
                    new Response(context.ui.showDateParseError(e.getParsedString()))
            );
        }

        context.getTaskList().add(task);
        ArrayList<Response> responses = new ArrayList<>(List.of(
                new Response(context.ui.showTaskAdded(task, context.getTaskList()))
        ));

        responses.addAll(context.saveTaskList());
        return responses;
    }
}
