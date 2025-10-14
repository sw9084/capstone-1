package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    // fields for each transaction

    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    // constructor to creat a Transcation object
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // getter for each field
    public LocalDate getData() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public String getDescription() {
        return description;
    }
    public String getVendor() {
        return vendor;
    }
    public double getAmount() {
        return amount;
    }


        // Convert transaction to a Csv Line to save in file

        public String toCSVLine() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return date.format(dateFormatter) + "|" +
                    time.format(timeFormatter) + "|" +
                    description + "|" +
                    vendor + "|" +
                    amount;
        }

        // creat a Transaction object from a csv Line
    public static Transaction fromCSVLine(String line) {
        String[] parts = line.split("\\|");
        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time = LocalTime.parse(parts[0]);
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);
        return new Transaction(date, time, description, vendor, amount);

    }
    // Display Transaction in readable formate
    public void display() {
       System.out.println(date + " " + time + " | " + description + " | "  + vendor + " | " + amount);
    }

}


