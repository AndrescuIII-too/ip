package proto.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }

    /**
     * Returns task list as string to display in ui.
     *
     * @return Task list as string.
     */
    public static String getDisplayString(List<Task> tasks) {
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

    public List<Task> find(String text) {
        Pattern rx = Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE);
        List<Task> matchedTasks = new ArrayList<>();

        for (Task task : this.tasks) {
            if (rx.matcher(task.description).find()) {
                matchedTasks.add(task);
            }
        }

        return matchedTasks;
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    public int size() {
        return this.tasks.size();
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    public void clear() {
        this.tasks.clear();
    }
}
