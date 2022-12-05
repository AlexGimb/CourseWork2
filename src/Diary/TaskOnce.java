package Diary;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskOnce extends Task {

    public TaskOnce(String title, String description, TaskType taskType, LocalDateTime taskDatetime, Repeatability repeatability) {
        super(title, description, taskType, taskDatetime, repeatability);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return localDate.equals(this.getTaskDatetime().toLocalDate());
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.ONCE;
    }
}
