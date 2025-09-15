package proto.command.types;

import java.util.ArrayList;
import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;

public class ClearTasks extends Command {
    @Override
    public List<Response> execute(Context context) {
        context.getTaskList().clear();

        ArrayList<Response> responses = new ArrayList<>(List.of(
                new Response(context.ui.showTaskListCleared())
        ));

        responses.addAll(context.saveTaskList());
        return responses;
    }
}
