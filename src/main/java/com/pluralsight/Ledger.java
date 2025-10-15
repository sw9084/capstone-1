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
     * loads all transaction from the csv file
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
            System.out.println("Error loading transactions: "+ e.getMessage());
        }
        return transactions;
    }

}
