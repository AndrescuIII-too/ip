package main.java;

public class Event extends Task{
    private final String startTime;
    private final String endTime;

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getTaskIcon() {
        return "E";
    }

    @Override
    public String getDisplayString() {
        return super.getDisplayString() +
                " (from: " + this.startTime + " to: " + this.endTime + ")";
    }
}
