package schedules.hrrnScheduling;

import schedules.AbstractScheduler;
import tasks.Task;


import java.util.concurrent.BlockingQueue;


import runner.Runner;

public class HrrnScheduler extends AbstractScheduler<Task> {

    private final HrrnTasksContainer hrrnTasksContainer;
    protected final Runner runner;
    public HrrnScheduler(BlockingQueue<Task> taskArrivalQueue) {
        super(taskArrivalQueue);
        
        hrrnTasksContainer = new HrrnTasksContainer();
        new Thread(() -> transferTasksFromArrivalToExecutionQueue(hrrnTasksContainer)).start();
        runner = new Runner();
    }

    @Override
    public void runSchedule() {
        while (true) {
            if (allTasksScheduled && !hrrnTasksContainer.hasTasksAvailable()) {
                System.out.println("Scheduler exhausted");
                return;
            }

            Task nextTask = hrrnTasksContainer.getNextTask();
            if (nextTask != null) {
                System.out.println("Running HRRN scheduled task: " + nextTask.getId());
                runner.runTask(nextTask);
                nextTask.setFinishTime();
            }
        }
    }

    @Override
    public void run() {
        runSchedule();
    }
}
