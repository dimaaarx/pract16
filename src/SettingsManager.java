import java.io.*;
import java.util.Scanner;
import java.time.LocalDateTime;

public class SettingsManager {
    private static int boardSize = 3;
    private static String playerX = "Гравець X";
    private static String playerO = "Гравець O";

    public static void loadSettings() {
        try (Scanner sc = new Scanner(new File("settings.txt"))) {
            boardSize = Integer.parseInt(sc.nextLine());
            playerX = sc.nextLine();
            playerO = sc.nextLine();
        } catch (Exception e) {
            boardSize = 3;
        }
    }

    public static void saveSettings() {
        try (PrintWriter out = new PrintWriter("settings.txt")) {
            out.println(boardSize);
            out.println(playerX);
            out.println(playerO);
        } catch (Exception e) {
            System.out.println("Помилка збереження налаштувань.");
        }
    }

    public static void changeSettings() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Оберіть розмір поля (3, 5, 7, 9): ");
        boardSize = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ім'я для гравця X: ");
        playerX = scanner.nextLine();
        System.out.print("Ім'я для гравця O: ");
        playerO = scanner.nextLine();
        saveSettings();
    }

    public static void saveGameStatistics(String result) {
        try (FileWriter writer = new FileWriter("stats.txt", true)) {
            String name = result.equals("X") ? playerX : result.equals("O") ? playerO : "Нічия";
            writer.write(name + "," + result + "," + LocalDateTime.now() + "," + boardSize + "\n");
        } catch (Exception e) {
            System.out.println("Помилка збереження статистики.");
        }
    }

    public static void showStatistics() {
        try (Scanner sc = new Scanner(new File("stats.txt"))) {
            System.out.println("=== Статистика ігор ===");
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 4) {
                    System.out.println("Гравець: " + parts[0] +
                            ", Результат: " + parts[1] +
                            ", Дата: " + parts[2] +
                            ", Розмір: " + parts[3]);
                }
            }
        } catch (Exception e) {
            System.out.println("Файл статистики не знайдено або порожній.");
        }
    }

    public static int getBoardSize() {
        return boardSize;
    }

    public static String getPlayerName(char symbol) {
        return (symbol == 'X') ? playerX : playerO;
    }
}
