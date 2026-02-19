package kirkstein.ui;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import kirkstein.task.Task;

/**
 * Handles all user interface interactions and message formatting.
 */
public class Ui {

    /**
     * Returns the welcome message.
     *
     * @return Welcome message string
     */
    public String showWelcome() {
        return """
                Hello! I'm KirkStein
                Welcome to my island!
                """;
    }

    /**
     * Returns the goodbye message.
     *
     * @return Goodbye message string
     */
    public String showGoodbye() {
        return "Bye! See you in the files.\n";
    }

    /**
     * Returns a formatted string displaying all tasks in the list.
     *
     * @param tasks The list of tasks to display
     * @return Formatted task list string
     */
    public String showTaskList(ArrayList<Task> tasks) {
        String taskLines = IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + "." + tasks.get(i).toString() + "\n")
                .collect(Collectors.joining());
        return "Here are your Epstein files:\n" + taskLines;
    }

    /**
     * Returns a confirmation message for a newly added task.
     *
     * @param task The task that was added
     * @param totalTasks The total number of tasks in the list
     * @return Task added confirmation message
     */
    public String showTaskAdded(Task task, int totalTasks) {
        return "Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + totalTasks + " tasks in the list.\n";
    }

    /**
     * Returns a confirmation message for a deleted task.
     *
     * @param task The task that was deleted
     * @param remainingTasks The number of tasks remaining in the list
     * @return Task deleted confirmation message
     */
    public String showTaskDeleted(Task task, int remainingTasks) {
        return "Noted. I've removed this file:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + remainingTasks + " files in the list.\n";
    }

    /**
     * Returns a confirmation message for a task marked as done.
     *
     * @param task The task that was marked
     * @return Task marked confirmation message
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this as redacted:\n"
                + task.toString() + "\n";
    }

    /**
     * Returns a confirmation message for a task unmarked as not done.
     *
     * @param task The task that was unmarked
     * @return Task unmarked confirmation message
     */
    public String showTaskUnmarked(Task task) {
        return "OK! I've unredacted this:\n"
                + task.toString() + "\n";
    }

    /**
     * Returns a formatted error message.
     *
     * @param message The error message to display
     * @return Formatted error message string
     */
    public String showError(String message) {
        return "OOPS!!! " + message + "\n";
    }

    /**
     * Returns a formatted string displaying tasks that match the search criteria.
     *
     * @param tasks List of tasks matching the search term
     * @return Formatted search results string
     */
    public String showFindResults(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No matching tasks found in your Epstein files.\n";
        }
        String taskLines = IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + "." + tasks.get(i).toString() + "\n")
                .collect(Collectors.joining());
        return "Here are your searched Epstein files:\n" + taskLines;
    }

    /**
     * Shows warning when dates clash
     *
     * @param clashes List of clashing Tasks.
     * @return String of clashing Tasks.
     */
    public String showClashWarning(ArrayList<Task> clashes) {
        StringBuilder result = new StringBuilder();
        result.append("Warning! This task clashes with:\n");
        for (Task task : clashes) {
            result.append("  ").append(task.toString()).append("\n");
        }
        result.append("Task has been added anyway.\n");
        return result.toString();
    }
}
