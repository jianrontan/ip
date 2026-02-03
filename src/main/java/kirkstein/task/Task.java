package kirkstein.task;

public class Task {
    private final String description;
    private TaskStatus status;

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

    public void markTrue() {
        this.status = TaskStatus.MARKED;
    }

    public void markFalse() {
        this.status = TaskStatus.UNMARKED;
    }

    public String toString() {
        if (this.status == TaskStatus.MARKED) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }
}