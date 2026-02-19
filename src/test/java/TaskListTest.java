import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kirkstein.task.Deadline;
import kirkstein.task.Event;
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

    // Event vs Event clashes
    @Test
    public void getClashingTasks_overlappingEvents_returnsClash() {
        LocalDate from1 = LocalDate.of(2025, 12, 1);
        LocalDate to1 = LocalDate.of(2025, 12, 5);
        LocalDate from2 = LocalDate.of(2025, 12, 3);
        LocalDate to2 = LocalDate.of(2025, 12, 7);
        taskList.addTask(new Event("meeting", from1, to1));

        ArrayList<Task> clashes = taskList.getClashingTasks(new Event("conference", from2, to2));

        assertEquals(1, clashes.size());
    }

    @Test
    public void getClashingTasks_nonOverlappingEvents_returnsEmpty() {
        LocalDate from1 = LocalDate.of(2025, 12, 1);
        LocalDate to1 = LocalDate.of(2025, 12, 3);
        LocalDate from2 = LocalDate.of(2025, 12, 4);
        LocalDate to2 = LocalDate.of(2025, 12, 7);
        taskList.addTask(new Event("meeting", from1, to1));

        ArrayList<Task> clashes = taskList.getClashingTasks(new Event("conference", from2, to2));

        assertEquals(0, clashes.size());
    }

    // Deadline vs Deadline clashes
    @Test
    public void getClashingTasks_sameDeadlines_returnsClash() {
        LocalDate date = LocalDate.of(2025, 12, 1);
        taskList.addTask(new Deadline("assignment", date));

        ArrayList<Task> clashes = taskList.getClashingTasks(new Deadline("project", date));

        assertEquals(1, clashes.size());
    }

    @Test
    public void getClashingTasks_differentDeadlines_returnsEmpty() {
        taskList.addTask(new Deadline("assignment", LocalDate.of(2025, 12, 1)));

        ArrayList<Task> clashes = taskList.getClashingTasks(
                new Deadline("project", LocalDate.of(2025, 12, 2)));

        assertEquals(0, clashes.size());
    }

    // Deadline within Event clashes
    @Test
    public void getClashingTasks_deadlineWithinEvent_returnsClash() {
        LocalDate from = LocalDate.of(2025, 12, 1);
        LocalDate to = LocalDate.of(2025, 12, 5);
        taskList.addTask(new Event("holiday", from, to));

        ArrayList<Task> clashes = taskList.getClashingTasks(
                new Deadline("assignment", LocalDate.of(2025, 12, 3)));

        assertEquals(1, clashes.size());
    }

    @Test
    public void getClashingTasks_deadlineOutsideEvent_returnsEmpty() {
        LocalDate from = LocalDate.of(2025, 12, 1);
        LocalDate to = LocalDate.of(2025, 12, 5);
        taskList.addTask(new Event("holiday", from, to));

        ArrayList<Task> clashes = taskList.getClashingTasks(
                new Deadline("assignment", LocalDate.of(2025, 12, 6)));

        assertEquals(0, clashes.size());
    }

    // Todo should never clash
    @Test
    public void getClashingTasks_todoTask_returnsEmpty() {
        LocalDate from = LocalDate.of(2025, 12, 1);
        LocalDate to = LocalDate.of(2025, 12, 5);
        taskList.addTask(new Event("holiday", from, to));
        taskList.addTask(new Deadline("assignment", LocalDate.of(2025, 12, 3)));

        ArrayList<Task> clashes = taskList.getClashingTasks(new Todo("read book"));

        assertEquals(0, clashes.size());
    }
}
