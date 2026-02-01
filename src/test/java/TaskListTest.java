import kirkstein.tasklist.TaskList;
import kirkstein.task.Task;
import kirkstein.task.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    public void addTask_singleTask_success() {
        Task todo = new Todo("read book");
        taskList.addTask(todo);

        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.getTask(0));
    }

    @Test
    public void addTask_multipleTasks_success() {
        taskList.addTask(new Todo("task 1"));
        taskList.addTask(new Todo("task 2"));
        taskList.addTask(new Todo("task 3"));

        assertEquals(3, taskList.size());
    }

    @Test
    public void removeTask_validIndex_success() {
        Task todo = new Todo("read book");
        taskList.addTask(todo);

        Task removed = taskList.removeTask(0);

        assertEquals(todo, removed);
        assertEquals(0, taskList.size());
    }

    @Test
    public void markTask_validIndex_taskMarked() {
        Task todo = new Todo("read book");
        taskList.addTask(todo);

        taskList.markTask(0);

        assertTrue(taskList.getTask(0).toString().contains("[X]"));
    }

    @Test
    public void unmarkTask_markedTask_taskUnmarked() {
        Task todo = new Todo("read book");
        taskList.addTask(todo);
        taskList.markTask(0);

        taskList.unmarkTask(0);

        assertTrue(taskList.getTask(0).toString().contains("[ ]"));
    }
}