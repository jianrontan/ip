package kirkstein.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import kirkstein.exception.KirkSteinException;

/**
 * Handles parsing of user input commands and data.
 */
public class Parser {

    /**
     * Parses the task number from user input
     *
     * @param input The user's input string
     * @param skipChars Number of characters to skip before parsing the number
     * @return The parsed task number
     * @throws KirkSteinException If the input is not a valid number
     */
    public static int parseTaskNumber(String input, int skipChars) throws KirkSteinException {
        try {
            return Integer.parseInt(input.substring(skipChars).trim());
        } catch (Exception e) {
            throw new KirkSteinException("Invalid task number");
        }
    }

    /**
     * Parses the description from a todo command.
     *
     * @param input The user input string.
     * @return The todo description.
     * @throws KirkSteinException If the description is empty.
     */
    public static String parseTodoDescription(String input) throws KirkSteinException {
        if (input.length() <= 5) {
            throw new KirkSteinException("Epstein todo description cannot be empty!");
        }

        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new KirkSteinException("Epstein todo description cannot be empty!");
        }
        return description;
    }

    /**
     * Parses a deadline command into description and date.
     *
     * @param input The user input string.
     * @return Array containing [description, dateString].
     * @throws KirkSteinException If the format is invalid.
     */
    public static String[] parseDeadline(String input) throws KirkSteinException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length != 2) {
            throw new KirkSteinException("Invalid kirk deadline format! Use: deadline <task> /by <date>");
        }
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    /**
     * Parses an event command into description and dates.
     *
     * @param input The user input string.
     * @return Array containing [description, fromDate, toDate].
     * @throws KirkSteinException If the format is invalid.
     */
    public static String[] parseEvent(String input) throws KirkSteinException {
        String remaining = input.substring(6);
        String[] descriptionAndTime = remaining.split(" /from ");
        if (descriptionAndTime.length != 2) {
            throw new KirkSteinException("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
        }

        String[] timeRange = descriptionAndTime[1].split(" /to ");
        if (timeRange.length != 2) {
            throw new KirkSteinException("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
        }

        return new String[]{descriptionAndTime[0].trim(), timeRange[0].trim(), timeRange[1].trim()};
    }

    /**
     * Parses a date string in yyyy/MM/dd or dd/MM/yyyy format.
     *
     * @param dateString The date string to parse.
     * @return The parsed LocalDate.
     * @throws KirkSteinException If the date format is invalid.
     */
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

    /**
     * Parses the search term when user uses find command
     *
     * @param input The user's input
     * @return Returns the user's search term
     * @throws KirkSteinException Throws exception when search is invalid (empty)
     */
    public static String parseFindTerm(String input) throws KirkSteinException {
        if (input.length() <= 5) {
            throw new KirkSteinException("Search term cannot be empty!");
        }

        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new KirkSteinException("Search term cannot be empty!");
        }
        return keyword;
    }
}
