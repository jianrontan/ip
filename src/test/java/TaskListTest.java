import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kirkstein.task.Task;
import kirkstein.task.Todo;
import kirkstein.tasklist.TaskList;

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

    @Test
    public void findTask_multipleMatches_success() {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("buy groceries"));
        taskList.addTask(new Todo("return book"));

        ArrayList<Task> results = taskList.findTask("book");

        assertEquals(2, results.size());
        assertTrue(results.get(0).getDescription().contains("book"));
        assertTrue(results.get(1).getDescription().contains("book"));
    }

    @Test
    public void findTask_noMatches_emptyList() {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("buy groceries"));

        ArrayList<Task> results = taskList.findTask("homework");

        assertEquals(0, results.size());
    }
}
