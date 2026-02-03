import kirkstein.task.Task;
import kirkstein.task.Todo;
import kirkstein.task.Deadline;
import kirkstein.task.Event;
import kirkstein.exception.KirkSteinException;
import kirkstein.storage.Storage;
import kirkstein.ui.Ui;
import kirkstein.parser.Parser;
import kirkstein.tasklist.TaskList;
import java.util.ArrayList;

import java.util.Scanner;
import java.io.File;
import java.time.LocalDate;

/**
 * Main class for the KirkStein chatbot application
 * KirkStein is a task management chatbot that helps users manage
 * todos, deadlines, and events through a command-line interface
 */
public class KirkStein {
    private static Storage storage;
    private static Ui ui;
    private static TaskList taskList;

    /**
     * Entry point for the KirkStein application
     * Initializes the chatbot and starts the main command loop
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        ui = new Ui();

        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        storage = new Storage("data/tasks.txt");
        taskList = new TaskList(storage.loadTask());

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
                ui.showTaskList(taskList.getTasks());
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
            // find item
            else if (userInput.startsWith("find")) {
                handleFind(userInput);
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
            taskList.markTask(taskNumber - 1);
            storage.saveTask(taskList.getTasks());
            ui.showTaskMarked(taskList.getTask(taskNumber - 1));
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
        } catch (Exception e) {
            ui.showError("Invalid mark command! Use: mark <task number>");
        }
    }

    private static void handleUnmark(String userInput) {
        try {
            int taskNumber = Parser.parseTaskNumber(userInput, 7);
            taskList.unmarkTask(taskNumber - 1);
            storage.saveTask(taskList.getTasks());
            ui.showTaskUnmarked(taskList.getTask(taskNumber - 1));
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
        } catch (Exception e) {
            ui.showError("Invalid unmark command! Use: unmark <task number>");
        }
    }

    private static void handleDelete(String userInput) {
        try {
            int taskNumber = Parser.parseTaskNumber(userInput, 7);
            if (taskNumber < 1 || taskNumber > taskList.size()) {
                ui.showError("Invalid Epstein file page!");
                return;
            }
            Task removedTask = taskList.removeTask(taskNumber - 1);
            storage.saveTask(taskList.getTasks());
            ui.showTaskDeleted(removedTask, taskList.size());
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
        } catch (Exception e) {
            ui.showError("Invalid delete command! Use: delete <task number>");
        }
    }

    private static void handleFind(String userInput) {
        try {
            String keyword = Parser.parseFindTerm(userInput);
            ArrayList<Task> matchingTasks = taskList.findTask(keyword);
            ui.showFindResults(matchingTasks);
        } catch (KirkSteinException e) {
            ui.showError(e.getMessage());
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
        taskList.addTask(task);
        storage.saveTask(taskList.getTasks());
        ui.showTaskAdded(task, taskList.size());
    }
}
