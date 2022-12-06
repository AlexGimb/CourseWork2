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
    private  final DateTimeFormatter DATA_FORMATTED = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private  final DateTimeFormatter TIME_FORMATTED = DateTimeFormatter.ofPattern("HH:mm");

    public  void mainMenu (Scanner scanner) {
        Menu menu = readMenu(scanner);
        switch (menu) {
            case ONE_MENU:
                printAllTask(scanner);
                break;
            case TWO_MENU:
                printTaskFotDate(scanner);
                break;
            case THREE_MENU:
                changeTask(scanner);
                break;
            case FOUR_MENU:
                addTusk(scanner);
                break;
            case FAVE_MENU:
                removeTask(scanner);
                break;
            case SIX_MENU:
                mainMenu(scanner);
                break;
            case SEVEN_MENU:
                scanner.close();
                break;
            default:
                throw new IllegalArgumentException("Ой что то пошло не так");
        }
    }

    private Menu readMenu(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Меню");
                for (Menu menu : Menu.values()) {
                    System.out.println(menu.ordinal() + " " + localizeMenu(menu));
                }
                System.out.print("Выберите пункт меню: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = parseInt(ordinalLine);
                return Menu.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Выбран не верный пункт меню!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Пункт меню не найден!");
            }
        }
    }

    private  void menuInOut(Scanner scanner) {
        Menu menu = backToMainMenuExitTheApp(scanner);
        switch (menu) {
            case SIX_MENU:
                mainMenu(scanner);
                break;
            case SEVEN_MENU:
                scanner.close();
                break;
        }
    }

    private Menu backToMainMenuExitTheApp(Scanner scanner) {
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
                return Menu.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Выбран не верный номер меню");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Меню не найдено");
            }
        }
    }
    public void changeTask(Scanner scanner) {
        int id = parseInt(readId(scanner));
        String title = readString("Введите новое название задачи: ", scanner);
        String description = readString("Введите новое описание задачи: ", scanner);
        Schedule.schedule.changeTask(id, title, description);
        menuInOut(scanner);
    }

    private  String readId(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Все задачи: ");
                for (Task task : Schedule.schedule.allTasks()) {
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
                Schedule.schedule.search(id);
                return idLines;
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер");
            } catch (IllegalStateException e) {
                System.out.println("Задача не найдена");
            }
        }
    }

    public  void addTusk(Scanner scanner) {
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
        Schedule.schedule.addTask(task);
        System.out.println("Задача <<" + title + ">> добавлена");
        menuInOut(scanner);
    }
    private  String readString(String message, Scanner scanner) {
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

    private  TaskType readType(Scanner scanner) {
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
    private  LocalDateTime readDateTime(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        LocalTime localTime = readTime(scanner);
        return localDate.atTime(localTime);
    }
    private  LocalDate readDate(Scanner scanner) {
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
    private  LocalTime readTime(Scanner scanner) {
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
    private  Repeatability readRepeatability(Scanner scanner) {
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
    public  void printAllTask(Scanner scanner) {
        System.out.println("Все задачи: ");
        for (Task task : Schedule.schedule.allTasks()) {
            System.out.printf("[%s] [%s] || Время: %s || %s - %s %n",
                    localizeTaskType(task.getTaskType()),
                    localizeRepeatability(task.getRepeatabilityType()),
                    task.getTaskDatetime().format(TIME_FORMATTED),
                    task.getTitle(),
                    task.getDescription());
        }
        menuInOut(scanner);
    }
    public  void printTaskFotDate(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        Collection<Task> taskFotDate = Schedule.schedule.getTaskForData(localDate);
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
    public  void removeTask(Scanner scanner) {
        System.out.println("Все задачи: ");
        for (Task task : Schedule.schedule.allTasks()) {
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
                Schedule.schedule.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер");
                break;
            } catch (IllegalStateException e) {
                System.out.println("Задача не найдена");
                break;
            }
        }
        System.out.println("Задача удалена");
        menuInOut(scanner);
    }
    private  String localizeTaskType(TaskType taskType) {
        switch (taskType) {
            case PERSONAL:
                return "Персональная задача";
            case WORKING:
                return "Рабочая задача";
            default:
                throw new IllegalArgumentException("Ой что то пошло не так");
        }
    }
    private  String localizeRepeatability(Repeatability repeatability) {
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

    private  String localizeMenu(Menu menu) {
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
