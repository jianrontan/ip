package kirkstein.task;

import java.time.LocalDate;

/**
 * Represents a task with a deadline
 */
public class Deadline extends Task {
    public static final char TASK_TYPE = 'D';

    protected LocalDate by;

    /**
     * Constructor for creating Deadline object
     *
     * @param description Description of Deadline task
     * @param by LocalDate object of deadline
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        assert by != null : "Deadline date cannot be null";
        this.by = by;
    }

    /**
     * Return by date
     *
     * @return Date object of by.
     */
    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DATE_FORMATTER) + ")";
    }
}
