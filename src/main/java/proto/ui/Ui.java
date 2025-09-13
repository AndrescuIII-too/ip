package proto.ui;

import proto.exception.ProtoException;
import proto.task.Task;
import proto.task.TaskList;

import java.io.IOException;
import java.util.List;

public class Ui {

//    public DialogBox showWelcome() {
//        System.out.println("Hello! I'm Proto\n" +
//                "What can I do for you?"
//        );
//    }

    public DialogBox showTaskDone(Task task) {
        return DialogBox.getProtoDialog(
                "Nice! I've marked this task as done:\n" +
                " " + task.getDisplayString()
        );
    }

    public DialogBox showTaskUndone(Task task) {
        return DialogBox.getProtoDialog(
                "OK, I've marked this task as not done yet:\n" +
                " " + task.getDisplayString()
        );
    }

    public DialogBox showTaskRemoved(Task task) {
        return DialogBox.getProtoDialog(
                "Noted! I've removed this task:\n" +
                " " + task.getDisplayString()
        );
    }

    public DialogBox showLoadingError(IOException e) {
        return DialogBox.getProtoDialog(
                "There was an error loading the file: " + e.getMessage()
        );
    }

    public DialogBox showSavingError(IOException e) {
        return DialogBox.getProtoDialog(
                "There was an error saving the file: " + e.getMessage()
        );
    }

    public DialogBox showIndexError(int index) {
        return DialogBox.getProtoDialog(
                "Task index " + index + " out of bounds"
        );
    }

    public DialogBox showDateParseError(String text) {
        return DialogBox.getProtoDialog(
                "Trouble parsing date argument: " + text
        );
    }

    public DialogBox showProtoException(ProtoException e) {
        return DialogBox.getProtoDialog(
                e.getMessage()
        );
    }

    public DialogBox showEmptyDescriptionError() {
        return DialogBox.getProtoDialog(
                "Description cannot be empty"
        );
    }

    public DialogBox showTaskAdded(Task task, TaskList taskList) {
        return DialogBox.getProtoDialog(
                "Got it. I've added this task:\n" +
                " " + task.getDisplayString() + "\n" +
                "Now you have " + taskList.size() + " tasks in the list."
        );
    }

    public DialogBox showTaskListEmpty() {
        return DialogBox.getProtoDialog(
                "You don't have any tasks in your list."
        );
    }

    public DialogBox showFindNothing(String text) {
        return DialogBox.getProtoDialog(
                "No tasks with description \"" + text + "\" found"
        );
    }

    public DialogBox showTaskList(List<Task> tasks) {
        return DialogBox.getProtoDialog(
                "Here are the tasks in your list:\n" +
                    TaskList.getDisplayString(tasks)
        );
    }
    public DialogBox showFindResults(List<Task> tasks) {
        return DialogBox.getProtoDialog(
                "Here are the matching tasks in your list:\n" +
                    TaskList.getDisplayString(tasks)
        );
    }

    public DialogBox showTaskListCleared() {
        return DialogBox.getProtoDialog(
                "Cleared all tasks from your list!"
        );
    }
}
