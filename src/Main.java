import schedules.fcfsScheduling.FCFSScheduler;
import schedules.feedbackScheduling.FeedbackScheduler;
import schedules.hrrnScheduling.HrrnScheduler;
import tasks.IntervalTaskInjector;
import tasks.Task;
import tasks.SimpleTaskFactory;
import schedules.spnScheduling.SpnScheduler;
import util.PerfCalculator;
import schedules.rrScheduling.RRScheduler;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        String selectedSchedule = args.length > 0 ? args[0] : "";
        switch (selectedSchedule) {
            case "1":
                runSpnSchedule();
                break;
            case "2":
                runFeedbackSchedule();
                break;
            case "3":
                runHrrnSchedule();
                break;
            case "4":
                runFcfsSchedule();
                break;
            case "5":
                runRRSchedule();
                break;
            default:
                System.out.println("Running all schedules");
                runSpnSchedule();
                runFeedbackSchedule();
                runHrrnSchedule();
                runFcfsSchedule();
                runRRSchedule();
        }
    }

    private static void runSpnSchedule() {
        System.out.println("-----------------SPN Scheduling-----------------");
        PreparedSchedulingItems preparedSchedulingItems = prepareSchedule();

        SpnScheduler spnScheduler = new SpnScheduler(preparedSchedulingItems.taskArrivalQueue);
        Thread scheduler = new Thread(spnScheduler);

        runAlgorithm(scheduler, preparedSchedulingItems.producerThread);

        summarize(preparedSchedulingItems.tasks);
        System.out.println("-----------------END-----------------");
    }

    private static void runFeedbackSchedule() {
        System.out.println("-----------------Feedback Scheduling-----------------");
        PreparedSchedulingItems preparedSchedulingItems = prepareSchedule();

        FeedbackScheduler feedbackScheduler = new FeedbackScheduler(preparedSchedulingItems.taskArrivalQueue);
        Thread scheduler = new Thread(feedbackScheduler);

        runAlgorithm(scheduler, preparedSchedulingItems.producerThread);

        summarize(preparedSchedulingItems.tasks);
        System.out.println("-----------------END-----------------");
    }

    private static void runHrrnSchedule() {
        System.out.println("-----------------HRRN Scheduling-----------------");
        PreparedSchedulingItems preparedSchedulingItems = prepareSchedule();

        HrrnScheduler hrrnScheduler = new HrrnScheduler(preparedSchedulingItems.taskArrivalQueue);
        Thread scheduler = new Thread(hrrnScheduler);

        runAlgorithm(scheduler, preparedSchedulingItems.producerThread);

        summarize(preparedSchedulingItems.tasks);
        System.out.println("-----------------END-----------------");
    }

    private static void runFcfsSchedule() {
        System.out.println("-----------------FCFS Scheduling-----------------");
        PreparedSchedulingItems preparedSchedulingItems = prepareSchedule();

        FCFSScheduler fcfsScheduler = new FCFSScheduler(preparedSchedulingItems.taskArrivalQueue);
        Thread scheduler = new Thread(fcfsScheduler);

        runAlgorithm(scheduler, preparedSchedulingItems.producerThread);

        summarize(preparedSchedulingItems.tasks);
        System.out.println("-----------------END-----------------");
    }
    private static void runRRSchedule (){
        System.out.println("-----------------RR Scheduling-----------------");
        PreparedSchedulingItems preparedSchedulingItems = prepareSchedule();

        RRScheduler rrScheduler = new RRScheduler(preparedSchedulingItems.taskArrivalQueue);
        Thread scheduler = new Thread(rrScheduler);

        runAlgorithm(scheduler, preparedSchedulingItems.producerThread);

        summarize(preparedSchedulingItems.tasks);
        System.out.println("-----------------END-----------------");
    }

    private static void summarize(List<Task> tasks) {
        System.out.println("-----------------SUMMARY-----------------");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("Average Response Time " + PerfCalculator.getAverageResponseTime(tasks) + " ms");
        System.out.println("Total Execution Time " + PerfCalculator.getTotalExecutionTime(tasks) + " ms");
        System.out.println("Average Turn-around Time " + PerfCalculator.getAverageTurnAroundTime(tasks) + " ms");
        System.out.println("Average Wait Time " + PerfCalculator.getAverageWaitTime(tasks) + " ms");
    }

    private static PreparedSchedulingItems prepareSchedule() {
        List<Task> tasks = SimpleTaskFactory.createTasks(10);
        BlockingQueue<Task> taskArrivalQueue = new LinkedBlockingQueue<>();
        IntervalTaskInjector injector = new IntervalTaskInjector(taskArrivalQueue, tasks);
        Thread producerThread = new Thread(injector);
        return new PreparedSchedulingItems(tasks, taskArrivalQueue, producerThread);
    }

    private static void runAlgorithm(Thread scheduler, Thread producerThread) {
        scheduler.start();
        System.out.println("Starting injector");
        producerThread.start();
        try {
            producerThread.join();
            scheduler.join();
        } catch (InterruptedException ignored) {}
    }

    private record PreparedSchedulingItems(List<Task> tasks, BlockingQueue<Task> taskArrivalQueue, Thread producerThread) {}

}
