package proto.command.types;

import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;

public class ListTasks extends Command {
    @Override
    public List<Response> execute(Context context) {
        if (context.taskList.isEmpty()) {
            return List.of(
                    new Response(context.ui.showTaskListEmpty())
            );
        } else {
            return List.of(
                    new Response(context.ui.showTaskList(context.taskList.getTasks()))
            );
        }
    }
}
