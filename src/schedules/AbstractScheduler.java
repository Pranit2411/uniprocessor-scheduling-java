package schedules;

import tasks.Task;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractScheduler<T> implements Runnable {
    protected final BlockingQueue<Task> taskArrivalQueue;
    protected boolean allTasksScheduled = false;

    public AbstractScheduler(BlockingQueue<Task> taskArrivalQueue) {
        this.taskArrivalQueue = taskArrivalQueue;
    }

    @Override
    public abstract void run();

    protected void transferTasksFromArrivalToExecutionQueue(SchedulingTasksContainer<T> tasksContainer) {
        try {
            while (true) {
                Task task = taskArrivalQueue.take();
                if (task.getId() == -1) { // sentinel task found. this signals the end of task arrival.
                    System.out.println("All tasks accepted");
                    allTasksScheduled = true;
                    return;
                }
                task.setArrivalTime();
                tasksContainer.addTask(task);
                System.out.println("Scheduling task: " + task.getId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public abstract void runSchedule();
}
