package main.java;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.Task;

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
                "Now you have " + tasks.size() + " tasks in the list.\n" +
                LINE_SEPARATOR);
    }

    public static void main(String[] args) {
        System.out.println(LINE_SEPARATOR + "\n" +
                "Hello! I'm Proto\n" +
                "What can I do for you?\n" +
                LINE_SEPARATOR
        );

        Scanner reader = new Scanner(System.in);

        Pattern rx_mark = Pattern.compile("^mark\\s+(\\d+)$");
        Pattern rx_unmark = Pattern.compile("^unmark\\s+(\\d+)$");
        Pattern rx_todo = Pattern.compile("^todo\\s+(.+)$");
        Pattern rx_deadline = Pattern.compile("^deadline\\s+(.+?)\\s+/by\\s+(.+)$");
        Pattern rx_event = Pattern.compile("^event\\s+(.+)\\s+/from\\s+(.+?)\\s+/to\\s+(.+)$");

        while (true) {
            System.out.print("> ");
            String input = reader.nextLine();
            System.out.println(LINE_SEPARATOR);

            switch (input) {
                case "bye" -> {
                    System.out.println("Bye. Hope to see you again soon!\n" +
                            LINE_SEPARATOR);
                    return;
                }
                case "list" -> System.out.println("Here are the tasks in your list:\n" +
                        tasksToString() + "\n" +
                        LINE_SEPARATOR);
                default -> {
                    Matcher match = rx_mark.matcher(input);
                    if (match.find()) {
                        Task task = tasks.get(Integer.parseInt(match.group(1)) - 1);
                        task.markAsDone();
                        System.out.println("Nice! I've marked this task as done:\n" +
                                " " + task.getDisplayString() + "\n" +
                                LINE_SEPARATOR);
                        continue;
                    }

                    match = rx_unmark.matcher(input);
                    if (match.find()) {
                        Task task = tasks.get(Integer.parseInt(match.group(1)) - 1);
                        task.markUndone();
                        System.out.println("OK, I've marked this task as not done yet:\n" +
                                " " + task.getDisplayString() + "\n" +
                                LINE_SEPARATOR);
                        continue;
                    }

                    match = rx_todo.matcher(input);
                    if (match.find()) {
                        addTask(new Todo(match.group(1)));
                    }

                    match = rx_deadline.matcher(input);
                    if (match.find()) {
                        addTask(new Deadline(match.group(1), match.group(2)));
                    }

                    match = rx_event.matcher(input);
                    if (match.find()) {
                        addTask(new Event(match.group(1), match.group(2), match.group(3)));
                    }
                }
            }
        }
    }
}
