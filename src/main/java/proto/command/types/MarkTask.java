package proto.command.types;

import java.util.ArrayList;
import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Parser;
import proto.command.Response;
import proto.exception.ProtoException;
import proto.task.Task;

public class MarkTask extends Command {
    private final String stringIndex;

    public MarkTask(String index) {
        this.stringIndex = index;
    }

    @Override
    public List<Response> execute(Context context) {
        int index;
        try {
            index = Parser.parseNumber(this.stringIndex);
        } catch (ProtoException e) {
            return List.of(
                    new Response(context.ui.showProtoException(e))
            );
        }

        List<Response> responses = new ArrayList<>();
        try {
            Task task = context.taskList.get(index - 1);
            task.markAsDone();
            responses.add(
                    new Response(context.ui.showTaskDone(task))
            );
        } catch (IndexOutOfBoundsException e) {
            return List.of(
                    new Response(context.ui.showIndexError(index))
            );
        }

        responses.addAll(context.saveTaskList());
        return responses;
    }
}
