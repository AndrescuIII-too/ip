package proto.command;

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
        } else {
            while (true) {
                Matcher match = RX_PARAMETER.matcher(input);
                if (!match.find()) {
                    return parameters; // Should be unreachable
                }
                parameters.add(new Parameter(match.group(1), match.group(2)));
                input = input.substring(match.end());

                if (match.hitEnd()) {
                    return parameters;
                }
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

    public static Command parseCommand(String input) {
        Matcher match = RX_ARGUMENTS.matcher(input);
        match.find();
        return new Command(match.group(1), match.group(2), Parser.parseParameters(match.group(3)));
    }

    public static int parseNumber(String text) throws ProtoInvalidArgument {
        Matcher match = RX_NUMBER.matcher(text);
        if (match.find()) {
            return Integer.parseInt(match.group(0));
        } else {
            throw new ProtoInvalidArgument("expected numeric value");
        }
    }
}
