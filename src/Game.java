import java.util.Scanner;

public class Game {
    private static char[][] board;
    private static char currentPlayer = 'X';
    private static boolean gameOver = false;
    private static final Scanner scanner = new Scanner(System.in);

    public static void start() {
        int boardSize = SettingsManager.getBoardSize();
        board = new char[boardSize][boardSize];
        initializeBoard(boardSize);
        gameOver = false;
        currentPlayer = 'X';

        while (!gameOver) {
            displayBoard(boardSize);
            playerMove(boardSize);
            checkGameState(boardSize);
            if (!gameOver) switchPlayer();
        }
    }

    private static void initializeBoard(int size) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = ' ';
    }

    private static void displayBoard(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j]);
                if (j < size - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i < size - 1) {
                for (int k = 0; k < size * 4 - 1; k++) System.out.print("-");
                System.out.println();
            }
        }
    }

    private static void playerMove(int size) {
        int row, col;
        while (true) {
            System.out.println(SettingsManager.getPlayerName(currentPlayer) + ", введіть рядок і стовпець (1-" + size + "): ");
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;
            if (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                break;
            } else {
                System.out.println("Невірний хід, спробуйте ще раз.");
            }
        }
    }

    private static void checkGameState(int size) {
        if (checkWin(size)) {
            displayBoard(size);
            System.out.println("Гравець " + SettingsManager.getPlayerName(currentPlayer) + " переміг!");
            SettingsManager.saveGameStatistics(String.valueOf(currentPlayer));
            gameOver = true;
        } else if (isDraw(size)) {
            displayBoard(size);
            System.out.println("Нічия!");
            SettingsManager.saveGameStatistics("Draw");
            gameOver = true;
        }
    }

    private static boolean checkWin(int size) {
        for (int i = 0; i < size; i++) {
            if (checkRow(i, size) || checkColumn(i, size)) return true;
        }
        return checkDiagonals(size);
    }

    private static boolean checkRow(int row, int size) {
        for (int j = 1; j < size; j++) {
            if (board[row][j] != board[row][0] || board[row][j] == ' ') return false;
        }
        return true;
    }

    private static boolean checkColumn(int col, int size) {
        for (int i = 1; i < size; i++) {
            if (board[i][col] != board[0][col] || board[i][col] == ' ') return false;
        }
        return true;
    }

    private static boolean checkDiagonals(int size) {
        boolean main = true, anti = true;
        for (int i = 1; i < size; i++) {
            if (board[i][i] != board[0][0] || board[i][i] == ' ') main = false;
            if (board[i][size - i - 1] != board[0][size - 1] || board[i][size - i - 1] == ' ') anti = false;
        }
        return main || anti;
    }

    private static boolean isDraw(int size) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == ' ') return false;
        return true;
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
}
