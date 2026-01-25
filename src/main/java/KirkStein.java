import java.util.Scanner;
import java.util.ArrayList;

public class KirkStein {
    private static ArrayList<Task> list = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        printWelcome();

        // Initial input
        String userInput = "";
        // Loop
        while (!userInput.equals("bye")) {
            // Get input
            userInput = input.nextLine();
            // End loop
            if (userInput.equals("bye")) {
                printGoodbye();
            }
            // Display list
            else if (userInput.equals("list")) {
                printList();
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

    private static void printWelcome() {
        // Initial logo
        String logo = "____________________________________________________________\n"
                + "Hello! I'm KirkStein\n"
                + "Welcome to my island!\n"
                + "____________________________________________________________";
        System.out.println(logo);
    }

    private static void printGoodbye() {
        String endLoop = "____________________________________________________________\n"
                + "Bye! See you in the files.\n"
                + "____________________________________________________________";
        System.out.println(endLoop);
    }

    private static void printList() {
        String printList = "____________________________________________________________\n"
                + "Here are your Epstein files:";
        System.out.println(printList);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + "." + list.get(i).toString());
        }
        System.out.println("____________________________________________________________");
    }

    private static void handleMark(String userInput) {
        try {
            int mark = Integer.parseInt(userInput.substring(5));
            list.get(mark - 1).markTrue();
            String markDone = "____________________________________________________________\n"
                    + "Nice! I've marked this as redacted:";
            System.out.println(markDone);
            System.out.println(list.get(mark - 1).toString());
            System.out.println("____________________________________________________________");
        }
        // Add to list
        catch (Exception e) {
            printError("Invalid mark command! Use: mark <task number>");
        }
    }

    private static void handleUnmark(String userInput) {
        try {
            int unmark = Integer.parseInt(userInput.substring(7));
            list.get(unmark - 1).markFalse();
            String markUndone = "____________________________________________________________\n"
                    + "OK! I've unredacted this:";
            System.out.println(markUndone);
            System.out.println(list.get(unmark - 1).toString());
            System.out.println("____________________________________________________________");
        }
        // Add to list
        catch (Exception e) {
            printError("Invalid unmark command! Use: unmark <task number>");
        }
    }

    private static void handleDelete(String userInput) {
        try {
            int taskNum = Integer.parseInt(userInput.substring(7).trim());
            if (taskNum < 1 || taskNum > list.size()) {
                printError("Invalid Epstein file page!");
                return;
            }
            Task removedTask = list.remove(taskNum - 1);  // remove() returns the removed item
            System.out.println("____________________________________________________________");
            System.out.println("Noted. I've removed this file:");
            System.out.println("  " + removedTask.toString());
            System.out.println("Now you have " + list.size() + " files in the list.");
            System.out.println("____________________________________________________________");
        } catch (Exception e) {
            printError("Invalid delete command! Use: delete <task number>");
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
            printError("Epstein todo description cannot be empty!");
        } else if (userInput.startsWith("deadline")) {
            printError("Invalid kirk deadline format! Use: deadline <task> /by <date>");
        } else if (userInput.startsWith("event")) {
            printError("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
        } else {
            printError("That can't be part of the Epstein files diddy blud! It has to start with todo, deadline, or event");
        }
    }

    private static void handleTodo(String userInput) {
        String description = userInput.substring(5).trim();
        if (description.isEmpty()) {
            printError("Epstein todo description cannot be empty!");
            return;
        }
        Task task = new Todo(description);
        addTask(task);
    }

    private static void handleDeadline(String userInput) {
        try {
            String[] parts = userInput.substring(9).split(" /by ");
            if (parts.length != 2) {
                printError("Invalid kirk deadline format! Use: deadline <task> /by <date>");
                return;
            }
            Task task = new Deadline(parts[0].trim(), parts[1].trim());
            addTask(task);
        } catch (Exception e) {
            printError("Invalid kirk deadline format!");
        }
    }

    private static void handleEvent(String userInput) {
        try {
            String remaining = userInput.substring(6);
            String[] parts1 = remaining.split(" /from ");
            if (parts1.length != 2) {
                printError("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
                return;
            }
            String[] parts2 = parts1[1].split(" /to ");
            if (parts2.length != 2) {
                printError("Invalid diddy party format! Use: event <task> /from <start> /to <end>");
                return;
            }
            Task task = new Event(parts1[0].trim(), parts2[0].trim(), parts2[1].trim());
            addTask(task);
        } catch (Exception e) {
            printError("Invalid diddy party format!");
        }
    }

    private static void addTask(Task task) {
        list.add(task);
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void printError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println("OOPS!!! " + message);
        System.out.println("____________________________________________________________");
    }
}
