package schedules.rrScheduling;

import schedules.SchedulingTasksContainer;
import tasks.Task;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class RRTasksContainer implements SchedulingTasksContainer<Task> {
    private final BlockingQueue<Task> taskExecutionQueue = new ArrayBlockingQueue<>(10);

    public void addTask(Task task) throws InterruptedException {
        this.taskExecutionQueue.put(task);
    }

    public boolean hasTasksAvailable() {
        return !taskExecutionQueue.isEmpty();
    }

    public void removeTask(Task task) {
        this.taskExecutionQueue.remove(task);
    }

    @Override
    public Iterator<Task> iterator() {
        return new RRIterator(this);
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    class RRIterator implements Iterator<Task> {

        private final RRTasksContainer tasks;


        public RRIterator(RRTasksContainer tasks) {
            this.tasks = tasks;
        }

        @Override
        public boolean hasNext() {
            return !this.tasks.taskExecutionQueue.isEmpty();
        }

        @Override
        public Task next() {
            Task task = this.tasks.taskExecutionQueue.poll();
            try {
                if (task != null) {
                    this.tasks.taskExecutionQueue.put(task);
                }
            } catch (InterruptedException ignored) {}
            return task;
        }
    }

}
