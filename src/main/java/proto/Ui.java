package main.java.proto;

import main.java.proto.exception.ProtoException;
import main.java.proto.task.Task;
import main.java.proto.task.TaskList;

import java.io.IOException;
import java.util.Scanner;

public class Ui {
    private static final String LINE_SEPARATOR = "-".repeat(40);

    private final Scanner reader = new Scanner(System.in);

    public String takeInput() {
        System.out.print("> ");
        String input = reader.nextLine();
        this.showDivider();
        return input;
    }

    public void showDivider() {
        System.out.println(LINE_SEPARATOR);
    }

    public void showWelcome() {
        this.showDivider();
        System.out.println("Hello! I'm Proto\n" +
                "What can I do for you?"
        );
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showTaskDone(Task task) {
        System.out.println("Nice! I've marked this task as done:\n" +
                " " + task.getDisplayString());
    }

    public void showTaskUndone(Task task) {
        System.out.println("OK, I've marked this task as not done yet:\n" +
                " " + task.getDisplayString());
    }

    public void showTaskRemoved(Task task) {
        System.out.println("Noted! I've removed this task:\n" +
                " " + task.getDisplayString());
    }

    public void showLoadingError(IOException e) {
        System.out.println("There was an error loading the file: " + e.getMessage());
    }

    public void showSavingError(IOException e) {
        System.out.println("There was an error saving the file: " + e.getMessage());
    }

    public void showIndexError(int index) {
        System.out.println("Task index " + index + " out of bounds");
    }

    public void showDateParseError(String text) {
        System.out.println("Trouble parsing date argument: " + text);
    }

    public void showProtoException(ProtoException e) {
        System.out.println(e.getMessage());
    }

    public void showEmptyDescriptionError() {
        System.out.println("Description cannot be empty");
    }

    public void showTaskAdded(Task task, TaskList taskList) {
        System.out.println("Got it. I've added this task:\n" +
                " " + task.getDisplayString() + "\n" +
                "Now you have " + taskList.size() + " tasks in the list.");
    }

    public void showTaskListEmpty() {
        System.out.println("You don't have any tasks in your list.");
    }

    public void showTaskList(TaskList taskList) {
        System.out.println("Here are the tasks in your list:\n" + taskList.display());
    }

    public void showTaskListCleared() {
        System.out.println("Cleared all tasks from your list!");
    }
}
