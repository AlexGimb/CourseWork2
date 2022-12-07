package Diary;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task implements TaskInterface{
    private String title;
    private String description;
    private TaskType taskType;
    private LocalDateTime taskDatetime;
    private Repeatability repeatability;
    private final int id;
    private static int counter = 0;


    public Task(String title, String description, TaskType taskType,
                LocalDateTime taskDatetime, Repeatability repeatability) {
        this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.taskDatetime = taskDatetime;
        this.repeatability = repeatability;
        this.id = counter++;

    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public LocalDateTime getTaskDatetime() {
        return taskDatetime;
    }

    public void setTaskDatetime(LocalDateTime taskDatetime) {
        this.taskDatetime = taskDatetime;
    }

    public Repeatability getRepeatability() {
        return repeatability;
    }

    public void setRepeatability(Repeatability repeatability) {
        this.repeatability = repeatability;
    }

    public int getId() {
        return id;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Task.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                taskType == task.taskType &&
                Objects.equals(taskDatetime, task.taskDatetime) &&
                repeatability == task.repeatability;
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, description, taskType, taskDatetime, repeatability, id);
    }
}