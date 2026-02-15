import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import kirkstein.task.Event;

public class EventTest {

    @Test
    public void toString_unmarkedEvent_correctFormat() {
        LocalDate from = LocalDate.of(2025, 12, 1);
        LocalDate to = LocalDate.of(2025, 12, 2);
        Event event = new Event("team meeting", from, to);

        assertEquals("[E][ ] team meeting (from: Dec 01 2025 to: Dec 02 2025)", event.toString());
    }

    @Test
    public void toString_markedEvent_correctFormat() {
        LocalDate from = LocalDate.of(2025, 12, 1);
        LocalDate to = LocalDate.of(2025, 12, 2);
        Event event = new Event("book club", from, to);
        event.markTrue();

        assertEquals("[E][X] book club (from: Dec 01 2025 to: Dec 02 2025)", event.toString());
    }
}
