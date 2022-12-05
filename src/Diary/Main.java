package Diary;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Schedule.addTusk(new TaskOnce("Прогулка", "Погулять с псами", TaskType.PERSONAL, LocalDateTime.now().plusHours(5), Repeatability.ONCE));
        Schedule.addTusk(new TaskDaily("Работа", "Поработать", TaskType.WORKING, LocalDateTime.now(), Repeatability.DAILY));
        Schedule.addTusk(new TaskWeekly("Пьянка", "Попить пивка", TaskType.PERSONAL, LocalDateTime.now().plusHours(1), Repeatability.WEEKLY));
        Schedule.addTusk(new TaskMonthly("Игры", "Поиграть", TaskType.PERSONAL, LocalDateTime.now().plusHours(2), Repeatability.MONTHLY));
        Schedule.addTusk(new TaskYearly("Туса", "Потусить", TaskType.PERSONAL, LocalDateTime.now().plusHours(3), Repeatability.YEARLY));

        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

        UserInterface.mainMenu(scanner);
    }
}