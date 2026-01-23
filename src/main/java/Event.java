public class Event extends Task {
    protected String deadline;
    protected String startDate;

    public Event(String description, String startDate, String deadline) {
        super(description);
        this.startDate = startDate;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from:" + startDate + " to: " + deadline + ")";
    }
}
