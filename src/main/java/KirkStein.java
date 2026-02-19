import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import kirkstein.exception.KirkSteinException;
import kirkstein.parser.Parser;
import kirkstein.storage.Storage;
import kirkstein.task.Deadline;
import kirkstein.task.Event;
import kirkstein.task.Task;
import kirkstein.task.Todo;
import kirkstein.tasklist.TaskList;
import kirkstein.ui.Ui;

/**
 * Main class for the KirkStein chatbot application
 * KirkStein is a task management chatbot that helps users manage
 * todos, deadlines, and events through a command-line interface
 */
public class KirkStein {
    private static final int MARK_PREFIX_LENGTH = 5; // "mark "
    private static final int UNMARK_PREFIX_LENGTH = 7; // "unmark "
    private static final int DELETE_PREFIX_LENGTH = 7; // "delete "

    private Storage storage;
    private Ui ui;
    private TaskList taskList;

    /**
     * Initializes KirkStein with storage, task list, and UI components
     */
    public KirkStein() {
        ui = new Ui();

        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        storage = new Storage("data/tasks.txt");
        taskList = new TaskList(storage.loadTask());
    }

    /**
     * Generates a response for the user's input
     *
     * @param input User's input command
     * @return Response string
     */
    public String getResponse(String input) {
        if (input.equals("bye")) {
            return ui.showGoodbye();
        } else if (input.equals("list")) {
            return ui.showTaskList(taskList.getTasks());
        } else if (input.startsWith("mark")) {
            return handleMark(input);
        } else if (input.startsWith("unmark")) {
            return handleUnmark(input);
        } else if (input.startsWith("delete")) {
            return handleDelete(input);
        } else if (input.startsWith("find")) {
            return handleFind(input);
        } else {
            return handleAdd(input);
        }
    }

    private String handleMark(String userInput) {
        try {
            int taskNumber = Parser.parseTaskNumber(userInput, MARK_PREFIX_LENGTH);
            taskList.markTask(taskNumber - 1);
            storage.saveTask(taskList.getTasks());
            return ui.showTaskMarked(taskList.getTask(taskNumber - 1));
        } catch (KirkSteinException e) {
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            return ui.showError("Invalid mark command! Use: mark <task number>");
        }
    }

    private String handleUnmark(String userInput) {
        try {
            int taskNumber = Parser.parseTaskNumber(userInput, UNMARK_PREFIX_LENGTH);
            taskList.unmarkTask(taskNumber - 1);
            storage.saveTask(taskList.getTasks());
            return ui.showTaskUnmarked(taskList.getTask(taskNumber - 1));
        } catch (KirkSteinException e) {
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            return ui.showError("Invalid unmark command! Use: unmark <task number>");
        }
    }

    private String handleDelete(String userInput) {
        try {
            int taskNumber = Parser.parseTaskNumber(userInput, DELETE_PREFIX_LENGTH);
            if (taskNumber < 1 || taskNumber > taskList.size()) {
                return ui.showError("Invalid Epstein file page!");
            }
            Task removedTask = taskList.removeTask(taskNumber - 1);
            storage.saveTask(taskList.getTasks());
            return ui.showTaskDeleted(removedTask, taskList.size());
        } catch (KirkSteinException e) {
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            return ui.showError("Invalid delete command! Use: delete <task number>");
        }
    }

    private String handleFind(String userInput) {
        try {
            String keyword = Parser.parseFindTerm(userInput);
            ArrayList<Task> matchingTasks = taskList.findTask(keyword);
            return ui.showFindResults(matchingTasks);
        } catch (KirkSteinException e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleAdd(String userInput) {
        if (userInput.startsWith("todo ")) {
            return handleTodo(userInput);
        } else if (userInput.startsWith("deadline ")) {
            return handleDeadline(userInput);
        } else if (userInput.startsWith("event ")) {
            return handleEvent(userInput);
        }

        if (userInput.equals("todo") || userInput.startsWith("todo ")) {
            return ui.showError("Epstein todo description cannot be empty!");
        } else if (userInput.equals("deadline") || userInput.startsWith("deadline ")) {
            return ui.showError("Invalid kirk deadline format! Use: deadline <task> /by <date>");
        } else if (userInput.equals("event") || userInput.startsWith("event ")) {
            return ui.showError("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
        } else {
            return ui.showError("Unknown command! It has to start with todo, deadline, or event");
        }
    }

    private String handleTodo(String userInput) {
        try {
            String description = Parser.parseTodoDescription(userInput);
            Task task = new Todo(description);
            return addTask(task);
        } catch (KirkSteinException e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleDeadline(String userInput) {
        try {
            String[] parts = Parser.parseDeadline(userInput);
            String description = parts[0];
            LocalDate byDate = Parser.parseDate(parts[1]);

            Task task = new Deadline(description, byDate);
            return addTask(task);
        } catch (KirkSteinException e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleEvent(String userInput) {
        try {
            String[] parts = Parser.parseEvent(userInput);
            String description = parts[0];
            LocalDate fromDate = Parser.parseDate(parts[1]);
            LocalDate toDate = Parser.parseDate(parts[2]);

            Task task = new Event(description, fromDate, toDate);
            return addTask(task);
        } catch (KirkSteinException e) {
            return ui.showError(e.getMessage());
        }
    }

    private String addTask(Task task) {
        taskList.addTask(task);
        storage.saveTask(taskList.getTasks());
        return ui.showTaskAdded(task, taskList.size());
    }
}
