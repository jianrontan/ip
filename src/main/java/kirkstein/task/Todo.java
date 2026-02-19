package kirkstein.task;

/**
 * Represents a todo task
 */
public class Todo extends Task {
    public static final char TASK_TYPE = 'T';

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
