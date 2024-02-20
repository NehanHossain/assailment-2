/* ***************************************
COSC1200
Nehan Hossain
Feb 18, 2024
produse a program that outputs
 */


public class Main {
    private String name;
    private int remainingPoints;
    private int dartsThrown;
    private boolean isWinner;

    public Player(String name, int startingPoints) {
        this.name = name;
        this.remainingPoints = startingPoints;
        this.dartsThrown = 0;
        this.isWinner = false;
    }

    public String getName() {
        return name;
    }

    public int getRemainingPoints() {
        return remainingPoints;
    }

    public int getDartsThrown() {
        return dartsThrown;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void subtractPoints(int points) {
        remainingPoints -= points;
        if (remainingPoints == 0) {
            isWinner = true;
        }
    }

    public void incrementDartsThrown() {
        dartsThrown++;
    }
}

class DartGame {
    private Player player1;
    private Player player2;
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    public DartGame(String player1Name, String player2Name, int startingPoints) {
        player1 = new Player(player1Name, startingPoints);
        player2 = new Player(player2Name, startingPoints);
    }

    public void startGame() {
        printGameStartMessage();
        determineStartingPlayer();
        playGame();
        printGameResult();
    }

    private void printGameStartMessage() {
        System.out.println("Player 1: " + player1.getName() + " and Player 2: " + player2.getName());
        System.out.println("Players will now throw a dart to decide who goes first.");
        System.out.println("Closest to the bullseye starts.");
    }

    private void determineStartingPlayer() {
        int player1Throw = random.nextInt(100) + 1;
        int player2Throw = random.nextInt(100) + 1;

        if (player1Throw < player2Throw) {
            System.out.println(player1.getName() + " goes first!");
        } else {
            System.out.println(player2.getName() + " goes first!");
        }
    }

    private void playGame() {
        while (!player1.isWinner() && !player2.isWinner()) {
            playRound(player1);
            if (player1.isWinner()) {
                break;
            }
            playRound(player2);
        }
    }

    private void playRound(Player player) {
        System.out.println("Round - " + player.getName() + "'s turn: " + player.getRemainingPoints() + " points remaining");
        int score = enterScore(player.getName());
        player.subtractPoints(score);
        player.incrementDartsThrown();
        if (player.getRemainingPoints() < 0) {
            player.subtractPoints(-score);
            System.out.println("Invalid score! Points remaining for " + player.getName() + ": " + player.getRemainingPoints());
        }
    }

    private int enterScore(String playerName) {
        System.out.print("Enter score for " + playerName + "'s dart: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private void printGameResult() {
        if (player1.isWinner()) {
            System.out.println(player1.getName() + " wins the game in " + player1.getDartsThrown() + " darts!");
        } else {
            System.out.println(player2.getName() + " wins the game in " + player2.getDartsThrown() + " darts!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Player 1's name: ");
        String player1Name = scanner.nextLine();
        System.out.print("Enter Player 2's name: ");
        String player2Name = scanner.nextLine();

        DartGame game = new DartGame(player1Name, player2Name, 501);
        game.startGame();

        scanner.close();
    }
}

