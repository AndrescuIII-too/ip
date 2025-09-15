package proto.command.types;

import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;
import proto.task.TaskList;

public class ListTasks extends Command {
    @Override
    public List<Response> execute(Context context) {
        TaskList taskList = context.getTaskList();
        if (taskList.isEmpty()) {
            return List.of(
                    new Response(context.ui.showTaskListEmpty())
            );
        } else {
            return List.of(
                    new Response(context.ui.showTaskList(taskList))
            );
        }
    }
}
