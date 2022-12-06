package Diary;

import java.time.LocalDate;
import java.util.*;

public class Schedule {
    private Map<Integer, Task> taskMap = new HashMap<>();
    static Schedule schedule = new Schedule();

    public  Collection<Task> getTaskForData(LocalDate date) {
        TreeSet<Task>taskForData = new TreeSet<>(Comparator.comparing(Task::getTaskDatetime));
        for (Task task : taskMap.values()) {
            if (task.appearsIn(date)) {
                taskForData.add(task);
            }
        }
        return taskForData;
    }

    public  Collection<Task> allTasks() {
        return taskMap.values();
    }
    public  void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void changeTask(int id, String title, String description) {
        if (schedule.taskMap.containsKey(id)) {
            Task task = schedule.taskMap.get(id);
            schedule.taskMap.replace(id, task.setTitle(title), task.setDescription(description));
        } else {
            throw new IllegalStateException();
        }
    }
    public  void search (int id) {
        if (taskMap.containsKey(id)){
        }else {
            throw new IllegalStateException();
        }
    }
    public  void removeTask (int id) {
        if (taskMap.containsKey(id)) {
            taskMap.remove(id);
        } else {
            throw new IllegalStateException();
        }
    }
}
