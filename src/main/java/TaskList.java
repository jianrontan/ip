import java.util.ArrayList;

public class TaskList {
    private static ArrayList<Task> list;

    TaskList(ArrayList<Task> tasks) {
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
}
