import kirkstein.task.Deadline;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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