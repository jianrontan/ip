import kirkstein.parser.Parser;
import kirkstein.exception.KirkSteinException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parseTaskNumber_validInput_success() throws KirkSteinException {
        assertEquals(5, Parser.parseTaskNumber("mark 5", 5));
        assertEquals(10, Parser.parseTaskNumber("delete 10", 7));
    }

    @Test
    public void parseTaskNumber_invalidInput_exceptionThrown() {
        assertThrows(KirkSteinException.class, () -> {
            Parser.parseTaskNumber("mark abc", 5);
        });
        assertThrows(KirkSteinException.class, () -> {
            Parser.parseTaskNumber("mark", 5);
        });
    }

    @Test
    public void parseTodoDescription_emptyDescription_exceptionThrown() {
        assertThrows(KirkSteinException.class, () -> {
            Parser.parseTodoDescription("todo ");
        });
        assertThrows(KirkSteinException.class, () -> {
            Parser.parseTodoDescription("todo");
        });
    }

    @Test
    public void parseTodoDescription_validDescription_success() throws KirkSteinException {
        assertEquals("read book", Parser.parseTodoDescription("todo read book"));
        assertEquals("homework", Parser.parseTodoDescription("todo homework"));
    }

    @Test
    public void parseDate_validFormats_success() throws KirkSteinException {
        // Test yyyy/MM/dd format
        LocalDate date1 = Parser.parseDate("2025/01/15");
        assertEquals(LocalDate.of(2025, 1, 15), date1);

        // Test dd/MM/yyyy format
        LocalDate date2 = Parser.parseDate("15/01/2025");
        assertEquals(LocalDate.of(2025, 1, 15), date2);
    }

    @Test
    public void parseDate_invalidFormat_exceptionThrown() {
        assertThrows(KirkSteinException.class, () -> {
            Parser.parseDate("2025-01-15"); // Wrong separator
        });
        assertThrows(KirkSteinException.class, () -> {
            Parser.parseDate("invalid date");
        });
    }

    @Test
    public void parseDeadline_validFormat_success() throws KirkSteinException {
        String[] result = Parser.parseDeadline("deadline homework /by 2025/01/15");
        assertEquals("homework", result[0]);
        assertEquals("2025/01/15", result[1]);
    }

    @Test
    public void parseDeadline_missingBy_exceptionThrown() {
        assertThrows(KirkSteinException.class, () -> {
            Parser.parseDeadline("deadline homework 2025/01/15");
        });
    }
}