package proto.command;

import proto.command.types.AddTaskDeadline;
import proto.command.types.AddTaskEvent;
import proto.command.types.AddTaskTodo;
import proto.command.types.ClearTasks;
import proto.command.types.DeleteTask;
import proto.command.types.Exit;
import proto.command.types.FindTasks;
import proto.command.types.Help;
import proto.command.types.ListTasks;
import proto.command.types.LoadTasks;
import proto.command.types.MarkTask;
import proto.command.types.Unknown;
import proto.command.types.UnmarkTask;
import proto.exception.ProtoDuplicateField;
import proto.exception.ProtoEmptyField;
import proto.exception.ProtoException;
import proto.exception.ProtoInvalidArgument;
import proto.exception.ProtoMissingField;
import proto.exception.ProtoUnexpectedField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final Pattern RX_ARGUMENTS = Pattern.compile("^(\\S*)\\s*(.*?)\\s*(/.*)?$");
    private static final Pattern RX_PARAMETER = Pattern.compile("^/(\\S*)\\s*(.*?)\\s*(?=/|$)");
    private static final Pattern RX_NUMBER = Pattern.compile("^\\d+$");


    /**
     * Parse string into list of parameters.
     * Parameters take the form "/[name] [value]".
     *
     * @param input String to parse.
     * @return List of parameters.
     */
    public static List<Parameter> parseParameters(String input) {
        List<Parameter> parameters = new ArrayList<>();

        if (input == null) {
            return parameters;
        }

        while (true) {
            Matcher match = RX_PARAMETER.matcher(input);
            if (!match.find()) {
                throw new AssertionError(); // Should be unreachable
            }
            parameters.add(new Parameter(match.group(1), match.group(2)));
            input = input.substring(match.end());

            if (match.hitEnd()) {
                return parameters;
            }
        }
    }

    /**
     * Checks if expected fields are assigned once.
     *
     * @param parameters List of parameters.
     * @param fieldNames Expected fields.
     * @return Mapping of field names to values.
     * @throws ProtoException If field is not included in expected fields, or field is empty, duplicated, or missing
     */
    public static HashMap<String, String> validateFields(
            List<Parameter> parameters, HashSet<String> fieldNames) throws ProtoException {
        HashMap<String, String> fields = new HashMap<>();

        for (Parameter parameter : parameters) {
            if (!fieldNames.contains(parameter.name())) {
                throw new ProtoUnexpectedField(parameter.name());
            } else if (fields.containsKey(parameter.name())) {
                throw new ProtoDuplicateField(parameter.name());
            } else if (parameter.value().isEmpty()) {
                throw new ProtoEmptyField(parameter.name());
            } else {
                fields.put(parameter.name(), parameter.value());
            }
        }

        for (String fieldName : fieldNames) {
            if (!fields.containsKey(fieldName)) {
                throw new ProtoMissingField(fieldName);
            }
        }

        return fields;
    }

    /**
     * Parses command from input string.
     *
     * @param input String to be parsed.
     * @return Executable command.
     */
    public static Command parseCommand(String input) {
        Matcher match = RX_ARGUMENTS.matcher(input);
        if (!match.find()) {
            throw new AssertionError(); // Should be unreachable
        }
        return Parser.resolve(match.group(1), match.group(2), Parser.parseParameters(match.group(3)));
    }

    public static int parseNumber(String text) throws ProtoInvalidArgument {
        Matcher match = RX_NUMBER.matcher(text);
        if (match.find()) {
            return Integer.parseInt(match.group(0));
        } else {
            throw new ProtoInvalidArgument("expected numeric value");
        }
    }

    private static Command resolve(String name, String body, List<Parameter> parameters) {
        return switch (name) {
        case "bye" -> new Exit();
        case "mark" -> new MarkTask(body);
        case "unmark" -> new UnmarkTask(body);
        case "delete" -> new DeleteTask(body);
        case "todo" -> new AddTaskTodo(body);
        case "deadline" -> new AddTaskDeadline(body, parameters);
        case "event" -> new AddTaskEvent(body, parameters);
        case "list" -> new ListTasks();
        case "find" -> new FindTasks(body);
        case "clear" -> new ClearTasks();
        case "load" -> new LoadTasks(body);
        case "help" -> new Help();
        default -> new Unknown(name);
        };
    }
}
