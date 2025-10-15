package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represent a single financial transaction
 * CSV format used by the app : YYYY-MM-dd|HH:mm:ss|description|vendor|amount
 * Example:
 * 2025-14-10|12:30:23|ergnomic office chair|Amazon|-169.99
 */


public class Transaction {
    // fields for each transaction/ configuration/formatter

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String DELIMITER = "\\|";
    private static final String DELIMITER_RAW = "|";

    private final LocalDate date;
    private final LocalTime time;
    private final String description;
    private final String vendor;
    private final double amount;

    /**
     * Construct a Transaction
     * @param date  transaction date (non-null)
     * @param time transaction time (non-null)
     * @param description short description (can't contain the pipe '|' character
     * @param vendor vendor name ( can't contain the pipe '|' character
     * @param amount positive for deposit, negative for payment
     * @throws IllegalArgumentException if description/vendor contain the delimiter or if null are provided
     */


    // constructor to creat a Transcation object
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("Date and Time cannot be null");
        }
        if (description == null || vendor == null) {
            throw new IllegalArgumentException("Description and Vendor must not be null");
        }
        // protection that don't allow the pipe character in description/vendor. avoid CSV parsing issues
        if (description.contains(DELIMITER_RAW) || vendor.contains(DELIMITER_RAW)) {
            throw new IllegalArgumentException("Description or Vendor  must not contain any characters the '|'");
        }
        this.date = date;
        this.time = time;
        this.description = description.trim();
        this.vendor = vendor.trim();
        this.amount = amount;
    }

    // getter for each field for safe access
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
    // CSV serialization

    /**
     * covert this transaction to the CSV line format used by the application.
     * example 2025-14-10| 1:30:45|ergonomic office chair |Amazon|169.99
     * @return CSV formatted line
     */


    public String toCSVLine() {
            // format amount with two decimal (ensures consistent CSV)
        String amountStr = String.format("%.2f", amount);
        return DATE_FORMAT.format(time) + DELIMITER_RAW
                + TIME_FORMAT.format(time) + DELIMITER_RAW
                + description + DELIMITER_RAW
                + vendor + DELIMITER_RAW
                + amountStr;
        }

    /**
     * parse a CSV line and return a transaction
     * @param line CSV line in expected format(dat|time|description|vendor|amount)
     * @return parsed Transaction
     * @throws IllegalArgumentException if the line is malformed or parsing fails
     */
    public static Transaction fromCSVLine(String line)  {
        if (line == null|| line.isBlank()) {
            throw new IllegalArgumentException("CSV line is null or empty");
        }

        String[] parts = line.split(DELIMITER, -1); // -1 keeps empty fields
        if (parts.length != 5){
            throw new IllegalArgumentException("CSV line does not have 5 fields: " + line);
    }
   try {
        LocalDate date = LocalDate.parse(parts[0], DATE_FORMAT);
        LocalTime time = LocalTime.parse(parts[1], TIME_FORMAT);
        String description = parts[2].trim();
        String vendor = parts[3].trim();
        double amount = Double.parseDouble(parts[4].trim());
        return new Transaction(date, time, description, vendor, amount);
   } catch (DateTimeParseException e) {
   throw new IllegalArgumentException("Invalid data/time format in CSV line: " + line, e);
   } catch (NumberFormatException e) {
       throw new IllegalArgumentException("Invalid amount format in CSV line: " + line, e);
   }
    }

    // Display Transaction in readable formate

    /**
     * return a human friendly single line representation that useful for ledger display
     */
    @Override
    public String toString() {
        return String.format("%s %s | %s | %s |%s",
                DATE_FORMAT.format(date),
                TIME_FORMAT.format(time),
                description,
                vendor,
                String.format("%.2f", amount));
    }

    /**
     * print a readable representation to standard output (convenience for CLI)
     */
    public void display() {
       System.out.println(this. toString());
    }

    //equal/ hashcode useful for collections and tests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                date.equals(that.date) &&
                time.equals(that.time) &&
                description.equals(that.description)&&
                vendor.equals (that .vendor);
    }
    @Override
    public int hashCode() {
        return Objects.hash(date, time, description, vendor, amount);
    }

}


