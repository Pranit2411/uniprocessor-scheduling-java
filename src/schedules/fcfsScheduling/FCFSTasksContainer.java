package schedules.fcfsScheduling;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.*;
import schedules.SchedulingTasksContainer;
import tasks.Task;

public class FCFSTasksContainer implements SchedulingTasksContainer<Task> {
    private final BlockingQueue<Task> TaskQueue = new LinkedBlockingQueue<>();

    public void addTask(Task task) throws InterruptedException 
    {
        this.TaskQueue.put(task);
    }

    public boolean hasTasksAvailable()
    {
        return !TaskQueue.isEmpty();
    }
    @Override
    public Iterator<Task> iterator()
    {
        return new FCFSIterator(this);
    }

    class FCFSIterator implements Iterator<Task>
    {
        private final FCFSTasksContainer tasks;

        public FCFSIterator(FCFSTasksContainer tasks)
        {
            this.tasks = tasks;
        }

        @Override
        public boolean hasNext()
        {
            return !this.tasks.TaskQueue.isEmpty();
        }
        @Override
        public Task next()
        {
            return TaskQueue.poll();
        }
    }
}
