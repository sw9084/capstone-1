package com.pluralsight;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.io.*;

/**
 * The Ledger class manages all transaction in the system
 * it loads transaction from CSV file, adds new ones
 * and display or filters transaction based on user input
 */

public class Ledger {
    private static final String FILE_PATH = "transactions.csv";

    /**
     * loads all transaction from the CSV file
     *
     * @return a list of transaction objects.
     */
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(Transaction.fromCSVLine(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }

    /**
     * saves a new transaction to the CSV file
     *
     * @param transaction the transaction to save
     */
    public static void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(transaction.toCSVLine());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }




    /**
     * display all transaction in ledger
     * @param transactions the list of transactions to display.
     */
    public static void displayAll(List<Transaction> transactions) {
        System.out.println("\n===== ALL TRANSACTIONS ====");
        for (int i = transactions.size()-1; i >= 0; i--) {
            System.out.println(transactions.get(i));
            }
        }
    /**
     * Display only deposit transaction(List<Transaction (positive amount)
     */
    public static void displayDeposits(List<Transaction> transactions) {
        System.out.println("\n=====DEPOSITS ====");
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                System.out.println(t);
            }
        }
    }
    /**
     * display only payment transactions (negative amounts).
     */
    public static void displayPayments(List<Transaction> transactions) {
        System.out.println("\n=====PAYMENTS ====");
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                System.out.println(t);
            }
        }
    }


}
