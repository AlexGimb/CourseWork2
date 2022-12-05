package Diary;

import java.time.LocalDate;
import java.util.*;

public class Schedule {
    private static final Map<Integer, Task> taskMap = new HashMap<>();

    public static Collection<Task> getTuskForData(LocalDate date) {
        TreeSet<Task>taskForData = new TreeSet<>(Comparator.comparing(Task::getTaskDatetime));
        for (Task task : taskMap.values()) {
            if (task.appearsIn(date)) {
                taskForData.add(task);
            }
        }
        return taskForData;
    }

    public static Collection<Task> allTasks() {
        return taskMap.values();
    }
    public static void addTusk(Task task) {
        taskMap.put(task.getId(), task);
    }

    public static void removeTask(int id) {
        if (taskMap.containsKey(id)){
            taskMap.remove(id);
        }else {
            throw new IllegalStateException();
        }
    }
}
