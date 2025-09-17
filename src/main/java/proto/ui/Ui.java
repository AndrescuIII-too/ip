package proto.ui;

import proto.exception.ProtoException;
import proto.task.IndexedTask;
import proto.task.Task;
import proto.task.TaskList;

import java.io.IOException;
import java.util.List;

public class Ui {

    public DialogBox showWelcome() {
        return DialogBox.getProtoDialog(
                "[Hi.]\n" +
                "[What can I do for you today.]"
        );
    }

    public DialogBox showFatalExit() {
        return DialogBox.getProtoDialog(
                "[Press any button to exit.]"
        );
    }

    public DialogBox showTaskDone(Task task) {
        return DialogBox.getProtoDialog(
                "[I have marked this task from your list as done.]\n" +
                "\n" +
                " " + task.getDisplayString()
        );
    }

    public DialogBox showTaskUndone(Task task) {
        return DialogBox.getProtoDialog(
                "[I have unmarked this task from your list.]\n" +
                "\n" +
                " " + task.getDisplayString()
        );
    }

    public DialogBox showTaskRemoved(Task task) {
        return DialogBox.getProtoDialog(
                "[I have removed this task from your list.]\n" +
                "\n" +
                " " + task.getDisplayString()
        );
    }

    public DialogBox showLoadingError(IOException e) {
        return DialogBox.getProtoDialog(
                "[There was an error when loading your save data: " + e.getMessage() + " ]",
                DialogBox.Type.ERROR
        );
    }

    public DialogBox showLoadingSuccess() {
        return DialogBox.getProtoDialog(
                "[Your tasks were loaded successfully.]"
        );
    }

    public DialogBox showSavingError(IOException e) {
        return DialogBox.getProtoDialog(
                "[There was an error when saving your data: " + e.getMessage() + " ]",
                DialogBox.Type.ERROR
        );
    }

    public DialogBox showIndexError(int index) {
        return DialogBox.getProtoDialog(
                "[You do not have a task with index " + index + "in your list.]",
                DialogBox.Type.ERROR
        );
    }

    public DialogBox showDateParseError(String text) {
        return DialogBox.getProtoDialog(
                "[I had trouble understanding this date: " + text + " ]",
                DialogBox.Type.ERROR
        );
    }

    public DialogBox showProtoException(ProtoException e) {
        return DialogBox.getProtoDialog(
                e.getMessage(),
                DialogBox.Type.ERROR
        );
    }

    public DialogBox showEmptyDescriptionError() {
        return DialogBox.getProtoDialog(
                "[I can't add a task without a description.]",
                DialogBox.Type.ERROR
        );
    }

    public DialogBox showTaskAdded(Task task, TaskList taskList) {
        return DialogBox.getProtoDialog(
                "[I have added this task to your list.]\n" +
                "\n" +
                " " + task.getDisplayString() + "\n" +
                "\n" +
                "[Now you have " + taskList.size() + " tasks in the list.]"
        );
    }

    public DialogBox showTaskListEmpty() {
        return DialogBox.getProtoDialog(
                "[There are no tasks in your list.]"
        );
    }

    public DialogBox showFindNothing(String text) {
        return DialogBox.getProtoDialog(
                "[I didn't find any tasks with the description containing \"" + text + "\".]"
        );
    }

    public DialogBox showTaskList(TaskList taskList) {
        return DialogBox.getProtoDialog(
                "[Here are the tasks in your list.]\n" +
                "\n" +
                taskList.getDisplayString(),
                DialogBox.Type.LIST
        );
    }
    public DialogBox showFindResults(List<IndexedTask> indexedTasks) {
        return DialogBox.getProtoDialog(
                "[Here are the matching tasks in your list.]\n" +
                "\n" +
                TaskList.getDisplayString(indexedTasks),
                DialogBox.Type.LIST
        );
    }

    public DialogBox showTaskListCleared() {
        return DialogBox.getProtoDialog(
                "[I have cleared your task list.]"
        );
    }

    public DialogBox showHelp(String helpString) {
        return DialogBox.getProtoDialog(helpString);
    }
}
