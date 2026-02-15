import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import kirkstein.task.Deadline;

public class DeadlineTest {

    @Test
    public void toString_unmarkedDeadline_correctFormat() {
        LocalDate date = LocalDate.of(2025, 1, 15);
        Deadline deadline = new Deadline("visit tel aviv", date);

        assertEquals("[D][ ] visit tel aviv (by: Jan 15 2025)", deadline.toString());
    }

    @Test
    public void toString_markedDeadline_correctFormat() {
        LocalDate date = LocalDate.of(2025, 12, 31);
        Deadline deadline = new Deadline("release epstein files", date);
        deadline.markTrue();

        assertEquals("[D][X] release epstein files (by: Dec 31 2025)", deadline.toString());
    }
}
