package kirkstein.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import kirkstein.task.Deadline;
import kirkstein.task.Event;
import kirkstein.task.Task;
import kirkstein.task.Todo;

/**
 * Handles storage of tasks
 */
public class Storage {
    private static final int TASK_TYPE_INDEX = 1;
    private static final int TASK_STATUS_INDEX = 4;
    private static final int TASK_DESCRIPTION_START = 7;
    private static final char MARKED_CHAR = 'X';

    private final String filePath;

    /**
     * Creates a Storage object with the provided text file path
     *
     * @param filePath The path to the text file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Save all tasks into text file
     *
     * @param tasks The array list of tasks
     */
    public void saveTask(ArrayList<Task> tasks) {
        try (FileWriter writer = new FileWriter(filePath)) {
            String content = tasks.stream()
                    .map(task -> task.toString() + "\n")
                    .collect(Collectors.joining());
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Warning: Failed to save tasks - " + e.getMessage());
        }
    }

    /**
     * Load tasks from text file
     *
     * @return Returns array list of tasks
     */
    public ArrayList<Task> loadTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char taskType = line.charAt(TASK_TYPE_INDEX);
                boolean marked = line.charAt(TASK_STATUS_INDEX) == MARKED_CHAR;
                String taskDescription = line.substring(TASK_DESCRIPTION_START).trim();

                if (taskType == Todo.TASK_TYPE) {
                    Todo todo = new Todo(taskDescription);
                    if (marked) {
                        todo.markTrue();
                    }
                    tasks.add(todo);
                    continue;
                }
                if (taskType == Deadline.TASK_TYPE) {
                    String[] deadlineParts = taskDescription.split(" \\(by: ");
                    String description = deadlineParts[0];
                    String dateStr = deadlineParts[1].replace(")", "");

                    LocalDate by = LocalDate.parse(dateStr, formatter);

                    Deadline deadline = new Deadline(description, by);
                    if (marked) {
                        deadline.markTrue();
                    }
                    tasks.add(deadline);
                    continue;
                }
                if (taskType == Event.TASK_TYPE) {
                    String[] eventParts = taskDescription.split(" \\(from: ");
                    String eventDescription = eventParts[0].trim();
                    String[] eventDatesParts = eventParts[1].split(" to: ");
                    String fromStr = eventDatesParts[0].trim();
                    String toStr = eventDatesParts[1].replace(")", "").trim();

                    LocalDate from = LocalDate.parse(fromStr, formatter);
                    LocalDate to = LocalDate.parse(toStr, formatter);

                    Event event = new Event(eventDescription, from, to);
                    if (marked) {
                        event.markTrue();
                    }
                    tasks.add(event);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Warning: Failed to save tasks - " + e.getMessage());
        }
        return tasks;
    }
}
