/* ***************************************
COSC1200
Nehan Hossain
Feb 18, 2024
produse a program that outputs
 */

// imports
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // Constants
    static final int NUMBER_OF_PLAYERS = 2;
    static final int MAX_ROUNDS = 3;
    static final int MAX_GAMES = 5;
    static final int MAX_SCORE = 501;
    static final int MAXIMUM_SCORE_POSSIBLE = 170;
    static final int MINIMUM_SCORE = 0;
    static final int FIRST_PLAYER_INDEX = 0;
    static final int SECOND_PLAYER_INDEX = 1;
    static final int FIRST_PLAYER = 0;
    static final int SECOND_PLAYER = 1;

    // Variables
    static String[] playerNames = new String[NUMBER_OF_PLAYERS];
    static int[] playerScores = new int[NUMBER_OF_PLAYERS];
    static int[] dartsThrown = new int[NUMBER_OF_PLAYERS];
    static int gamesWonByPlayer1 = 0;
    static int gamesWonByPlayer2 = 0;
    static int totalDartsThrown = 0;
    static int gamesPlayed = 0;

    // Main method
    public static void main(String[] args) {
        System.out.println("Welcome to the Darts Game!");

        // Input player names
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            System.out.print("Enter Player " + (i + 1) + "'s name: ");
            playerNames[i] = scanner.nextLine();
        }

        // Play games
        while (gamesPlayed < MAX_GAMES && (gamesWonByPlayer1 < MAX_ROUNDS && gamesWonByPlayer2 < MAX_ROUNDS)) {
            playGame();
        }

        // Output final results
        System.out.println("Match Over!");
        System.out.println("Final Score:");
        System.out.println(playerNames[FIRST_PLAYER_INDEX] + " won " + gamesWonByPlayer1 + " games.");
        System.out.println(playerNames[SECOND_PLAYER_INDEX] + " won " + gamesWonByPlayer2 + " games.");
        System.out.println(playerNames[FIRST_PLAYER_INDEX] + " 3-dart average score: " +
                calculateAverageScore(gamesWonByPlayer1));
        System.out.println(playerNames[SECOND_PLAYER_INDEX] + " 3-dart average score: " +
                calculateAverageScore(gamesWonByPlayer2));
    }

    // Method to play a game
    public static void playGame() {
        System.out.println("\nStarting Game " + (gamesPlayed + 1) + ".");

        // Determine starting player
        int currentPlayerIndex = random.nextInt(NUMBER_OF_PLAYERS);

        // Initialize player scores
        Arrays.fill(playerScores, MAX_SCORE);

        // Game loop
        while (true) {
            int opponentIndex = (currentPlayerIndex + 1) % NUMBER_OF_PLAYERS;
            System.out.println("\nIt's " + playerNames[currentPlayerIndex] + "'s turn.");
            System.out.println("Remaining score: " + playerScores[currentPlayerIndex]);

            int roundScore = 0;
            // Input scores for each dart
            for (int dart = 0; dart < MAX_ROUNDS; dart++) {
                System.out.print("Enter score for dart " + (dart + 1) + ": ");
                int score = validateInput();
                roundScore += score;
                if (score == MAX_SCORE) {
                    dartsThrown[currentPlayerIndex] = dart + 1;
                    break;
                }
            }
            scanner.nextLine();

            // Update player score
            if (playerScores[currentPlayerIndex] - roundScore >= MINIMUM_SCORE) {
                playerScores[currentPlayerIndex] -= roundScore;
                totalDartsThrown += dartsThrown[currentPlayerIndex];
            } else {
                System.out.println("The score you have entered has resulted in a negative score. " +
                        "Your score has returned to " + playerScores[currentPlayerIndex]);
            }

            // Check for win or bust
            if (playerScores[currentPlayerIndex] == MINIMUM_SCORE) {
                System.out.println(playerNames[currentPlayerIndex] + " wins the game in " +
                        dartsThrown[currentPlayerIndex] + " darts!");
                updateGameStatistics(currentPlayerIndex);
                gamesPlayed++;
                break;
            } else if (playerScores[currentPlayerIndex] < MINIMUM_SCORE) {
                System.out.println("Busted! " + playerNames[currentPlayerIndex] +
                        " starts the next round with " + MAX_SCORE + " points.");
            }
            currentPlayerIndex = opponentIndex;
        }
    }

    // Method to validate input score
    public static int validateInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer between 0 and 170.");
            scanner.next();
        }
        int input = scanner.nextInt();
        while (input < MINIMUM_SCORE || input > MAXIMUM_SCORE_POSSIBLE) {
            System.out.println("Invalid input. Please enter an integer between 0 and 170.");
            input = scanner.nextInt();
        }
        return input;
    }

    // Method to update game statistics
    public static void updateGameStatistics(int currentPlayerIndex) {
        if (currentPlayerIndex == FIRST_PLAYER) {
            gamesWonByPlayer1++;
        } else {
            gamesWonByPlayer2++;
        }
    }

    // Method to calculate average score
    public static double calculateAverageScore(int gamesWonByPlayer) {
        return (gamesWonByPlayer > MINIMUM_SCORE) ? (double) totalDartsThrown / gamesWonByPlayer : 0.0;
    }
}
