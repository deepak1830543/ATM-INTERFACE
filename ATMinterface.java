import java.util.Scanner;

class Account {
    private String userID;
    private String password;
    private double balance;
    private String transactionHistory;

    public Account(String userID, String password, double balance) {
        this.userID = userID;
        this.password = password;
        this.balance = balance;
        this.transactionHistory = "";
    }

    public String getUserID() {
        return userID;
    }

    public boolean verifyPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory += "Deposit: +" + amount + "\n";
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory += "Withdrawal: -" + amount + "\n";
            return true;
        }
        return false;
    }

    public void transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory += "Transfer: -" + amount + " to " + recipient.getUserID() + "\n";
           System.out.println("Transanction successfull.");
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for " + userID + ":");
        System.out.println(transactionHistory);
    }
}

public class ATMinterface {
    public static void main(String[] args) {
        Account account1 = new Account("user123", "password123", 1000);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter user ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (account1.getUserID().equals(userID) && account1.verifyPassword(password)) {
            System.out.println("Login successful!");

            int choice;
            do {
                System.out.println("\nChoose an option:");
                System.out.println("1. Transaction history");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        account1.printTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        if (account1.withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        account1.deposit(depositAmount);
                        System.out.println("Deposit successful.");
                        break;
                    case 4:
                        System.out.print("Enter recipient's user ID: ");
                        String recipientID = scanner.next();
                        Account recipientAccount = (recipientID.equals(account1.getUserID())) ? account1 : new Account(recipientID, "", 0);
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        account1.transfer(recipientAccount, transferAmount);
                        

                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);
        } else {
            System.out.println("Invalid user ID or password. Login failed.");
        }

        scanner.close();
    }
}
