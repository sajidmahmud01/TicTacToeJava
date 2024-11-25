package upei.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TicTacToe, Player strategies, and GameController.
 */
public class TicTacToeTest {

    private TicTacToe game;
    private Player randomPlayer;
    private Player blockingPlayer;

    /**
     * Set up a new game and players before each test.
     */
    @BeforeEach
    public void setUp() {
        game = new TicTacToe(3);
        randomPlayer = new RandomPlayer('X');
        blockingPlayer = new BlockingPlayer('O');
    }

    /**
     * Test that the board is initialized correctly with all empty cells.
     */
    @Test
    public void testInitializeBoard() {
        game.initializeBoard();
        char[][] board = game.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals('-', board[i][j], "Board should be initialized with empty cells.");
            }
        }
    }

    /**
     * Test playing a valid move on the board.
     */
    @Test
    public void testPlayMoveValid() {
        boolean movePlayed = game.playMove(0, 0);
        assertTrue(movePlayed, "Move should be valid.");
        assertEquals('X', game.getBoard()[0][0], "Cell should contain the current player's symbol.");
    }

    /**
     * Test playing an invalid move (out of bounds).
     */
    @Test
    public void testPlayMoveInvalidOutOfBounds() {
        boolean movePlayed = game.playMove(3, 3);
        assertFalse(movePlayed, "Move should be invalid if out of bounds.");
    }

    /**
     * Test playing an invalid move (cell already occupied).
     */
    @Test
    public void testPlayMoveInvalidOccupied() {
        game.playMove(0, 0);
        boolean movePlayed = game.playMove(0, 0);
        assertFalse(movePlayed, "Move should be invalid if cell is already occupied.");
    }

    /**
     * Test the checkWinner method when a player has won in a row.
     */
    @Test
    public void testCheckWinnerRow() {
        game.playMove(0, 0);
        game.playMove(1, 0);
        game.playMove(0, 1);
        game.playMove(1, 1);
        game.playMove(0, 2);
        assertTrue(game.checkWinner(), "Player X should win with a complete row.");
    }

    /**
     * Test the checkWinner method when a player has won in a column.
     */
    @Test
    public void testCheckWinnerColumn() {
        game.playMove(0, 0);
        game.playMove(0, 1);
        game.playMove(1, 0);
        game.playMove(1, 1);
        game.playMove(2, 0);
        assertTrue(game.checkWinner(), "Player X should win with a complete column.");
    }

    /**
     * Test the checkWinner method when a player has won in the main diagonal.
     */
    @Test
    public void testCheckWinnerMainDiagonal() {
        game.playMove(0, 0);
        game.playMove(0, 1);
        game.playMove(1, 1);
        game.playMove(1, 2);
        game.playMove(2, 2);
        assertTrue(game.checkWinner(), "Player X should win with a complete main diagonal.");
    }

    /**
     * Test the checkWinner method when a player has won in the anti-diagonal.
     */
    @Test
    public void testCheckWinnerAntiDiagonal() {
        game.playMove(0, 2);
        game.playMove(0, 1);
        game.playMove(1, 1);
        game.playMove(1, 0);
        game.playMove(2, 0);
        assertTrue(game.checkWinner(), "Player X should win with a complete anti-diagonal.");
    }

    /**
     * Test the isBoardFull method when the board is full.
     */
    @Test
    public void testIsBoardFull() {
        game.playMove(0, 0);
        game.playMove(0, 1);
        game.playMove(0, 2);
        game.playMove(1, 0);
        game.playMove(1, 1);
        game.playMove(1, 2);
        game.playMove(2, 0);
        game.playMove(2, 1);
        game.playMove(2, 2);
        assertTrue(game.isBoardFull(), "The board should be full.");
    }

    /**
     * Test the makeMove method of RandomPlayer to ensure it makes a valid move.
     */
    @Test
    public void testRandomPlayerMove() {
        int[] move = randomPlayer.makeMove(game.getBoard());
        assertTrue(move[0] >= 0 && move[0] < 3 && move[1] >= 0 && move[1] < 3, "RandomPlayer should make a move within bounds.");
    }

    /**
     * Test the makeMove method of BlockingPlayer to ensure it blocks the opponent.
     */
    @Test
    public void testBlockingPlayerMove() {
        game.playMove(0, 0); // X
        game.playMove(1, 1); // O
        game.playMove(0, 1); // X
        int[] move = blockingPlayer.makeMove(game.getBoard());
        assertArrayEquals(new int[]{0, 2}, move, "BlockingPlayer should block Player X from winning.");
    }
}
