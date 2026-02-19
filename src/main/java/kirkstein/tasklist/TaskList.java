package kirkstein.tasklist;

import java.util.ArrayList;

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
        ArrayList<Task> result = new ArrayList<>();
        for (Task task : list) {
            if (task.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                result.add(task);
            }
        }
        return result;
    }
}
