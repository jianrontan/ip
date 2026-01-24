public class Task {
    private final String description;
    private final boolean marked;

    Task(String description) {
        this.description = description;
        this.marked = false;
    }

    private Task(String description, boolean marked) {
        this.description = description;
        this.marked = marked;
    }

    public Task markTrue() {
        return new Task(this.description, true);
    }

    public Task markFalse() {
        return new Task(this.description, false);
    }

    public String printTask() {
        if (this.marked) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }
}