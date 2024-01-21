package runner;

import tasks.Task;

public class Runner {
    private int timeSlice;
    private final boolean isTimeSliced;

    public Runner() {
        this.isTimeSliced = false;
    }

    public Runner(int timeSlice) {
        this.timeSlice = timeSlice;
        this.isTimeSliced = true;
    }

    public void runTask(Task task) {
        try {
            task.setStartTime();
            task.run();
            int runningTime = isTimeSliced ? Math.min(task.getRemainingTime(), timeSlice) : task.getRemainingTime();
            Thread.sleep(runningTime);
            task.setRemainingTime(task.getRemainingTime() - runningTime);
        } catch (InterruptedException ignored) {}
    }

    public void runTask(Task task, int timeSlice) {
        try {
            task.setStartTime();
            task.run();
            int runningTime = Math.min(task.getRemainingTime(), timeSlice);
            Thread.sleep(runningTime);
            task.setRemainingTime(task.getRemainingTime() - runningTime);
        } catch (InterruptedException ignored) {}
    }
}
