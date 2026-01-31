import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class KirkStein {
    private static ArrayList<Task> list;
    private static Storage storage;
    private static Ui ui;

    public static void main(String[] args) {
        ui = new Ui();

        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        storage = new Storage("data/tasks.txt");
        list = storage.loadTask();

        Scanner input = new Scanner(System.in);
        ui.showWelcome();

        // Initial input
        String userInput = "";
        // Loop
        while (!userInput.equals("bye")) {
            // Get input
            userInput = input.nextLine();
            // End loop
            if (userInput.equals("bye")) {
                ui.showGoodbye();
            }
            // Display list
            else if (userInput.equals("list")) {
                ui.showTaskList(list);
            }
            // Mark item
            else if (userInput.startsWith("mark")) {
                handleMark(userInput);
            }
            // Unmark item
            else if (userInput.startsWith("unmark")) {
                handleUnmark(userInput);
            }
            // Delete item
            else if (userInput.startsWith("delete")) {
                handleDelete(userInput);
            }
            // Add to list
            else {
                handleAdd(userInput);
            }
        }
        input.close();
    }

    private static void handleMark(String userInput) {
        try {
            int mark = Integer.parseInt(userInput.substring(5));
            list.get(mark - 1).markTrue();
            storage.saveTask(list);
            ui.showTaskMarked(list.get(mark - 1));
        }
        // Add to list
        catch (Exception e) {
            ui.showError("Invalid mark command! Use: mark <task number>");
        }
    }

    private static void handleUnmark(String userInput) {
        try {
            int unmark = Integer.parseInt(userInput.substring(7));
            list.get(unmark - 1).markFalse();
            storage.saveTask(list);
            ui.showTaskUnmarked(list.get(unmark - 1));
        }
        // Add to list
        catch (Exception e) {
            ui.showError("Invalid unmark command! Use: unmark <task number>");
        }
    }

    private static void handleDelete(String userInput) {
        try {
            int taskNum = Integer.parseInt(userInput.substring(7).trim());
            if (taskNum < 1 || taskNum > list.size()) {
                ui.showError("Invalid Epstein file page!");
                return;
            }
            Task removedTask = list.remove(taskNum - 1);
            storage.saveTask(list);
            ui.showTaskDeleted(removedTask, list.size());
        } catch (Exception e) {
            ui.showError("Invalid delete command! Use: delete <task number>");
        }
    }

    private static void handleAdd(String userInput) {
        if (userInput.startsWith("todo ")) {
            handleTodo(userInput);
        } else if (userInput.startsWith("deadline ")) {
            handleDeadline(userInput);
        } else if (userInput.startsWith("event ")) {
            handleEvent(userInput);
        } else if (userInput.startsWith("todo")) {
            ui.showError("Epstein todo description cannot be empty!");
        } else if (userInput.startsWith("deadline")) {
            ui.showError("Invalid kirk deadline format! Use: deadline <task> /by <date>");
        } else if (userInput.startsWith("event")) {
            ui.showError("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
        } else {
            ui.showError("That can't be part of the Epstein files diddy blud! It has to start with todo, deadline, or event");
        }
    }

    private static void handleTodo(String userInput) {
        String description = userInput.substring(5).trim();
        if (description.isEmpty()) {
            ui.showError("Epstein todo description cannot be empty!");
            return;
        }
        Task task = new Todo(description);
        addTask(task);
    }

    private static LocalDate parseDate(String date) throws DateTimeParseException {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } catch (DateTimeParseException e1) {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e2) {
                throw new DateTimeParseException("Invalid date format", date, 0);
            }
        }
    }

    private static void handleDeadline(String userInput) {
        try {
            String[] parts = userInput.substring(9).split(" /by ");
            if (parts.length != 2) {
                ui.showError("Invalid kirk deadline format! Use: deadline <task> /by <date>");
                return;
            }
            LocalDate byDate = parseDate(parts[1].trim());
            Task task = new Deadline(parts[0].trim(), byDate);
            addTask(task);
        } catch (DateTimeParseException e) {
            ui.showError("Invalid date format! Use yyyy/mm/dd or dd/mm/yyyy");
        } catch (Exception e) {
            ui.showError("Invalid deadline format!");
        }
    }

    private static void handleEvent(String userInput) {
        try {
            String remaining = userInput.substring(6);
            String[] parts1 = remaining.split(" /from ");
            if (parts1.length != 2) {
                ui.showError("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
                return;
            }
            String[] parts2 = parts1[1].split(" /to ");
            if (parts2.length != 2) {
                ui.showError("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
                return;
            }

            LocalDate fromDate = parseDate(parts2[0].trim());
            LocalDate toDate = parseDate(parts2[1].trim());

            Task task = new Event(parts1[0].trim(), fromDate, toDate);
            addTask(task);
        } catch (Exception e) {
            ui.showError("Invalid diddy party format!");
        }
    }

    private static void addTask(Task task) {
        list.add(task);
        storage.saveTask(list);
        ui.showTaskAdded(task, list.size());
    }
}
