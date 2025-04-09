import java.util.*;
import java.io.*;

class Transaction {
    String businessType;
    String itemName;
    double origPrice;
    double resellPrice;
    double profitUsd;
    double profitPhp;
    double amountLent;
    int months;
    double repayAmount;
    String date;

    public Transaction(String businessType, String itemName, double origPrice, double resellPrice,
                       double profitUsd, double profitPhp, double amountLent, int months, double repayAmount, String date) {
        this.businessType = businessType;
        this.itemName = itemName;
        this.origPrice = origPrice;
        this.resellPrice = resellPrice;
        this.profitUsd = profitUsd;
        this.profitPhp = profitPhp;
        this.amountLent = amountLent;
        this.months = months;
        this.repayAmount = repayAmount;
        this.date = date;
    }

    public String toString() {
        return date + "|" + businessType + "|" + itemName + "|" + origPrice + "|" + resellPrice + "|" + profitUsd + "|" + profitPhp + "|" + amountLent + "|" + months + "|" + repayAmount;
    }

    public static Transaction fromString(String line) {
        String[] parts = line.split("\\|");
        return new Transaction(
            parts[1], parts[2], Double.parseDouble(parts[3]), Double.parseDouble(parts[4]),
            Double.parseDouble(parts[5]), Double.parseDouble(parts[6]), Double.parseDouble(parts[7]),
            Integer.parseInt(parts[8]), Double.parseDouble(parts[9]), parts[0]
        );
    }
}

class TransactionHistory {
    List<Transaction> transactions = new ArrayList<>();
    final String filename = "transactions.txt";

    public TransactionHistory() {
        loadFromFile();
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
        saveToFile(transaction);  // Auto-save only the new transaction
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(Transaction.fromString(line));
            }
        } catch (IOException e) {
            // It's okay if the file doesn't exist yet
        }
    }

    private void saveToFile(Transaction t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(t.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("❌ Error saving transaction: " + e.getMessage());
        }
    }

    public void printHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        System.out.println("===== Transaction History =====");
        transactions.sort((t1, t2) -> t2.date.compareTo(t1.date)); // Sort by date descending

        System.out.printf("%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-10s %-15s %-15s%n",
            "Date", "Business Type", "Item Name", "Bought Price", "Sold Price",
            "Profit (USD)", "Profit (PHP)", "Amount Lent", "Months", "Repay Amount");
        System.out.println("=====================================================================================================================");

        for (Transaction t : transactions) {
            if (t.businessType.equals("Buy & Sell")) {
                System.out.printf("%-20s %-15s %-15s %-15.2f %-15.2f %-15.2f %-15.2f %-10s %-15s %-15s%n",
                        t.date, t.businessType, t.itemName, t.origPrice, t.resellPrice,
                        t.profitUsd, t.profitPhp, "-", "-", "-");
            } else if (t.businessType.equals("Lending")) {
                System.out.printf("%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-10.2f %-15d %-15.2f%n",
                        t.date, t.businessType, "-", "-", "-",
                        "-", "-", t.amountLent, t.months, t.repayAmount);
            }
        }

        printSummary();
    }

    private void printSummary() {
        double totalPhpProfit = 0;
        double lendingProfit = 0;

        for (Transaction t : transactions) {
            if (t.businessType.equals("Buy & Sell")) {
                totalPhpProfit += t.profitPhp;
            } else if (t.businessType.equals("Lending")) {
                lendingProfit += (t.repayAmount - t.amountLent);
            }
        }

        System.out.println("\n===== Profit Summary =====");
        System.out.printf("Buy & Sell Total Profit (PHP): %.2f%n", totalPhpProfit);
        System.out.printf("Lending Total Profit:          %.2f%n", lendingProfit);
        System.out.printf("Combined Total Profit:         %.2f%n", (totalPhpProfit + lendingProfit));
    }
}

public class Incomemonitor {

    static Scanner scanner = new Scanner(System.in);
    static TransactionHistory history = new TransactionHistory();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the Income Monitoring Program");
            System.out.println("1. Buy and Sell Calculator");
            System.out.println("2. Lending Business Calculator");
            System.out.println("3. View Transaction History");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = getValidInteger(scanner);

            switch (choice) {
                case 1:
                    buyAndSellCalculator();
                    break;
                case 2:
                    lendingBusinessCalculator();
                    break;
                case 3:
                    history.printHistory();
                    break;
                case 4:
                    System.out.println("Exiting the program... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Please input a valid choice!");
            }
        }
    }

    static void buyAndSellCalculator() {
        System.out.println("\n===== Buy and Sell Calculator =====");
        int transactions = getValidInteger(scanner, "How many transactions would you like to record? ");
        double usdToPhpRate = getValidDouble(scanner, "Enter the exchange rate (1 USD to PHP): ");

        for (int i = 0; i < transactions; i++) {
            System.out.println("\n===== Transaction " + (i + 1) + " =====");
            String itemName = getInputString(scanner, "Enter the item name: ");
            double origPrice = getValidDouble(scanner, "Enter the price you bought '" + itemName + "': ");
            double resellPrice = getValidDouble(scanner, "Enter the price you sold '" + itemName + "': ");
            double profitUsd = resellPrice - origPrice;
            double profitPhp = profitUsd * usdToPhpRate;
            String date = getDateInput(scanner);

            Transaction t = new Transaction("Buy & Sell", itemName, origPrice, resellPrice,
                    profitUsd, profitPhp, 0, 0, 0, date);
            history.add(t);
            System.out.println("✔️ Transaction saved!");
        }
    }

    static void lendingBusinessCalculator() {
        System.out.println("\n===== Lending Business Calculator =====");
        int transactions = getValidInteger(scanner, "How many loan transactions would you like to record? ");
        for (int i = 0; i < transactions; i++) {
            double amountLent = getValidDouble(scanner, "Enter the amount lent: ");
            int months = getValidInteger(scanner, "Enter loan duration in months: ");
            double repayAmount = (amountLent * 0.10 * months) + amountLent;
            String date = getDateInput(scanner);

            Transaction t = new Transaction("Lending", "", 0, 0, 0, 0,
                    amountLent, months, repayAmount, date);
            history.add(t);
            System.out.println("✔️ Transaction saved!");
        }
    }

    static int getValidInteger(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return value;
            } else {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine();
            }
        }
    }

    static int getValidInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return value;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    static double getValidDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                return value;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    static String getInputString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    static String getDateInput(Scanner scanner) {
        while (true) {
            System.out.print("Enter transaction date (yyyy-MM-dd): ");
            String date = scanner.nextLine();
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return date;
            }
            System.out.println("Invalid format! Use yyyy-MM-dd.");
        }
    }
}
