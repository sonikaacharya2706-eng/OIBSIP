package atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// --- CLASS 1: Transaction ---
class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;
    private String note;

    public Transaction(String type, double amount, double balanceAfter, String note) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.note = note;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format("[%s] %-14s | Amount: $%.2f | Balance: $%.2f | %s", 
                timestamp, type, amount, balanceAfter, note);
    }
}

// --- CLASS 2: Account ---
class Account {
    private String userId;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Error: Deposit amount must be greater than zero.");
            return;
        }
        balance += amount;
        Transaction transaction = new Transaction("Deposit", amount, balance, "Direct Deposit");
        transactionHistory.add(transaction);
        System.out.printf("✅ Successfully deposited $%.2f. New Balance: $%.2f\n", amount, balance);
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Error: Withdrawal amount must be greater than zero.");
            return false;
        }
        if (amount > balance) {
            System.out.printf("❌ Insufficient Funds! Current Balance: $%.2f\n", balance);
            return false;
        }
        balance -= amount;
        Transaction transaction = new Transaction("Withdrawal", amount, balance, "ATM Cash Withdrawal");
        transactionHistory.add(transaction);
        System.out.printf("✅ Successfully withdrew $%.2f. Remaining Balance: $%.2f\n", amount, balance);
        return true;
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount <= 0) {
            System.out.println("❌ Error: Transfer amount must be greater than zero.");
            return false;
        }
        if (amount > balance) {
            System.out.printf("❌ Insufficient Funds! Current Balance: $%.2f\n", balance);
            return false;
        }

        // Deduct from sender and credit recipient
        this.balance -= amount;
        recipient.balance += amount;

        // Record log for sender
        Transaction senderTx = new Transaction("Transfer Out", amount, this.balance, "To Account: " + recipient.getUserId());
        this.transactionHistory.add(senderTx);

        // Record log for recipient
        Transaction recipientTx = new Transaction("Transfer In", amount, recipient.balance, "From Account: " + this.getUserId());
        recipient.transactionHistory.add(recipientTx);

        System.out.printf("✅ Successfully transferred $%.2f to User '%s'. New Balance: $%.2f\n", 
                amount, recipient.getUserId(), balance);
        return true;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

// --- CLASS 3: Bank ---
class Bank {
    private Map<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void addAccount(Account account) {
        accounts.put(account.getUserId(), account);
    }

    public Account authenticate(String userId, String pin) {
        Account account = accounts.get(userId);
        if (account != null && account.validatePin(pin)) {
            return account;
        }
        return null;
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }
}

// --- CLASS 4: ATM ---
class ATM {
    private Bank bank;
    private Scanner scanner;
    private Account currentAccount;

    public ATM(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("==========================================");
        System.out.println("       WELCOME TO OASIS BANK ATM          ");
        System.out.println("==========================================");

        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        // Startup Login Prompt (Max 3 Attempts)
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("\nEnter User ID: ");
            String userId = scanner.nextLine().trim();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            currentAccount = bank.authenticate(userId, pin);

            if (currentAccount != null) {
                System.out.println("\n✅ Access Granted! Welcome, " + userId + "!");
                showMainMenu();
                return;
            } else {
                attempts++;
                int remaining = MAX_ATTEMPTS - attempts;
                System.out.println("❌ Invalid User ID or PIN.");
                if (remaining > 0) {
                    System.out.println("⚠️ Attempts remaining: " + remaining);
                } else {
                    System.out.println("\n🚫 Access Denied! Maximum login attempts exceeded. Account Locked.");
                }
            }
        }
    }

    private void showMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n------------------------------------------");
            System.out.println("               MAIN MENU                  ");
            System.out.println("------------------------------------------");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Select an option (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayTransactionHistory();
                    break;
                case "2":
                    performWithdrawal();
                    break;
                case "3":
                    performDeposit();
                    break;
                case "4":
                    performTransfer();
                    break;
                case "5":
                    System.out.println("\nThank you for choosing Oasis Bank. Have a great day! 👋");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option! Please enter a choice between 1 and 5.");
            }
        }
    }

    private void displayTransactionHistory() {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        List<Transaction> history = currentAccount.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions found in this session.");
        } else {
            for (Transaction t : history) {
                System.out.println(t);
            }
        }
    }

    private void performWithdrawal() {
        System.out.print("\nEnter withdrawal amount: $");
        double amount = readDoubleInput();
        if (amount > 0) {
            currentAccount.withdraw(amount);
        }
    }

    private void performDeposit() {
        System.out.print("\nEnter deposit amount: $");
        double amount = readDoubleInput();
        if (amount > 0) {
            currentAccount.deposit(amount);
        }
    }

    private void performTransfer() {
        System.out.print("\nEnter Recipient User ID: ");
        String recipientId = scanner.nextLine().trim();

        if (recipientId.equalsIgnoreCase(currentAccount.getUserId())) {
            System.out.println("❌ Error: You cannot transfer money to your own account.");
            return;
        }

        Account recipient = bank.getAccount(recipientId);
        if (recipient == null) {
            System.out.println("❌ Error: Recipient account ID not found.");
            return;
        }

        System.out.print("Enter transfer amount: $");
        double amount = readDoubleInput();
        if (amount > 0) {
            currentAccount.transfer(recipient, amount);
        }
    }

    private double readDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a valid number.");
            return -1;
        }
    }
}

// --- CLASS 5: Main ---
public class Mainatm {
    public static void main(String[] args) {
        // Step 1: Initialize the Bank
        Bank bank = new Bank();

        // Step 2: Seed Mock Account Data for Testing
        // User 1: ID = user123, PIN = 1234, Initial Balance = $1000.00
        // User 2: ID = user456, PIN = 5678, Initial Balance = $500.00
        bank.addAccount(new Account("Kaushal", "1234", 1000.00));
        bank.addAccount(new Account("Soni", "5678", 500.00));

        // Step 3: Launch ATM Simulation
        ATM atm = new ATM(bank);
        atm.start();
    }
}