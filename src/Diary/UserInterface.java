package Diary;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;

public class UserInterface {

    private static final DateTimeFormatter DATA_FORMATTED = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMATTED = DateTimeFormatter.ofPattern("HH:mm");

    public static void addTusk(Scanner scanner) {
        String title = scanner.next("Выберите");
        String description = scanner.next();
        TaskType taskType = readType(scanner);
        LocalDateTime taskDatetime = readDateTime(scanner);
        Repeatability repeatability = readRepeatability();
        Task task = switch (repeatability) {
            case ONCE -> new TaskOnce(title, description, taskType, taskDatetime, Repeatability.ONCE);
            case DAILY -> new TaskDaily(title, description, taskType, taskDatetime, Repeatability.DAILY);
            case WEEKLY -> new TaskWeekly(title, description, taskType, taskDatetime, Repeatability.WEEKLY);
            case MONTHLY -> new TaskMonthly(title, description, taskType, taskDatetime, Repeatability.MONTHLY);
            case YEARLY -> new TaskYearly(title, description, taskType, taskDatetime, Repeatability.YEARLY);
        };
    }

    private static Repeatability readRepeatability() {
        while (true) {
            try {
                System.out.println("Тип задачи:");
                for (TaskType taskType : TaskType.values()) {
                    System.out.println(taskType.ordinal() + " " + localizeTaskType(taskType));
                }
                System.out.println("Выберите тип задачи:");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return TaskType.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Выбран не верный тип задачи!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден!");
            }
        }
    }

    private static TaskType readType(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Тип задачи:");
                for (TaskType taskType : TaskType.values()) {
                    System.out.println(taskType.ordinal() + " " + localizeTaskType(taskType));
                }
                System.out.println("Выберите тип задачи:");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return TaskType.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Выбран не верный тип задачи!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден!");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        LocalTime localTime = readTime(scanner);
        return localDate.atTime(localTime);

    }

    public static void printAllTask(Scanner scanner) {

    }
    public static void printTaskFotDate(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        Collection<Task> taskFotDate = Schedule.getTuskForData(localDate);
        System.out.println("Задачи на : " + localDate.format(DATA_FORMATTED));
        for (Task task : taskFotDate) {
            System.out.printf("[%s] [%s] || Время: %s || %s - %s %n",
                    localizeTaskType(task.getTaskType()),
                    localizeRepeatability(task.getRepeatabilityType()),
                    task.getTaskDatetime().format(TIME_FORMATTED),
                    task.getTitle(),
                    task.getDescription()
            );
        }

    }

    public static void removeTask(Scanner scanner) {
        System.out.println("Все задачи: ");
        for (Task task : Schedule.allTasks()) {
            System.out.printf("[ %s ] [%s] [%s] || Время: %s || %s - %s %n",
                    task.getId(),
                    localizeTaskType(task.getTaskType()),
                    localizeRepeatability(task.getRepeatabilityType()),
                    task.getTaskDatetime().format(TIME_FORMATTED),
                    task.getTitle(),
                    task.getDescription());
        }
        while (true) {
            try {
                System.out.print("Выберите номер задачи для удаления: ");
                String idLines = scanner.nextLine();
                int id = Integer.parseInt(idLines);
                Schedule.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер");
            } catch (IllegalStateException e) {
                System.out.println("Задача не найдена");
            }
        }
        System.out.println("Задача удалена");

    }

    private static LocalDate readDate(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите дату задачи: ");
                String dateLane = scanner.nextLine();
                return LocalDate.parse(dateLane, DATA_FORMATTED);
            } catch (DateTimeException e) {
                System.out.println("Введен не верный формат даты!");
            }
        }
    }
    private static LocalTime readTime(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите время задачи: ");
                String dateLane = scanner.nextLine();
                return LocalTime.parse(dateLane, TIME_FORMATTED);
            } catch (DateTimeException e) {
                System.out.println("Введен не верный формат времени!");
            }
        }
    }

    private static String localizeTaskType(TaskType taskType) {
        return switch (taskType) {
            case PERSONAL -> "Персональная задача";
            case WORKING -> "Рабочая задача";
        };
    }

    private static String localizeRepeatability(Repeatability repeatability) {
        return switch (repeatability) {
            case ONCE -> "Однократно";
            case DAILY -> "Ежедневно";
            case WEEKLY -> "Еженедельно";
            case MONTHLY -> "Ежемесячно";
            case YEARLY -> "Ежегодно";
        };
    }

}
