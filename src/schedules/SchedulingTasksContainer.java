package schedules;

import tasks.Task;

public interface SchedulingTasksContainer<T> extends Iterable<T> {
    public void addTask(Task task) throws InterruptedException;
    public boolean hasTasksAvailable();
}
