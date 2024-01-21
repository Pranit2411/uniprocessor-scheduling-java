package tasks;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class SimpleTaskFactory {
    private static final Random random = new Random();
    private static int taskId = 1;

    public static List<Task> createTasks(int numberOfTasks) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            tasks.add(new SimpleTask(taskId++, generateRandomDuration()));
        }
        return tasks;
    }

    private static int generateRandomDuration() {
        int[] shortTerm = {300, 700}; // 300ms to 700ms
        int[] mediumTerm = {5000, 7000}; // 5s to 7s
        int[] longTerm = {13000, 15000}; // 13s to 15s

        // Randomly select a term
        return switch (random.nextInt(3)) {
            case 0 -> // Short term
                    random.nextInt(shortTerm[1] - shortTerm[0] + 1) + shortTerm[0];
            case 1 -> // Medium term
                    random.nextInt(mediumTerm[1] - mediumTerm[0] + 1) + mediumTerm[0];
            case 2 -> // Long term
                    random.nextInt(longTerm[1] - longTerm[0] + 1) + longTerm[0];
            default -> throw new IllegalStateException("Unexpected value");
        };
    }

    public static Task createSentinelTask() {
        return new SimpleTask(-1, 0);
    }
}
