package proto.command.types;

import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;
import proto.task.Task;

public class FindTasks extends Command {
    private final String query;
    public FindTasks(String query) {
        this.query = query;
    }

    @Override
    public List<Response> execute(Context context) {
        List<Task> matchedTasks = context.taskList.find(this.query);

        if (matchedTasks.isEmpty()) {
            return List.of(
                    new Response(context.ui.showFindNothing(this.query))
            );
        } else {
            return List.of(
                    new Response(context.ui.showFindResults(matchedTasks))
            );
        }
    }
}
