package Diary;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Schedule.addTusk(new TaskOnce("Прогулка", "Погулять с псами", TaskType.PERSONAL, LocalDateTime.now().plusHours(9), Repeatability.ONCE));
        Schedule.addTusk(new TaskDaily("Прогулка", "Погулять с псами", TaskType.PERSONAL, LocalDateTime.now().plusHours(3), Repeatability.DAILY));
        Schedule.addTusk(new TaskWeekly("Прогулка", "Погулять с псами", TaskType.PERSONAL, LocalDateTime.now().plusHours(2), Repeatability.WEEKLY));
        Schedule.addTusk(new TaskMonthly("Прогулка", "Погулять с псами", TaskType.PERSONAL, LocalDateTime.now().plusHours(6), Repeatability.MONTHLY));
        Schedule.addTusk(new TaskYearly("Прогулка", "Погулять с псами", TaskType.PERSONAL, LocalDateTime.now().plusHours(1), Repeatability.YEARLY));

//        UserInterface.printTaskFotDate(scanner);

        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

        UserInterface.removeTask(scanner);












    }
}