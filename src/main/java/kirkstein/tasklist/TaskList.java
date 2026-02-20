package kirkstein.tasklist;

import java.util.ArrayList;
import java.util.stream.Collectors;

import kirkstein.task.Deadline;
import kirkstein.task.Event;
import kirkstein.task.Task;


/**
 * Manages a collection of tasks
 */
public class TaskList {
    private ArrayList<Task> list;

    /**
     * Creates a TaskList with the given tasks
     *
     * @param tasks The initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Task list cannot be null";
        list = tasks;
    }

    /**
     * Returns all tasks in the list
     *
     * @return The ArrayList of tasks
     */
    public ArrayList<Task> getTasks() {
        return list;
    }

    /**
     * Returns the number of tasks in the list
     *
     * @return The size of the task list
     */
    public int size() {
        return list.size();
    }

    /**
     * Marks a task as done
     *
     * @param index The index of the task to mark
     */
    public void markTask(int index) {
        list.get(index).markTrue();
    }

    /**
     * Unmarks a task
     *
     * @param index The index of the task to unmark
     */
    public void unmarkTask(int index) {
        list.get(index).markFalse();
    }

    /**
     * Returns the task at the specified index
     *
     * @param index The index of the task
     * @return The task at the index
     */
    public Task getTask(int index) {
        return list.get(index);
    }

    /**
     * Removes and returns the task at the specified index
     *
     * @param index The index of the task to remove
     * @return The removed task
     */
    public Task removeTask(int index) {
        assert index >= 0 && index < list.size() : "Index out of bounds: " + index;
        int sizeBefore = list.size();
        Task removed = list.remove(index);
        assert list.size() == sizeBefore - 1 : "Task was not removed correctly";
        assert removed != null : "Removed task should not be null";
        return removed;
    }

    /**
     * Adds a task to the list
     *
     * @param task The task to add
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add null task";
        int sizeBefore = list.size();
        list.add(task);
        assert list.size() == sizeBefore + 1 : "Task was not added correctly";
    }

    /**
     * Finds tasks whose descriptions contain the search keyword.
     *
     * @param searchTerm The keyword to search for.
     * @return ArrayList of tasks that match the keyword.
     */
    public ArrayList<Task> findTask(String searchTerm) {
        return list.stream()
                .filter(task -> task.getDescription().toLowerCase()
                        .contains(searchTerm.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get the list of tasks whose dates clash with the new task added
     *
     * @param newTask New task added.
     * @return Returns array of tasks whose dates clash.
     */
    public ArrayList<Task> getClashingTasks(Task newTask) {
        return list.stream()
                .filter(existing -> clashesWith(newTask, existing))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean clashesWith(Task newTask, Task existing) {
        if (newTask instanceof Event && existing instanceof Event) {
            return eventsClash((Event) newTask, (Event) existing);
        } else if (newTask instanceof Deadline && existing instanceof Deadline) {
            return deadlinesClash((Deadline) newTask, (Deadline) existing);
        } else if (newTask instanceof Event && existing instanceof Deadline) {
            return deadlineWithinEvent((Deadline) existing, (Event) newTask);
        } else if (newTask instanceof Deadline && existing instanceof Event) {
            return deadlineWithinEvent((Deadline) newTask, (Event) existing);
        }
        return false;
    }

    /**
     * Claude Sonnet 4.6 Extended Thinking suggested to refactor the earlier complicated expression
     * to extract out the booleans making the code more readable.
     */
    private boolean eventsClash(Event a, Event b) {
        boolean aEndsBeforeB = a.getTo().isBefore(b.getFrom());
        boolean bEndsBeforeA = b.getTo().isBefore(a.getFrom());
        return !aEndsBeforeB && !bEndsBeforeA;
    }

    private boolean deadlinesClash(Deadline a, Deadline b) {
        return a.getBy().equals(b.getBy());
    }

    private boolean deadlineWithinEvent(Deadline deadline, Event event) {
        return !deadline.getBy().isBefore(event.getFrom())
                && !deadline.getBy().isAfter(event.getTo());
    }
}
