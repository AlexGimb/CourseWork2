package Diary;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface userInterface = new UserInterface();

        userInterface.mainMenu(scanner);

    }

}