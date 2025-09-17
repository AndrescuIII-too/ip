package proto.command.types;

import java.util.List;

import proto.command.Command;
import proto.command.Context;
import proto.command.Response;

public class Help extends Command {
    @Override
    public List<Response> execute(Context context) {
        return List.of(
                new Response(context.ui.showHelp("""
                                [Here is a list of my commands.]

                                todo [description]
                                deadline [description] /by [YYYY-MM-DD]
                                event [description] /from [YYYY-MM-DD] /to [YYYY-MM-DD]
                                  - Adds a todo/deadline/event task to your list.
                                  
                                list
                                  - Shows the contents of your task list.
                                  
                                find [query]
                                  - Finds and shows tasks containing [query] in its description.
                                  
                                delete [index]
                                  - Deletes a task from your list.
                                  
                                clear
                                  - Deletes all tasks from your list.
                                  
                                mark [index]
                                  - Marks a task as done from your list.
                                  
                                unmark [index]
                                  - Unmarks a task from your list.
                                  
                                load [path to save file]
                                  - Loads and uses the task list specified by the path.
                                  
                                bye
                                  - Exits the program.
                                  
                                help
                                  - Shows this help text."""
                ))
        );
    }
}
