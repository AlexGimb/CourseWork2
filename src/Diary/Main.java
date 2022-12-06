package Diary;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static UserInterface userInterface = new UserInterface();

    public static void main(String[] args) {
        userInterface.mainMenu(scanner);

    }
}