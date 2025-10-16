package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java. util.List;
import java.util.Scanner;

/**
 * the Main class provide a simple command-line interface
 * for manging financial transaction (deposits and payments)
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("==========================================");
        System.out.println("         WELCOME TO FINTRACK APP          ");
        System.out.println("==========================================");

        while (running) {
            System.out.println("\nMAIN MENU:");
            System.out.println("1) Add Deposit");
            System.out.println("2) Add Payment");
            System.out.println("3) View Ledger");
            System.out.println("4) Exit");
            System.out.println("Choose an option: ");

            String choice = scanner.nextLine().trim();
            List<Transaction> transactions = Ledger.loadTransactions();

            switch (choice) {
                   case "1":
                    addTransaction(scanner, true);
                    break;
                    case "2":
                        addTransaction(scanner, false);
                        break;
                        case "3":
                            showLedgerMenu(scanner,transactions);
                            break;
                            case "4":
                                running = false;
                                System.out.println("GoodBye! Thank you for choosing FinTrack APP!");
                                break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    /** adds a transaction (deposit or payment) to the ledger
     *
     */
    private static  void addTransaction(Scanner scanner, boolean isDeposit) {
        try
            System.out.print("Enter description:");
            String description = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();

            System.out.print("Enter amount:");
            double amount = Double.parseDouble(scanner.nextLine());

            if (!isDeposit) amount *= -1;

            Transaction newTranscation = new Transaction(
                    LocalDate.now(),
                    LocalTime.now(),
                    description,
                    vendor,
                    amount);
            Ledger.saveTransaction(newTranscation);
            System.out.println("Transaction saved successfully!");
    } catch (Exception e) {
        System.out.println("Error adding transaction: " + e.getMessage());
    }
}

/**
 * Display ledger option (all, deposits, payment)
 */
private static void showLedgerMenu(Scanner scanner, List<Transaction> transactions) {
    System.out.println("\nLEDGER MENU:");
    System.out.println("1) View All Transactions");
    System.out.println("2) View Deposit ");
    System.out.println("3) View Payment ");
    System.out.println("Choose an option:");

    String option = scanner.nextLine().trim();

    switch (option) {
        case "1":
            Ledger.displayAll(transactions);
            break;
        case "2":
            Ledger.displayDeposits(transactions);
            break;
        case "3":
            Ledger.displayPayments(transactions);
            break;
        default:
            System.out.println("Invalid option. Returning to main menu.");
    }
}
}
