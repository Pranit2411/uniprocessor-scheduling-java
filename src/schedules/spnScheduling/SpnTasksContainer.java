package schedules.spnScheduling;

import schedules.SchedulingTasksContainer;
import tasks.Task;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class SpnTasksContainer implements SchedulingTasksContainer<Task> {
    private final BlockingQueue<Task> taskExecutionQueue = new PriorityBlockingQueue<>(
            10,
            Comparator.comparingInt(Task::getRemainingTime)
    );

    public void addTask(Task task) throws InterruptedException {
        this.taskExecutionQueue.put(task);
    }

    public boolean hasTasksAvailable() {
        return !taskExecutionQueue.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return new SpnIterator(this);
    }

    class SpnIterator implements Iterator<Task> {

        private final SpnTasksContainer tasks;

        public SpnIterator(SpnTasksContainer tasks) {
            this.tasks = tasks;
        }

        @Override
        public boolean hasNext() {
            return !this.tasks.taskExecutionQueue.isEmpty();
        }

        @Override
        public Task next() {
            return taskExecutionQueue.poll();
        }
    }

}
