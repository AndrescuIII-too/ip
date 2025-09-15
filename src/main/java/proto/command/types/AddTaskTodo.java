package proto.command.types;

import java.util.ArrayList;
import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;
import proto.task.Task;
import proto.task.types.Todo;

public class AddTaskTodo extends Command {
    private final String description;

    public AddTaskTodo(String description) {
        this.description = description;
    }

    @Override
    public List<Response> execute(Context context) {
        if (this.description.isEmpty()) {
            return List.of(
                    new Response(context.ui.showEmptyDescriptionError())
            );
        }

        Task task = new Todo(this.description);
        context.getTaskList().add(task);
        ArrayList<Response> responses = new ArrayList<>(List.of(
                new Response(context.ui.showTaskAdded(task, context.getTaskList()))
        ));

        responses.addAll(context.saveTaskList());
        return responses;
    }
}
