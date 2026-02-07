package kirkstein.task;

/**
 * Represents a task with a description and completion status
 */
public class Task {
    private final String description;
    private TaskStatus status;

    /**
     * Creates a new unmarked task
     *
     * @param description The task description
     */
    public Task(String description) {
        this.description = description;
        this.status = TaskStatus.UNMARKED;
    }

    /**
     * Returns description of the task
     *
     * @return Returns description of task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as done
     */
    public void markTrue() {
        this.status = TaskStatus.MARKED;
    }

    /**
     * Marks this task and not done
     */
    public void markFalse() {
        this.status = TaskStatus.UNMARKED;
    }

    @Override
    public String toString() {
        if (this.status == TaskStatus.MARKED) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }
}
