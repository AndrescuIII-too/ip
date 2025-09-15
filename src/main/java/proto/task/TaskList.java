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
     * Returns entire task list as string to display in ui.
     *
     * @return Task list as string.
     */
    public String getDisplayString() {
        List<IndexedTask> indexedTasks = new ArrayList<>();
        for (int i = 0; i < this.tasks.size(); i++) {
            indexedTasks.add(new IndexedTask(i + 1, this.tasks.get(i)));
        }
        return TaskList.getDisplayString(indexedTasks);
    }

    /**
     * Returns task list with indices as string to display in ui.
     *
     * @return Task list as string.
     */
    public static String getDisplayString(List<IndexedTask> indexedTasks) {
        StringBuilder sb = new StringBuilder();
        for (IndexedTask indexedTask: indexedTasks) {
            int index = indexedTask.index();
            Task task = indexedTask.task();
            sb.append(index < 10 ? " " : "")
                    .append(index)
                    .append(".")
                    .append(task.getDisplayString())
                    .append("\n");
        }

        sb.deleteCharAt(sb.length() - 1); // Strip trailing space
        return sb.toString();
    }

    public List<IndexedTask> find(String text) {
        Pattern rx = Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE);
        List<IndexedTask> matchedTasks = new ArrayList<>();

        for (int i = 0; i < this.tasks.size(); i++) {
            Task task = this.tasks.get(i);
            if (rx.matcher(task.description).find()) {
                matchedTasks.add(new IndexedTask(i + 1, task));
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
