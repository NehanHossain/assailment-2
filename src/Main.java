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
    static final float ZEROPOINTZERO = 0.0f;
    static final int NUMBEROFPLAYERS = 2;
    static final int MAXIMUMROUNDS = 3;
    static final int MAXIMUMGAMES = 5;
    static final int MAXIMUMSCORE = 501;
    static final int MAXIMUMSCOREPOSSIBLE = 170;
    static final int MINIMUMSCORE = 0;
    static final int FIRSTPLAYERINDEX = 0;
    static final int SECONDPLAYERINDEX = 1;
    static final int FIRSTPLAYER = 0;
    static final int SECONDPLAYER = 1;

    // Variables
    static String[] player_names = new String[NUMBEROFPLAYERS];
    static int[] player_scores = new int[NUMBEROFPLAYERS];
    static int[] darts_thrown = new int[NUMBEROFPLAYERS];
    static int games_won_by_player1 = 0;
    static int games_won_by_player2 = 0;
    static int total_darts_thrown = 0;
    static int games_played = 0;

    // Main method
    public static void main(String[] args) {
        System.out.println("Welcome to the Darts Game!");

        // Input player names
        for (int name = FIRSTPLAYER; name < NUMBEROFPLAYERS; name++) {
            System.out.print("Enter Player " + (name + SECONDPLAYER) + "'s name: ");
            player_names[name] = scanner.nextLine();
        }

        while (games_played < MAXIMUMGAMES && (games_won_by_player1 < MAXIMUMROUNDS && games_won_by_player2 < MAXIMUMROUNDS)) {
            playGame();
        }

        System.out.println("Match Over!");
        System.out.println("Final Score:");
        System.out.println(player_names[FIRSTPLAYERINDEX] + " won " + games_won_by_player1 + " games.");
        System.out.println(player_names[SECONDPLAYERINDEX] + " won " + games_won_by_player2 + " games.");
        System.out.println(player_names[FIRSTPLAYERINDEX] + " 3-dart average score: " +
                calculateAverageScore(games_won_by_player1));
        System.out.println(player_names[SECONDPLAYERINDEX] + " 3-dart average score: " +
                calculateAverageScore(games_won_by_player2));
    }

    // Method to play a game
    public static void playGame() {
        System.out.println("\nStarting Game " + (games_played + SECONDPLAYER) + ".");

        int currentPlayerIndex = random.nextInt(NUMBEROFPLAYERS);

        Arrays.fill(player_scores, MAXIMUMSCORE);

        while (true) {
            int opponentIndex = (currentPlayerIndex + SECONDPLAYER) % NUMBEROFPLAYERS;
            System.out.println("\nIt's " + player_names[currentPlayerIndex] + "'s turn.");
            System.out.println("Remaining score: " + player_scores[currentPlayerIndex]);

            int roundScore = FIRSTPLAYER;

            for (int dart = FIRSTPLAYER; dart < MAXIMUMROUNDS; dart++) {
                System.out.print("Enter score for dart " + (dart + SECONDPLAYER) + ": ");
                int score = validateInput();
                roundScore += score;
                if (score == MAXIMUMSCORE) {
                    darts_thrown[currentPlayerIndex] = dart + SECONDPLAYER;
                    break;
                }
            }
            scanner.nextLine();

            if (player_scores[currentPlayerIndex] - roundScore >= MINIMUMSCORE) {
                player_scores[currentPlayerIndex] -= roundScore;
                total_darts_thrown += darts_thrown[currentPlayerIndex];
            } else {
                System.out.println("The score you have entered has resulted in a negative score. " +
                        "Your score has returned to " + player_scores[currentPlayerIndex]);
            }

            if (player_scores[currentPlayerIndex] == MINIMUMSCORE) {
                System.out.println(player_names[currentPlayerIndex] + " wins the game in " +
                        darts_thrown[currentPlayerIndex] + " darts!");
                updateGameStatistics(currentPlayerIndex);
                games_played++;
                break;
            } else if (player_scores[currentPlayerIndex] < MINIMUMSCORE) {
                System.out.println("Busted! " + player_names[currentPlayerIndex] +
                        " starts the next round with " + MAXIMUMSCORE + " points.");
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
        while (input < MINIMUMSCORE || input > MAXIMUMSCOREPOSSIBLE) {
            System.out.println("Invalid input. Please enter an integer between 0 and 170.");
            input = scanner.nextInt();
        }
        return input;
    }

    // Method to update game statistics
    public static void updateGameStatistics(int currentPlayerIndex) {
        if (currentPlayerIndex == FIRSTPLAYER) {
            games_won_by_player1++;
        } else {
            games_won_by_player2++;
        }
    }

    // Method to calculate average score
    public static double calculateAverageScore(int gamesWonByPlayer) {
        return (gamesWonByPlayer > MINIMUMSCORE) ? (double) total_darts_thrown / gamesWonByPlayer : ZEROPOINTZERO;
    }
}
