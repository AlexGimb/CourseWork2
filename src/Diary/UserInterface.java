package Diary;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class UserInterface {

    private static final DateTimeFormatter DATA_FORMATTED = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMATTED = DateTimeFormatter.ofPattern("HH:mm");

    public static String menu(Scanner scanner) {
        System.out.println("Добро пожаловать в планировщик задач");
        while (true) {
            try {
                System.out.println("Пункт меню");
                for (Menu menu : Menu.values()) {
                    System.out.println(menu.ordinal() + " " + localizeMenu(menu));
                }
                System.out.print("Выберите пункт меню: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = parseInt(ordinalLine);
                return Integer.toString(Menu.values()[ordinal].ordinal());
            } catch (NumberFormatException e) {
                System.out.println("Выбран не верный номер меню");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Меню не найдено");
            }
        }
    }

    public static void mainMenu (Scanner scanner) {
        int number = Integer.parseInt((menu(scanner)));
        if (number == 0) {
            printAllTask(scanner);
        }if (number == 1) {
            printTaskFotDate(scanner);
        }if (number == 2) {
            changeTask(scanner);
        }if (number == 3) {
            addTusk(scanner);
        }if (number == 4) {
            removeTask(scanner);
        }if (number == 5) {
            mainMenu(scanner);
        }if (number == 6) {
            scanner.close();
        }
    }

    private static void menuInOut(Scanner scanner) {
        int number = Integer.parseInt((inOut(scanner)));;
        if (number == 5) {
            mainMenu(scanner);
        }
        if (number == 6) {
            scanner.close();
        }
    }

    private static String inOut(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Пункт меню");
                for (Menu menu : Menu.values()) {
                    if (menu.equals(Menu.SIX_MENU)||menu.equals(Menu.SEVEN_MENU)) {
                        System.out.println(menu.ordinal() + " " + localizeMenu(menu));
                    }
                }
                System.out.print("Выберите пункт меню: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = parseInt(ordinalLine);
                return Integer.toString(Menu.values()[ordinal].ordinal());
            } catch (NumberFormatException e) {
                System.out.println("Выбран не верный номер меню");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Меню не найдено");
            }
        }
    }
    public static void changeTask(Scanner scanner) {
        int id = parseInt(readId(scanner));
        String title = readString("Введите новое название задачи: ", scanner);
        String description = readString("Введите новое описание задачи: ", scanner);
        Schedule.changeTask(id, title, description);
        menuInOut(scanner);
    }

    private static String readId(Scanner scanner) {
        while (true) {
            try {
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
                System.out.print("Выберите номер задачи для редактирования: ");
                String idLines = scanner.nextLine();
                int id = parseInt(idLines);
                Schedule.search(id);
                return idLines;
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер");
            } catch (IllegalStateException e) {
                System.out.println("Задача не найдена");
            }
        }
    }

    public static void addTusk(Scanner scanner) {
        String title = readString("Введите название задачи: ", scanner);
        String description = readString("Введите описание задачи: ", scanner);
        TaskType taskType = readType(scanner);
        LocalDateTime taskDatetime = readDateTime(scanner);
        Repeatability repeatability = readRepeatability(scanner);
        Task task;
        switch (repeatability) {
            case ONCE:
                task = new TaskOnce(title, description, taskType, taskDatetime, Repeatability.ONCE);
                break;
            case DAILY:
                task = new TaskDaily(title, description, taskType, taskDatetime, Repeatability.DAILY);
                break;
            case WEEKLY:
                task = new TaskWeekly(title, description, taskType, taskDatetime, Repeatability.WEEKLY);
                break;
            case MONTHLY:
                task = new TaskMonthly(title, description, taskType, taskDatetime, Repeatability.MONTHLY);
                break;
            case YEARLY:
                task = new TaskYearly(title, description, taskType, taskDatetime, Repeatability.YEARLY);
                break;
            default:
            throw new IllegalArgumentException("Ой что то пошло не так");
        }
        Schedule.addTusk(task);
        System.out.println("Задача <<" + title + ">> добавлена");
        menuInOut(scanner);
    }
    private static String readString(String message, Scanner scanner) {
        while (true) {
            System.out.print(message);
            String readString = scanner.nextLine();
            if (readString == null || readString.isBlank()) {
                System.out.println("Заполните данные!");
            } else {
                return readString;
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
                System.out.print("Выберите тип задачи: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = parseInt(ordinalLine);
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
    private static Repeatability readRepeatability(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Тип повторяемости:");
                for (Repeatability repeatability : Repeatability.values()) {
                    System.out.println(repeatability.ordinal() + " " + localizeRepeatability(repeatability));
                }
                System.out.print("Выберите тип повторяемости: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = parseInt(ordinalLine);
                return Repeatability.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Выбран не верный тип повторяемости!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип повторяемости не найден!");
            }
        }
    }
    public static void printAllTask(Scanner scanner) {
        System.out.println("Все задачи: ");
        for (Task task : Schedule.allTasks()) {
            System.out.printf("[%s] [%s] || Время: %s || %s - %s %n",
                    localizeTaskType(task.getTaskType()),
                    localizeRepeatability(task.getRepeatabilityType()),
                    task.getTaskDatetime().format(TIME_FORMATTED),
                    task.getTitle(),
                    task.getDescription());
        }
        menuInOut(scanner);
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
        menuInOut(scanner);
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
                int id = parseInt(idLines);
                Schedule.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер");
            } catch (IllegalStateException e) {
                System.out.println("Задача не найдена");
            }
        }
        System.out.println("Задача удалена");
        menuInOut(scanner);
    }
    private static String localizeTaskType(TaskType taskType) {
        switch (taskType) {
            case PERSONAL:
                return "Персональная задача";
            case WORKING:
                return "Рабочая задача";
            default:
                throw new IllegalArgumentException("Ой что то пошло не так");
        }
    }
    private static String localizeRepeatability(Repeatability repeatability) {
        switch (repeatability) {
            case ONCE:
                return "Однократно";
            case DAILY:
                return "Ежедневно";
            case WEEKLY:
                return "Еженедельно";
            case MONTHLY:
                return "Ежемесячно";
            case YEARLY:
                return "Ежегодно";
            default:
                throw new IllegalArgumentException("Ой что то пошло не так");
        }
    }

    private static String localizeMenu(Menu menu) {
        switch (menu) {
            case ONE_MENU:
                return "Посмотреть список всех задач за все время";
            case TWO_MENU:
                return "Посмотреть список всех задач на день";
            case THREE_MENU:
                return "Редактировать задачу";
            case FOUR_MENU:
                return "Добавить новую задачу";
            case FAVE_MENU:
                return "Удалить задачу";
            case SIX_MENU:
                return "Вернуться в главное меню";
            case SEVEN_MENU:
                return "Выйти из приложения";
            default:
                throw new IllegalArgumentException("Ой что то пошло не так");
        }
    }
}
