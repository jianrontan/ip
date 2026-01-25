public class Task {
    private final String description;
    private boolean marked;

    Task(String description) {
        this.description = description;
        this.marked = false;
    }

    private Task(String description, boolean marked) {
        this.description = description;
        this.marked = marked;
    }

    public void markTrue() {
        this.marked = true;
    }

    public void markFalse() {
        this.marked = false;
    }

    public String toString() {
        if (this.marked) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }
}