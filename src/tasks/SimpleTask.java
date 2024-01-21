package tasks;

import java.time.Duration;
import java.time.Instant;

public class SimpleTask implements Task {

    private final int id;
    private int duration;
    private final int totalTime;
    private Instant arrivalTime;
    private Instant startTime;
    private Instant finishTime;

    private final Instant creationTime;

    public SimpleTask(int id, int duration) {
        this.id = id;
        this.totalTime = this.duration = duration;
        this.creationTime = Instant.now();
    }

    @Override
    public void run() {
        System.out.println("Running task: " + id + " - total time: " + totalTime + " - remaining time: " + duration);
    }

    @Override
    public int getRemainingTime() {
        return duration;
    }


    @Override
    public void setRemainingTime(int duration) {
        this.duration = duration;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getTotalTime() {
        return totalTime;
    }

    @Override
    public void setStartTime() {
        if (startTime == null) {
            this.startTime = Instant.now();
        }
    }

    @Override
    public void setArrivalTime() {
        this.arrivalTime = Instant.now();
    }

    @Override
    public void setFinishTime() {
        this.finishTime = Instant.now();
    }

    @Override
    public Duration getResponseTime() {
        return Duration.between(this.arrivalTime, this.startTime);
    }

    @Override
    public Duration getTurnAroundTime() {
        return Duration.between(this.arrivalTime, this.finishTime);
    }

    @Override
    public Duration getWaitTime() {
        return getTurnAroundTime().minusMillis(totalTime);
    }

    @Override
    public String toString() {
        return String.format(
                "Task ID: %d\tStart time: %dms\tFinish time: %dms\tExecution time: %dms\tWait time: %dms",
                getId(),
                Duration.between(arrivalTime, startTime).toMillis(),
                getTurnAroundTime().toMillis(),
                totalTime,
                getWaitTime().toMillis()
        );
    }

    @Override
    public Instant getArrivalTime() {
        return this.arrivalTime;
    }
}
