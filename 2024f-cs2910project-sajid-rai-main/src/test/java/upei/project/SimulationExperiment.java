package upei.project;

import java.util.HashMap;
import java.util.Map;

/**
 * SimulationExperiment class to conduct experiments comparing different player strategies.
 */
public class SimulationExperiment {
    private static final int NUM_TRIALS = 100;

    public static void main(String[] args) {
        runExperiment();
    }

    /**
     * Method to run the experiment with different player strategies.
     */
    public static void runExperiment() {
        Player randomPlayerX = new RandomPlayer('X');
        Player randomPlayerO = new RandomPlayer('O');
        Player blockingPlayerX = new BlockingPlayer('X');
        Player blockingPlayerO = new BlockingPlayer('O');

        // Experiment 1: Random Player vs Blocking Player
        System.out.println("Experiment 1: Random Player vs Blocking Player");
        Map<String, Integer> results1 = playGames(randomPlayerX, blockingPlayerO);
        printResults(results1);

        // Experiment 2: Blocking Player vs Random Player
        System.out.println("Experiment 2: Blocking Player vs Random Player");
        Map<String, Integer> results2 = playGames(blockingPlayerX, randomPlayerO);
        printResults(results2);

        // Experiment 3: Blocking Player vs Blocking Player
        System.out.println("Experiment 3: Blocking Player vs Blocking Player");
        Map<String, Integer> results3 = playGames(blockingPlayerX, blockingPlayerO);
        printResults(results3);
    }

    /**
     * Method to play multiple games between two players and collect results.
     * @param playerX Player representing 'X'.
     * @param playerO Player representing 'O'.
     * @return A map containing the results of the games.
     */
    public static Map<String, Integer> playGames(Player playerX, Player playerO) {
        Map<String, Integer> results = new HashMap<>();
        results.put("Player X Wins", 0);
        results.put("Player O Wins", 0);
        results.put("Draws", 0);

        for (int i = 0; i < NUM_TRIALS; i++) {
            TicTacToe game = new TicTacToe(3);
            GameController controller = new GameController(3, playerX, playerO);

            Player currentPlayer = playerX;
            game.initializeBoard();

            while (true) {
                int[] move = currentPlayer.makeMove(game.getBoard());
                if (game.playMove(move[0], move[1])) {
                    if (game.checkWinner()) {
                        if (currentPlayer == playerX) {
                            results.put("Player X Wins", results.get("Player X Wins") + 1);
                        } else {
                            results.put("Player O Wins", results.get("Player O Wins") + 1);
                        }
                        break;
                    } else if (game.isBoardFull()) {
                        results.put("Draws", results.get("Draws") + 1);
                        break;
                    }
                    currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
                }
            }
        }
        return results;
    }

    /**
     * Method to print the results of the experiment.
     * @param results A map containing the results of the games.
     */
    public static void printResults(Map<String, Integer> results) {
        System.out.println("Results after " + NUM_TRIALS + " trials:");
        System.out.println("Player X Wins: " + results.get("Player X Wins"));
        System.out.println("Player O Wins: " + results.get("Player O Wins"));
        System.out.println("Draws: " + results.get("Draws"));
        System.out.println();
    }
}
