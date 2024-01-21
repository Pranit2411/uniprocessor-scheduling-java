package schedules.hrrnScheduling;

import schedules.SchedulingTasksContainer;
import tasks.Task;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HrrnTasksContainer implements  SchedulingTasksContainer<Task> {

    private final BlockingQueue<Task> taskExecutionQueue = new LinkedBlockingQueue<>();

    public void addTask(Task task) throws InterruptedException {
        this.taskExecutionQueue.put(task);
    }

    public boolean hasTasksAvailable() {
        return !taskExecutionQueue.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return new HrrnIterator(this);
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    class HrrnIterator implements Iterator<Task> {

        private final HrrnTasksContainer tasks;

        public HrrnIterator(HrrnTasksContainer tasks) {
            this.tasks = tasks;
        }

        @Override
        public boolean hasNext() {
            return !this.tasks.taskExecutionQueue.isEmpty();
        }

        @Override
        public Task next() {
            return  tasks.taskExecutionQueue.poll();
        }
    }

    private double calculateResponseRatio(Task task) {
        long waitingTime = Duration.between(task.getArrivalTime(), Instant.now()).toMillis();
        return (waitingTime + task.getRemainingTime()) / (double) task.getRemainingTime();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Task getNextTask() {
        Task nextTask = null;
        double highestResponseRatio = Double.MIN_VALUE;

        for (Task task : taskExecutionQueue) {
            double responseRatio = calculateResponseRatio(task);
            if (responseRatio > highestResponseRatio) {
                highestResponseRatio = responseRatio;
                nextTask = task;
            }
        }

        if (nextTask != null) {
            taskExecutionQueue.remove(nextTask);
        }

        return nextTask;
    }
}
