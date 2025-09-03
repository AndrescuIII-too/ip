package proto;

import proto.command.Command;
import proto.command.Parser;
import proto.exception.ProtoException;
import proto.exception.ProtoInvalidData;
import proto.exception.ProtoUnknownCommand;
import proto.task.Deadline;
import proto.task.Event;
import proto.task.Task;
import proto.task.TaskList;
import proto.task.Todo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

public class Proto {
    private final Ui ui;
    private final Storage storage;
    private TaskList taskList;

    public Proto(File file) {
        this.ui = new Ui();
        this.storage = new Storage(file);

        try {
            this.taskList = storage.load();
        } catch (IOException e) {
            this.ui.showLoadingError(e);
        } catch (ProtoInvalidData e) {
            this.ui.showProtoException(e);
        }
    }

    public void run() {
        this.ui.showWelcome();

        while (true) {
            this.ui.showDivider();
            String input = this.ui.takeInput();
            Command command = Parser.parseCommand(input);

            try {
                switch (command.name()) {
                case "bye" -> {
                    this.ui.showGoodbye();
                    this.ui.showDivider();
                    return;
                }
                case "mark" -> {
                    int index = Parser.parseNumber(command.body());
                    try {
                        Task task = this.taskList.get(index - 1);
                        task.markAsDone();
                        this.ui.showTaskDone(task);
                    } catch (IndexOutOfBoundsException e) {
                        this.ui.showIndexError(index);
                    }
                }
                case "unmark" -> {
                    int index = Parser.parseNumber(command.body());
                    try {
                        Task task = this.taskList.get(index - 1);
                        task.markUndone();
                        this.ui.showTaskUndone(task);
                    } catch (IndexOutOfBoundsException e) {
                        this.ui.showIndexError(index);
                    }
                }
                case "delete" -> {
                    int index = Parser.parseNumber(command.body());
                    try {
                        Task task = this.taskList.remove(index - 1);
                        this.ui.showTaskRemoved(task);
                    } catch (IndexOutOfBoundsException e) {
                        this.ui.showIndexError(index);
                    }
                }
                case "todo" -> {
                    if (command.body().isEmpty()) {
                        this.ui.showEmptyDescriptionError();
                        continue;
                    }

                    Task task = new Todo(command.body());
                    this.taskList.add(task);
                    this.ui.showTaskAdded(task, this.taskList);
                }
                case "deadline" -> {
                    if (command.body().isEmpty()) {
                        this.ui.showEmptyDescriptionError();
                        continue;
                    }

                    HashMap<String, String> fields = Parser.validateFields(command.parameters(),
                            new HashSet<>(List.of("by")));

                    Task task;
                    try {
                        task = new Deadline(command.body(), fields.get("by"));
                    } catch (DateTimeParseException e) {
                        this.ui.showDateParseError(e.getParsedString());
                        continue;
                    }
                    this.taskList.add(task);
                    this.ui.showTaskAdded(task, this.taskList);
                }
                case "event" -> {
                    if (command.body().isEmpty()) {
                        this.ui.showEmptyDescriptionError();
                        continue;
                    }

                    HashMap<String, String> fields = Parser.validateFields(command.parameters(),
                            new HashSet<>(List.of("from", "to")));

                    Task task;
                    try {
                        task = new Event(command.body(), fields.get("from"), fields.get("to"));
                    } catch (DateTimeParseException e) {
                        this.ui.showDateParseError(e.getParsedString());
                        continue;
                    }
                    this.taskList.add(task);
                    this.ui.showTaskAdded(task, this.taskList);
                }
                case "list" -> {
                    if (this.taskList.isEmpty()) {
                        this.ui.showTaskListEmpty();
                    } else {
                        this.ui.showTaskList(this.taskList.getTasks());
                    }
                    continue;
                }
                case "find" -> {
                    List<Task> matchedTasks = this.taskList.find(command.body());

                    if (matchedTasks.isEmpty()) {
                        this.ui.showFindNothing(command.body());
                    } else {
                        this.ui.showFindResults(matchedTasks);
                    }
                }
                case "clear" -> {
                    this.taskList.clear();
                    this.ui.showTaskListCleared();
                }
                default -> throw new ProtoUnknownCommand(command.name());
                }

                // Save changes to hard disk
                try {
                    this.storage.save(this.taskList);
                } catch (IOException e) {
                    this.ui.showSavingError(e);
                }
            } catch (ProtoException e) {
                this.ui.showProtoException(e);
            }
        }
    }

    public static void main(String[] args) {
        new Proto(Paths.get(".", "data", "tasks.txt").toFile()).run();
    }
}
