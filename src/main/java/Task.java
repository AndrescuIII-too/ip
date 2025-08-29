package main.java;

import java.util.ArrayList;

public class Task {
    private final String description;
    private boolean isDone = false;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    public String getDisplayString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
