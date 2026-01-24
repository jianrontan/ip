import java.util.Scanner;

public class KirkStein {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Initial logo
        String logo = "____________________________________________________________\n"
                + "Hello! I'm KirkStein\n"
                + "Welcome to my island!\n"
                + "____________________________________________________________";
        System.out.println(logo);

        // Initial input
        String userInput = "";
        Task[] list = new Task[100];
        int index = 0;

        // Loop
        while (!userInput.equals("bye")) {
            // Get input
            userInput = input.nextLine();

            // End loop
            if (userInput.equals("bye")) {
                String endLoop = "____________________________________________________________\n"
                        + "Bye! See you in the files.\n"
                        + "____________________________________________________________";
                System.out.println(endLoop);
            }

            // Display list
            else if (userInput.equals("list")) {
                String printList = "____________________________________________________________\n"
                        + "Here are your Epstein files:";
                System.out.println(printList);
                for (int i = 0; i < index; i++) {
                    System.out.println(i + 1 + "." + list[i].printTask());
                }
                System.out.println("____________________________________________________________");
            }

            // Mark item
            else if (userInput.startsWith("mark")) {
                try {
                    int mark = Integer.parseInt(userInput.substring(5));
                    list[mark - 1] = list[mark - 1].markTrue();
                    String markDone = "____________________________________________________________\n"
                            + "Nice! I've marked this as redacted:";
                    System.out.println(markDone);
                    System.out.println(list[mark - 1].printTask());
                    System.out.println("____________________________________________________________");
                }

                // Add to list
                catch (Exception e) {
                    list[index] = new Task(userInput);
                    index++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Added to files: " + userInput);
                    System.out.println("____________________________________________________________");
                }
            }

            // Unmark item
            else if (userInput.startsWith("unmark")) {
                try {
                    int unmark = Integer.parseInt(userInput.substring(7));
                    list[unmark - 1] = list[unmark - 1].markFalse();
                    String markUndone = "____________________________________________________________\n"
                            + "OK! I've unredacted this:";
                    System.out.println(markUndone);
                    System.out.println(list[unmark - 1].printTask());
                    System.out.println("____________________________________________________________");
                }

                // Add to list
                catch (Exception e) {
                    list[index] = new Task(userInput);
                    index++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Added to files: " + userInput);
                    System.out.println("____________________________________________________________");
                }
            }

            // Add to list
            else {
                list[index] = new Task(userInput);
                index++;
                System.out.println("____________________________________________________________");
                System.out.println("Added to files: " + userInput);
                System.out.println("____________________________________________________________");
            }
        }
        input.close();
    }
}
