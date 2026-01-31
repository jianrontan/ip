import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

class Storage {
    private final String filePath;

    Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTask(ArrayList<Task> tasks) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> loadTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char taskType = line.charAt(1);
                boolean marked = line.charAt(4) == 'X';
                String taskDescription = line.substring(7);
                if (taskType == 'T') {
                    Todo todo = new Todo(taskDescription);
                    if (marked) {
                        todo.markTrue();
                    }
                    tasks.add(todo);
                } else if (taskType == 'D') {
                    String[] parts = taskDescription.split(" \\(by: ");
                    String description = parts[0];
                    String by = parts[1].replace(")", "");
                    Deadline deadline = new Deadline(description, by);
                    if (marked) {
                        deadline.markTrue();
                    }
                    tasks.add(deadline);
                } else if (taskType == 'E') {
                    String[] parts = taskDescription.split(" \\(from: ");
                    String description = parts[0].trim();
                    String[] parts2 = parts[1].split(" to: ");
                    String from = parts2[0].trim();
                    String to = parts2[1].replace(")", "").trim();
                    Event event = new Event(description, from, to);
                    if (marked) {
                        event.markTrue();
                    }
                    tasks.add(event);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}