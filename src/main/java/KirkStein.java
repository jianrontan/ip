import java.util.Scanner;

public class KirkStein {
    private static Task[] list = new Task[100];
    private static int index = 0;

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
        for (int i = 0; i < index; i++) {
            System.out.println(i + 1 + "." + list[i].toString());
        }
        System.out.println("____________________________________________________________");
    }

    private static void handleMark(String userInput) {
        try {
            int mark = Integer.parseInt(userInput.substring(5));
            list[mark - 1].markTrue();
            String markDone = "____________________________________________________________\n"
                    + "Nice! I've marked this as redacted:";
            System.out.println(markDone);
            System.out.println(list[mark - 1].toString());
            System.out.println("____________________________________________________________");
        }
        // Add to list
        catch (Exception e) {
            handleAdd(userInput);
        }
    }

    private static void handleUnmark(String userInput) {
        try {
            int unmark = Integer.parseInt(userInput.substring(7));
            list[unmark - 1].markFalse();
            String markUndone = "____________________________________________________________\n"
                    + "OK! I've unredacted this:";
            System.out.println(markUndone);
            System.out.println(list[unmark - 1].toString());
            System.out.println("____________________________________________________________");
        }
        // Add to list
        catch (Exception e) {
            handleAdd(userInput);
        }
    }

    private static void handleAdd(String userInput) {
        if (userInput.startsWith("todo ")) {
            handleTodo(userInput);
        } else if (userInput.startsWith("deadline ")) {
            handleDeadline(userInput);
        } else if (userInput.startsWith("event ")) {
            handleEvent(userInput);
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
        list[index] = task;
        index++;
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + index + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    private static void printError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println("OOPS!!! " + message);
        System.out.println("____________________________________________________________");
    }
}
