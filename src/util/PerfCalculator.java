package util;

import tasks.Task;

import java.util.List;

public class PerfCalculator {
    public static float getAverageResponseTime(List<Task> tasks) {
        float totalResponseTime = tasks.stream()
                .map(task -> task.getResponseTime().toMillis())
                .reduce(0L, Long::sum);
        return totalResponseTime / tasks.size();
    }

    public static int getTotalExecutionTime(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getTotalTime)
                .reduce(0, Integer::sum);
    }

    public static float getAverageTurnAroundTime(List<Task> tasks) {
        float totalTurnAroundTime = tasks.stream()
                .map(task -> task.getTurnAroundTime().toMillis())
                .reduce(0L, Long::sum);;
        return totalTurnAroundTime / tasks.size();
    }

    public static float getAverageWaitTime(List<Task> tasks) {
        float totalWaitTime = tasks.stream()
                .map(task -> task.getWaitTime().toMillis())
                .reduce(0L, Long::sum);
        return totalWaitTime / tasks.size();
    }
}
