public class Task {
    private final String description;
    private TaskStatus status;

    Task(String description) {
        this.description = description;
        this.status = TaskStatus.UNMARKED;
    }

    private Task(String description, boolean marked) {
        this.description = description;
        this.status = TaskStatus.MARKED;
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