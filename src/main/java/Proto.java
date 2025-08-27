import java.util.ArrayList;
import java.util.Scanner;

public class Proto {
    static String LINE_SEPARATOR = "-".repeat(40);
    static ArrayList<String> taskList = new ArrayList<>();

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

            switch (input) {
            case "bye":
                System.out.println("Bye. Hope to see you again soon!\n" + LINE_SEPARATOR);
                return;
            case "list":
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < taskList.size(); i++) {
                    sb.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
                }
                System.out.println(sb.append(LINE_SEPARATOR));
                break;
            default:
                taskList.add(input);
                System.out.println("added: " + input + "\n" + LINE_SEPARATOR);
            }
        }
    }
}
