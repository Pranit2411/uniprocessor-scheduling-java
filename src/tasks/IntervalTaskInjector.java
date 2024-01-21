package tasks;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class IntervalTaskInjector implements Runnable {
    private final BlockingQueue<Task> taskArrivalQueue;
    private final List<Task> tasks;

    public IntervalTaskInjector(BlockingQueue<Task> taskArrivalQueue, List<Task> tasks) {
        this.taskArrivalQueue = taskArrivalQueue;
        this.tasks = tasks;
    }

    public void run() {
        try {
            for (Task task : tasks) {
                System.out.println("Adding task: " + task.getId() + " - Duration: " + task.getRemainingTime() + " ms");
                taskArrivalQueue.put(task);
                Thread.sleep(500); // add task to input queue at set intervals
            }
            taskArrivalQueue.put(SimpleTaskFactory.createSentinelTask());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
