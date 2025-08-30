package main.java;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parameter {
    public String name;
    public String value;

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

public class Proto {
    static String LINE_SEPARATOR = "-".repeat(40);
    static ArrayList<Task> tasks = new ArrayList<>();

    public static String tasksToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            sb.append(i + 1)
                    .append(".")
                    .append(task.getDisplayString())
                    .append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static void addTask(Task task) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:\n" +
                " " + task.getDisplayString() + "\n" +
                "Now you have " + tasks.size() + " tasks in the list.");
    }

    private static final Pattern rx_arguments = Pattern.compile("^(\\S*)\\s*(.*?)\\s*(/.*)?$");
    private static final Pattern rx_parameter = Pattern.compile("^/(\\S*)\\s*(.*?)(?=/|$)");
    private static final Pattern rx_number = Pattern.compile("^\\d+$");


    public static ArrayList<Parameter> parseParameters(String input) {
        ArrayList<Parameter> parameters = new ArrayList<>();

        if (input == null) {
            return parameters;
        } else {
            while (true) {
                Matcher match = rx_parameter.matcher(input);
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

    public static HashMap<String, String> validateFields(
            ArrayList<Parameter> parameters, HashSet<String> fieldNames) throws ProtoException {
        HashMap<String, String> fields = new HashMap<>();

        for (Parameter parameter : parameters) {
            if (!fieldNames.contains(parameter.name)) {
                throw new ProtoUnexpectedField(parameter.name);
            } else if (fields.containsKey(parameter.name)) {
                throw new ProtoDuplicateField(parameter.name);
            } else if (parameter.value.isEmpty()) {
                throw new ProtoEmptyField(parameter.name);
            } else {
                fields.put(parameter.name, parameter.value);
            }
        }

        for (String fieldName: fieldNames) {
            if (!fields.containsKey(fieldName)) {
                throw new ProtoMissingField(fieldName);
            }
        }

        return fields;
    }

    public static void processCommand(String input) throws ProtoException {
        Matcher match = rx_arguments.matcher(input);
        if (!match.find()) {
            return; // Should be unreachable
        }
        String command = match.group(1);
        String text = match.group(2);
        ArrayList<Parameter> parameters = parseParameters(match.group(3));

        switch (command) {
            case "mark" -> {
                match = rx_number.matcher(text);
                if (match.find()) {
                    Task task = tasks.get(Integer.parseInt(match.group(0)) - 1);
                    task.markAsDone();
                    System.out.println("Nice! I've marked this task as done:\n" +
                            " " + task.getDisplayString());
                } else {
                    throw new ProtoInvalidArgument("expected numeric value");
                }
            }
            case "unmark" -> {
                match = rx_number.matcher(text);
                if (match.find()) {
                    Task task = tasks.get(Integer.parseInt(match.group(0)) - 1);
                    task.markUndone();
                    System.out.println("OK, I've marked this task as not done yet:\n" +
                            " " + task.getDisplayString());
                } else {
                    throw new ProtoInvalidArgument("expected numeric value");
                }
            }
            case "delete" -> {
                match = rx_number.matcher(text);
                if (match.find()) {
                    Task task = tasks.remove(Integer.parseInt(match.group(0)) - 1);
                    System.out.println("Noted! I've removed this task:\n" +
                            " " + task.getDisplayString());
                } else {
                    throw new ProtoInvalidArgument("expected numeric value");
                }
            }
            case "todo" -> {
                if (text.isEmpty()) {
                    throw new ProtoEmptyDescription();
                } else {
                    addTask(new Todo(text));
                }
            }
            case "deadline" -> {
                if (text.isEmpty()) {
                    throw new ProtoEmptyDescription();
                } else {
                    HashMap<String, String> fields = validateFields(parameters, new HashSet<>(List.of("by")));
                    addTask(new Deadline(text, fields.get("by")));
                }
            }
            case "event" -> {
                if (text.isEmpty()) {
                    throw new ProtoEmptyDescription();
                } else {
                    HashMap<String, String> fields = validateFields(parameters, new HashSet<>(Arrays.asList("from", "to")));
                    addTask(new Event(text, fields.get("from"), fields.get("to")));
                }
            }
            case "list" -> {
                System.out.println("Here are the tasks in your list:\n" + tasksToString());
            }
            default -> throw new ProtoUnknownCommand(command);
        }
    }

    public static void main(String[] args) {
        System.out.println(LINE_SEPARATOR + "\n" +
                "Hello! I'm Proto\n" +
                "What can I do for you?\n" +
                LINE_SEPARATOR
        );

        Scanner reader = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = reader.nextLine();
            System.out.println(LINE_SEPARATOR);

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!\n" +
                        LINE_SEPARATOR);
                return;
            } else {
                try {
                    processCommand(input);
                } catch (ProtoException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println(LINE_SEPARATOR);
            }
        }
    }
}
