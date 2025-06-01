import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SettingsManager.loadSettings();
        while (true) {
            int choice = showMenu();
            switch (choice) {
                case 1:
                    Game.start();
                    break;
                case 2:
                    SettingsManager.changeSettings();
                    break;
                case 3:
                    SettingsManager.showStatistics();
                    break;
                case 0:
                    System.out.println("Вихід з гри");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static int showMenu() {
        System.out.println("=== Головне меню ===");
        System.out.println("1. Почати гру");
        System.out.println("2. Налаштування");
        System.out.println("3. Показати статистику");
        System.out.println("0. Вихід");
        System.out.print("Ваш вибір: ");
        return scanner.nextInt();
    }
}
