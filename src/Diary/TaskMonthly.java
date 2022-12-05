package Diary;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskMonthly extends Task{

    public TaskMonthly(String title, String description, TaskType taskType, LocalDateTime taskDatetime, Repeatability repeatability) {
        super(title, description, taskType, taskDatetime, repeatability);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getTaskDatetime().toLocalDate();
        return localDate.equals(taskDate) ||
                (localDate.isAfter(taskDate) && localDate.getDayOfMonth() == taskDate.getDayOfMonth());
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.MONTHLY;
    }
}
