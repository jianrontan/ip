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
            int taskNumber = Parser.parseTaskNumber(userInput, 5);
            list.get(taskNumber - 1).markTrue();
            storage.saveTask(list);
            ui.showTaskMarked(list.get(taskNumber - 1));
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
        } catch (Exception e) {
            ui.showError("Invalid mark command! Use: mark <task number>");
        }
    }

    private static void handleUnmark(String userInput) {
        try {
            int taskNumber = Parser.parseTaskNumber(userInput, 7);
            list.get(taskNumber - 1).markFalse();
            storage.saveTask(list);
            ui.showTaskUnmarked(list.get(taskNumber - 1));
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
        } catch (Exception e) {
            ui.showError("Invalid unmark command! Use: unmark <task number>");
        }
    }

    private static void handleDelete(String userInput) {
        try {
            int taskNumber = Parser.parseTaskNumber(userInput, 7);
            if (taskNumber < 1 || taskNumber > list.size()) {
                ui.showError("Invalid Epstein file page!");
                return;
            }
            Task removedTask = list.remove(taskNumber - 1);
            storage.saveTask(list);
            ui.showTaskDeleted(removedTask, list.size());
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
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
        try {
            String description = Parser.parseTodoDescription(userInput);
            Task task = new Todo(description);
            addTask(task);
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
        }
    }

    private static void handleDeadline(String userInput) {
        try {
            String[] parts = Parser.parseDeadline(userInput);
            String description = parts[0];
            LocalDate byDate = Parser.parseDate(parts[1]);

            Task task = new Deadline(description, byDate);
            addTask(task);
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
        }
    }

    private static void handleEvent(String userInput) {
        try {
            String[] parts = Parser.parseEvent(userInput);
            String description = parts[0];
            LocalDate fromDate = Parser.parseDate(parts[1]);
            LocalDate toDate = Parser.parseDate(parts[2]);

            Task task = new Event(description, fromDate, toDate);
            addTask(task);
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
        }
    }

    private static void addTask(Task task) {
        list.add(task);
        storage.saveTask(list);
        ui.showTaskAdded(task, list.size());
    }
}
