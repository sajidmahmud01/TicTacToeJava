package upei.project;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Abstract Player class to represent a player in the game.
 * Contains common properties like the symbol ('X' or 'O').
 */
abstract class Player {
    protected char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Abstract method to be implemented by subclasses to make a move.
     * @param board The current state of the board.
     * @return The row and column of the move.
     */
    public abstract int[] makeMove(char[][] board);

    /**
     * Get the symbol of the player.
     * @return The symbol ('X' or 'O').
     */
    public char getSymbol() {
        return symbol;
    }
}

/**
 * RandomPlayer class that makes random moves on the board.
 */
class RandomPlayer extends Player {
    private Random random;

    public RandomPlayer(char symbol) {
        super(symbol);
        random = new Random();
    }

    /**
     * Makes a random move on the board.
     * @param board The current state of the board.
     * @return The row and column of the move.
     */
    @Override
    public int[] makeMove(char[][] board) {
        int row, col;
        do {
            row = random.nextInt(board.length);
            col = random.nextInt(board.length);
        } while (board[row][col] != '-');
        return new int[]{row, col};
    }
}

/**
 * BlockingPlayer class that tries to block the opponent from winning.
 */
class BlockingPlayer extends Player {
    public BlockingPlayer(char symbol) {
        super(symbol);
    }

    /**
     * Makes a move to block the opponent if possible, otherwise makes a random move.
     * @param board The current state of the board.
     * @return The row and column of the move.
     */
    @Override
    public int[] makeMove(char[][] board) {
        // Simple strategy: try to block the opponent from winning
        char opponent = (symbol == 'X') ? 'O' : 'X';
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = opponent;
                    if (checkWinner(board, opponent)) {
                        board[i][j] = '-';
                        return new int[]{i, j};
                    }
                    board[i][j] = '-';
                }
            }
        }
        // If no blocking move, choose randomly
        return new RandomPlayer(symbol).makeMove(board);
    }

    /**
     * Helper method to check if the given player has won.
     * @param board The current state of the board.
     * @param player The player symbol to check.
     * @return True if the player has won, false otherwise.
     */
    private boolean checkWinner(char[][] board, char player) {
        int boardSize = board.length;
        // Check rows and columns
        for (int i = 0; i < boardSize; i++) {
            if (checkRow(board, i, player) || checkColumn(board, i, player)) {
                return true;
            }
        }
        // Check diagonals
        return checkMainDiagonal(board, player) || checkAntiDiagonal(board, player);
    }

    /**
     * Helper method to check if the player has won in a specific row.
     * @param board The current state of the board.
     * @param row The row to check.
     * @param player The player symbol to check.
     * @return True if the player has won in the row, false otherwise.
     */
    private boolean checkRow(char[][] board, int row, char player) {
        for (int j = 0; j < board.length; j++) {
            if (board[row][j] != player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if the player has won in a specific column.
     * @param board The current state of the board.
     * @param col The column to check.
     * @param player The player symbol to check.
     * @return True if the player has won in the column, false otherwise.
     */
    private boolean checkColumn(char[][] board, int col, char player) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] != player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if the player has won in the main diagonal.
     * @param board The current state of the board.
     * @param player The player symbol to check.
     * @return True if the player has won in the main diagonal, false otherwise.
     */
    private boolean checkMainDiagonal(char[][] board, char player) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if the player has won in the anti-diagonal.
     * @param board The current state of the board.
     * @param player The player symbol to check.
     * @return True if the player has won in the anti-diagonal, false otherwise.
     */
    private boolean checkAntiDiagonal(char[][] board, char player) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][board.length - i - 1] != player) {
                return false;
            }
        }
        return true;
    }
}

/**
 * GameController class to manage the game flow between two players.
 */
class GameController {
    private TicTacToe game;
    private Player playerX;
    private Player playerO;

    public GameController(int boardSize, Player playerX, Player playerO) {
        this.game = new TicTacToe(boardSize);
        this.playerX = playerX;
        this.playerO = playerO;
    }

    /**
     * Method to play a complete game between two players.
     */
    public void playGame() {
        game.initializeBoard();
        game.printBoard();
        Player currentPlayer = playerX;

        while (true) {
            int[] move = currentPlayer.makeMove(game.getBoard());
            if (game.playMove(move[0], move[1])) {
                System.out.println("Player " + currentPlayer.getSymbol() + " makes a move at: (" + move[0] + ", " + move[1] + ")");
                game.printBoard();

                if (game.checkWinner()) {
                    System.out.println("Player " + currentPlayer.getSymbol() + " wins!");
                    game.updateScore();
                    break;
                } else if (game.isBoardFull()) {
                    System.out.println("The game is a draw!");
                    game.updateScore();
                    break;
                }

                currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
            } else {
                System.out.println("Invalid move by Player " + currentPlayer.getSymbol() + ". Try again.");
            }
        }

        game.printScore();
    }

    /**
     * Main method to start the game.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter board size (default 3 for 3x3): ");
        int boardSize = scanner.nextInt();

        Player playerX = new RandomPlayer('X');
        Player playerO = new BlockingPlayer('O');

        GameController controller = new GameController(boardSize, playerX, playerO);

        boolean playAgain = true;
        while (playAgain) {
            controller.playGame();
            System.out.print("Do you want to play again? (true/false): ");
            playAgain = scanner.nextBoolean();
        }
    }
}

/**
 * TicTacToe class to represent the game board and manage game mechanics.
 */
class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private int boardSize;
    private List<int[]> moveHistory;
    private int playerXWins;
    private int playerOWins;
    private int draws;

    /**
     * Constructor to initialize the game with the specified board size.
     * @param boardSize The size of the game board.
     */
    public TicTacToe(int boardSize) {
        this.boardSize = boardSize;
        board = new char[boardSize][boardSize];
        currentPlayer = 'X';
        moveHistory = new ArrayList<>();
        playerXWins = 0;
        playerOWins = 0;
        draws = 0;
        initializeBoard();
    }

    /**
     * Method to initialize the board with empty cells ('-').
     */
    public void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = '-';
            }
        }
    }

    /**
     * Method to print the current state of the board.
     */
    public void printBoard() {
        System.out.println("Current board:");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Method to play a move at the specified row and column.
     * @param row The row index for the move.
     * @param col The column index for the move.
     * @return True if the move is valid, false otherwise.
     */
    public boolean playMove(int row, int col) {
        // Check if the move is within bounds and the cell is empty
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || board[row][col] != '-') {
            return false;
        }
        // Place the current player's symbol on the board
        board[row][col] = currentPlayer;
        // Add the move to the history for undo functionality
        moveHistory.add(new int[]{row, col});
        return true;
    }

    /**
     * Method to check if the current player has won.
     * @return True if the current player has won, false otherwise.
     */
    public boolean checkWinner() {
        // Check all rows and columns for a win
        for (int i = 0; i < boardSize; i++) {
            if (checkRow(i) || checkColumn(i)) {
                return true;
            }
        }
        // Check both diagonals for a win
        return checkMainDiagonal() || checkAntiDiagonal();
    }

    /**
     * Helper method to check if the current player has won in a specific row.
     * @param row The row index to check.
     * @return True if the current player has won in the row, false otherwise.
     */
    private boolean checkRow(int row) {
        for (int j = 1; j < boardSize; j++) {
            if (board[row][j] != currentPlayer || board[row][j - 1] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if the current player has won in a specific column.
     * @param col The column index to check.
     * @return True if the current player has won in the column, false otherwise.
     */
    private boolean checkColumn(int col) {
        for (int i = 1; i < boardSize; i++) {
            if (board[i][col] != currentPlayer || board[i - 1][col] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if the current player has won in the main diagonal.
     * @return True if the current player has won in the main diagonal, false otherwise.
     */
    private boolean checkMainDiagonal() {
        for (int i = 1; i < boardSize; i++) {
            if (board[i][i] != currentPlayer || board[i - 1][i - 1] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to check if the current player has won in the anti-diagonal.
     * @return True if the current player has won in the anti-diagonal, false otherwise.
     */
    private boolean checkAntiDiagonal() {
        for (int i = 1; i < boardSize; i++) {
            if (board[i][boardSize - i - 1] != currentPlayer || board[i - 1][boardSize - i] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to check if the board is full (i.e., no empty cells).
     * @return True if the board is full, false otherwise.
     */
    public boolean isBoardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method to update the score after a game ends.
     */
    public void updateScore() {
        if (checkWinner()) {
            if (currentPlayer == 'X') {
                playerXWins++;
            } else {
                playerOWins++;
            }
        } else if (isBoardFull()) {
            draws++;
        }
    }

    /**
     * Method to print the current score of the game.
     */
    public void printScore() {
        System.out.println("Scoreboard:");
        System.out.println("Player X Wins: " + playerXWins);
        System.out.println("Player O Wins: " + playerOWins);
        System.out.println("Draws: " + draws);
    }

    /**
     * Getter for the board.
     * @return The current state of the board.
     */
    public char[][] getBoard() {
        return board;
    }
}
