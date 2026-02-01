package kirkstein.parser;

import kirkstein.exception.KirkSteinException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static int parseTaskNumber(String input, int skipChars) throws KirkSteinException {
        try {
            return Integer.parseInt(input.substring(skipChars).trim());
        } catch (Exception e) {
            throw new KirkSteinException("Invalid task number");
        }
    }

    public static String parseTodoDescription(String input) throws KirkSteinException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new KirkSteinException("Epstein todo description cannot be empty!");
        }
        return description;
    }

    public static String[] parseDeadline(String input) throws KirkSteinException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length != 2) {
            throw new KirkSteinException("Invalid kirk deadline format! Use: deadline <task> /by <date>");
        }
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    public static String[] parseEvent(String input) throws KirkSteinException {
        String remaining = input.substring(6);
        String[] parts1 = remaining.split(" /from ");
        if (parts1.length != 2) {
            throw new KirkSteinException("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
        }

        String[] parts2 = parts1[1].split(" /to ");
        if (parts2.length != 2) {
            throw new KirkSteinException("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
        }

        return new String[]{parts1[0].trim(), parts2[0].trim(), parts2[1].trim()};
    }

    public static LocalDate parseDate(String dateString) throws KirkSteinException {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e2) {
                throw new KirkSteinException("Invalid date format! Use yyyy/mm/dd or dd/mm/yyyy");
            }
        }
    }
}