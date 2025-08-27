import java.util.Objects;
import java.util.Scanner;

public class Proto {
    static String LINE_SEPARATOR = "-".repeat(40);

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
                System.out.println("Bye. Hope to see you again soon!\n" + LINE_SEPARATOR);
                break;
            } else {
                System.out.println(input + "\n" + LINE_SEPARATOR);
            }
        }
    }
}
