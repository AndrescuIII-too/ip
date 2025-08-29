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

    public static void main(String[] args) {
        System.out.println(LINE_SEPARATOR + "\n" +
                "Hello! I'm Proto\n" +
                "What can I do for you?\n" +
                LINE_SEPARATOR
        );

        Scanner reader = new Scanner(System.in);

        Pattern mark_r = Pattern.compile("^mark (\\d+)$");
        Pattern unmark_r = Pattern.compile("^unmark (\\d+)$");

        while (true) {
            System.out.print("> ");
            String input = reader.nextLine();
            System.out.println(LINE_SEPARATOR);

            switch (input) {
                case "bye" -> {
                    System.out.println("Bye. Hope to see you again soon!\n" + LINE_SEPARATOR);
                    return;
                }
                case "list" -> System.out.println("Here are the tasks in your list:\n" +
                        tasksToString() +
                        "\n" + LINE_SEPARATOR);
                default -> {
                    Matcher mark_m = mark_r.matcher(input);
                    if (mark_m.find()) {
                        Task task = tasks.get(Integer.parseInt(mark_m.group(1)) - 1);
                        task.markAsDone();
                        System.out.println("Nice! I've marked this task as done:\n " + task.getDisplayString() +
                                "\n" + LINE_SEPARATOR);
                        continue;
                    }

                    Matcher unmark_m = unmark_r.matcher(input);
                    if (unmark_m.find()) {
                        Task task = tasks.get(Integer.parseInt(unmark_m.group(1)) - 1);
                        task.markUndone();
                        System.out.println("OK, I've marked this task as not done yet:\n " + task.getDisplayString() +
                                "\n" + LINE_SEPARATOR);
                        continue;
                    }

                    tasks.add(new Task(input));
                    System.out.println("added: " + input + "\n" + LINE_SEPARATOR);
                }
            }
        }
    }
}
