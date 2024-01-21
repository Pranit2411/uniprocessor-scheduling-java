package schedules.rrScheduling;

import runner.Runner;
import tasks.Task;

import java.util.concurrent.BlockingQueue;

public class RRScheduler extends schedules.AbstractScheduler<Task> {

    private final RRTasksContainer rrTasksContainer;
    protected final Runner runner;

    public RRScheduler(BlockingQueue<Task> taskArrivalQueue) {
        super(taskArrivalQueue);
        rrTasksContainer = new RRTasksContainer();
        new Thread(() -> transferTasksFromArrivalToExecutionQueue(rrTasksContainer)).start();
        runner = new Runner(300);
    }

    @Override
    public void run() {
        runSchedule();
    }

    @Override
    public void runSchedule() {
        while (true) {
            if (allTasksScheduled && !rrTasksContainer.hasTasksAvailable()) {
                System.out.println("Scheduler exhausted");
                return;
            }
            for (Task task : rrTasksContainer) {
                System.out.println("received task");
                runner.runTask(task);
                if (task.getRemainingTime() == 0) {
                    task.setFinishTime();
                    rrTasksContainer.removeTask(task);
                }
            }
        }
    }
}
