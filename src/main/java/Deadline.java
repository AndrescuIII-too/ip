package main.java;

public class Deadline extends Task {
    private final String endTime;

    public Deadline(String description, String endTime) {
        super(description);
        this.endTime = endTime;
    }

    @Override
    public String getTaskIcon() {
        return "D";
    }

    @Override
    public String getDisplayString() {
        return super.getDisplayString() +
                " (by: " + this.endTime + ")";
    }
}
