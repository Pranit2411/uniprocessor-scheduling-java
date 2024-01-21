package schedules.feedbackScheduling;

import runner.Runner;
import schedules.AbstractScheduler;
import tasks.Task;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class FeedbackScheduler extends AbstractScheduler<BlockingQueue<Task>> {

    private final FeedbackTasksContainer feedbackTasksContainer;
    protected final Runner runner;
    public FeedbackScheduler(BlockingQueue<Task> taskArrivalQueue) {
        super(taskArrivalQueue);
        feedbackTasksContainer = new FeedbackTasksContainer();
        new Thread(() -> transferTasksFromArrivalToExecutionQueue(feedbackTasksContainer)).start();
        runner = new Runner();
    }

    @Override
    public void run() {
        runSchedule();
    }

    @Override
    public void runSchedule() {
        while (true) {
            if (allTasksScheduled && !feedbackTasksContainer.hasTasksAvailable()) {
                System.out.println("Scheduling complete");
                return;
            }
            for (BlockingQueue<Task> queue : feedbackTasksContainer) {
                System.out.println("Priority level " + feedbackTasksContainer.getQueuePriorityLevel(queue));
                if (queue.isEmpty()) {
                    System.out.println("Skipped - Queue empty");
                }
                for (Task task : queue) {
                    runner.runTask(task, getTimeSliceByPriority(feedbackTasksContainer.getQueuePriorityLevel(queue)));
                    Random random = new Random();
                    task.getArrivalTime();
                    try {
                        if (task.getRemainingTime() == 0) {
                            feedbackTasksContainer.removeTask(queue, task);
                            task.setFinishTime();
                        } else if (random.nextInt(6) == 5) { // Simulate task making an IO request and being promoted
                            System.out.println("Task " + task.getId() + " promoted to higher priority");
                            feedbackTasksContainer.promote();
                        } else {
                            System.out.println("Task " + task.getId() + " demoted to lower priority");
                            feedbackTasksContainer.demote();
                        }
                    } catch (InterruptedException ignored) {}
                }
            }
        }
    }

    private static int getTimeSliceByPriority(int priority) {
        return switch (priority) {
            case 1 -> 500;
            case 2 -> 1000;
            case 3 -> 2000;
            case 4 -> 4000;
            default -> 0;
        };
    }
}
