import kirkstein.task.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {

    @Test
    public void toString_unmarkedTodo_correctFormat() {
        Todo todo = new Todo("read epstein files");
        assertEquals("[T][ ] read epstein files", todo.toString());
    }

    @Test
    public void toString_markedTodo_correctFormat() {
        Todo todo = new Todo("sybau");
        todo.markTrue();
        assertEquals("[T][X] sybau", todo.toString());
    }

    @Test
    public void markAndUnmark_changesStatus() {
        Todo todo = new Todo("bruh");

        // Initially unmarked
        assertTrue(todo.toString().contains("[ ]"));

        // Mark it
        todo.markTrue();
        assertTrue(todo.toString().contains("[X]"));

        // Unmark it
        todo.markFalse();
        assertTrue(todo.toString().contains("[ ]"));
    }
}