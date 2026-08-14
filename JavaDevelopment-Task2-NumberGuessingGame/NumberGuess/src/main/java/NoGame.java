

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class NoGame {

    private static String userName = "Gamer";
    private static int totalWins = 0;
    private static int totalLosses = 0;
    private static int roundCounter = 0;
    private static List<String> roundSummaries = new ArrayList<>();
    
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        displayGameIntro();
        setupProfile();

        boolean quit = false;
        while (!quit) {
            displayMainMenu();
            int choice = getIntInput(1, 5);

            switch (choice) {
                case 1: playMode("Easy", 50, 10); break;
                case 2: playMode("Medium", 100, 7); break;
                case 3: playMode("Hard", 200, 5); break;
                case 4: displayStats(); break;
                case 5: quit = true; break;
                default: System.out.println("Invalid choice."); break;
            }

            // Play Again prompt directly after a game round finishes (Choices 1-3)
            if (!quit && choice >= 1 && choice <= 3) {
                System.out.print("\nDo you want to play again? (yes/no): ");
                String playAgain = scanner.nextLine().trim().toLowerCase();
                if (playAgain.equals("no") || playAgain.equals("n")) {
                    quit = true;
                }
            }
        }

        displayGameOutro();
        scanner.close();
    }

    private static void displayGameIntro() {
        System.out.println("=====================================================");
        System.out.println("          WELCOME TO THE NUMBER GUESSING GAME        ");
        System.out.println("=====================================================");
        System.out.println();
    }

    private static void setupProfile() {
        System.out.print("Enter your username:");
        userName = scanner.nextLine().trim();
        if (userName.isEmpty()) { userName = "Guest"; }
        System.out.println("Welcome, " + userName + "! Your new profile is set up.");
        System.out.println();
    }

    private static void displayGameOutro() {
        System.out.println();
        printLineBorder();
        System.out.println("Thank you for playing, " + userName + "! Final Score:");
        System.out.println("Wins: " + totalWins + " | Losses: " + totalLosses);
        printLineBorder();
    }

    // --- Main Menu ---

    private static void displayMainMenu() {
        System.out.println();
        System.out.println("=======================================");
        System.out.println("    USER PROFILE: " + userName);
        System.out.println("    Rounds Played: " + roundCounter);
        System.out.println("=======================================");
        System.out.println("1. Easy (1-50, 10 attempts)");
        System.out.println("2. Medium (1-100, 7 attempts)");
        System.out.println("3. Hard (1-200, 5 attempts)");
        System.out.println("4. View Detailed Stats (Round Summaries)");
        System.out.println("5. Exit Game");
        System.out.print("Enter choice (1-5): ");
    }

    // --- Game Logic ---

    private static void playMode(String modeName, int range, int maxChances) {
        roundCounter++;
        int currentTarget = random.nextInt(range) + 1; // 1 to range inclusive
        int attemptsTaken = 0;
        boolean won = false;

        System.out.println();
        System.out.println("--- ROUND " + roundCounter + ": " + modeName.toUpperCase() + " MODE ---");
        System.out.println("A number has been generated in the range 1 to " + range + ".");
        System.out.println("You have " + maxChances + " attempts to find it.");

        while (attemptsTaken < maxChances) {
            int remainingChances = maxChances - attemptsTaken;
            System.out.println("\nAttempts remaining: " + remainingChances);
            System.out.print("Enter your guess: ");
            int guess = getIntInput(1, range);
            
            attemptsTaken++;

            if (guess == currentTarget) {
                System.out.println("Correct, You Win!");
                won = true;
                totalWins++;
                // Track score summary as requested
                roundSummaries.add("Round " + roundCounter + " — guessed in " + attemptsTaken + " attempts (" + modeName + ")");
                break;
            } else if (guess > currentTarget) {
                System.out.println("Low!");
            } else {
                System.out.println("High!");
            }
        }

        if (!won) {
            System.out.println("\nYou Lost! The number was: " + currentTarget);
            totalLosses++;
            roundSummaries.add("Round " + roundCounter + " — Failed to guess (Target: " + currentTarget + ", " + modeName + ")");
        }
    }

    // --- Statistical Display ---

    private static void displayStats() {
        System.out.println();
        printLineBorder();
        System.out.println("         " + userName + "'s SCORE TRACKING        ");
        printLineBorder();
        System.out.println("Total Wins:   " + totalWins);
        System.out.println("Total Losses: " + totalLosses);
        
        System.out.println("\n--- Round Summaries ---");
        if (roundSummaries.isEmpty()) {
            System.out.println("No rounds played yet.");
        } else {
            for (String summary : roundSummaries) {
                System.out.println(summary);
            }
        }
        printLineBorder();
        System.out.println("Press Enter to return to main menu...");
        scanner.nextLine();
    }

    // --- Helper Functions ---

    // Get an integer input within a specific range with validation
    private static int getIntInput(int min, int max) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.print("Invalid input. Enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid format. Enter a number: ");
            }
        }
    }

    private static void printLineBorder() {
        System.out.println("---------------------------------------");
    }
}