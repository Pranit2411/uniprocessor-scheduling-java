package schedules.spnScheduling;

import runner.Runner;
import tasks.Task;
import java.util.concurrent.BlockingQueue;

public class SpnScheduler extends schedules.AbstractScheduler<Task> {

    private final SpnTasksContainer spnTasksContainer;
    protected final Runner runner;

    public SpnScheduler(BlockingQueue<Task> taskArrivalQueue) {
        super(taskArrivalQueue);
        spnTasksContainer = new SpnTasksContainer();
        new Thread(() -> transferTasksFromArrivalToExecutionQueue(spnTasksContainer)).start();
        runner = new Runner();
    }

    @Override
    public void run() {
        runSchedule();
    }

    @Override
    public void runSchedule() {
        while (true) {
            if (allTasksScheduled && !spnTasksContainer.hasTasksAvailable()) {
                System.out.println("Scheduler exhausted");
                return;
            }
            for (Task task : spnTasksContainer) {
                System.out.println("received task");
                runner.runTask(task);
                task.setFinishTime();
            }
        }
    }
}
