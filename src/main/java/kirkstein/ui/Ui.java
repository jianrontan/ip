package kirkstein.ui;

import kirkstein.task.Task;

import java.util.ArrayList;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Hello! I'm KirkStein");
        System.out.println("Welcome to my island!");
        System.out.println(LINE);
    }

    public void showGoodbye() {
        System.out.println(LINE);
        System.out.println("Bye! See you in the files.");
        System.out.println(LINE);
    }

    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println(LINE);
        System.out.println("Here are your Epstein files:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i).toString());
        }
        System.out.println(LINE);
    }

    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showTaskDeleted(Task task, int remainingTasks) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this file:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + remainingTasks + " files in the list.");
        System.out.println(LINE);
    }

    public void showTaskMarked(Task task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this as redacted:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("OK! I've unredacted this:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println(LINE);
        System.out.println("OOPS!!! " + message);
        System.out.println(LINE);
    }

    /**
     * Displays the tasks whose description matches the search term
     *
     * @param tasks Array list of tasks that matches the search term
     */
    public void showFindResults(ArrayList<Task> tasks) {
        System.out.println(LINE);
        if (tasks.isEmpty()) {
            System.out.println("No matching tasks found in your Epstein files.");
        } else {
            System.out.println("Here are your searched Epstein files:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i).toString());
            }
        }
        System.out.println(LINE);
    }
}