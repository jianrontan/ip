package kirkstein.task;

import java.time.LocalDate;

/**
 * Represents a task that is an event with a date range
 */
public class Event extends Task {
    public static final char TASK_TYPE = 'E';

    protected LocalDate from;
    protected LocalDate to;

    /**
     * Constructor to create Event object
     *
     * @param description Description of event
     * @param from LocalDate of when event starts
     * @param to LocalDate of when event ends
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns from date
     *
     * @return Date object of from.
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns to date
     *
     * @return Date object of to.
     */
    public LocalDate getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DATE_FORMATTER)
                + " to: " + to.format(DATE_FORMATTER) + ")";
    }
}
