package kirkstein.tasklist;

import kirkstein.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> list;

    public TaskList(ArrayList<Task> tasks) {
        list = tasks;
    }

    public ArrayList<Task> getTasks() {
        return list;
    }

    public int size() {
        return list.size();
    }

    public void markTask(int index) {
        list.get(index).markTrue();
    }

    public void unmarkTask(int index) {
        list.get(index).markFalse();
    }

    public Task getTask(int index) {
        return list.get(index);
    }

    public Task removeTask(int index) {
        return list.remove(index);
    }

    public void addTask(Task task) {
        list.add(task);
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
