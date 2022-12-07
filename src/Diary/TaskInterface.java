package Diary;

import java.time.LocalDate;

public interface TaskInterface  {
    boolean appearsIn(LocalDate localDate);

    Repeatability getRepeatabilityType();
}
