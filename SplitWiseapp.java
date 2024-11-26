import java.util.ArrayList;
import java.util.Scanner;

// Class to represent a user
class User {
    String name;
    float balance; // Positive = credit, Negative = debt

    User(String name) {
        this.name = name;
        this.balance = 0.0f;
    }
}

public class SplitWiseapp {

    private static final ArrayList<User> users = new ArrayList<>();

    // Method to find a user by name
    private static User findUser(String name) {
        for (User user : users) {
            if (user.name.equals(name)) {
                return user;
            }
        }
        return null;
    }

    // Method to add a new user
    private static void addUser(String name) {
        if (findUser(name) != null) {
            System.out.println("User " + name + " already exists!");
            return;
        }
        users.add(new User(name));
        System.out.println("User " + name + " added.");
    }

    // Method to add an expense
    private static void addExpense(String payer, String category, float amount, String[] recipients) {
        User payerUser = findUser(payer);
        if (payerUser == null) {
            System.out.println("Payer " + payer + " not found!");
            return;
        }

        float splitAmount = amount / recipients.length;

        for (String recipient : recipients) {
            User recipientUser = findUser(recipient);
            if (recipientUser == null) {
                System.out.println("Recipient " + recipient + " not found!");
                return;
            }
            // Update balances
            payerUser.balance += splitAmount;
            recipientUser.balance -= splitAmount;
        }

        System.out.println("Expense added: " + payer + " paid " + amount + " for " + category + ", split among " + recipients.length + " people.");
    }

    // Method to show all balances
    private static void showBalances() {
        System.out.println("\nBalances:");
        for (User user : users) {
            System.out.println(user.name + ": " + user.balance);
        }
    }

    // Method to show who owes whom
    private static void showWhoOwesWhom() {
        System.out.println("\nWho Owes Whom:");
        for (User debtor : users) {
            if (debtor.balance < 0) {
                for (User creditor : users) {
                    if (creditor.balance > 0) {
                        float amountToTransfer = Math.min(-debtor.balance, creditor.balance);
                        if (amountToTransfer > 0) {
                            System.out.println(debtor.name + " owes " + amountToTransfer + " to " + creditor.name);
                            debtor.balance += amountToTransfer;
                            creditor.balance -= amountToTransfer;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Splitwise App ---");
            System.out.println("1. Add User");
            System.out.println("2. Add Expense");
            System.out.println("3. Show Balances");
            System.out.println("4. Show Who Owes Whom");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    addUser(name);
                }
                case 2 -> {
                    System.out.print("Enter payer's name: ");
                    String payer = scanner.nextLine();
                    System.out.print("Enter expense category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    float amount = scanner.nextFloat();
                    System.out.print("Enter number of recipients: ");
                    int numRecipients = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String[] recipients = new String[numRecipients];
                    for (int i = 0; i < numRecipients; i++) {
                        System.out.print("Enter recipient " + (i + 1) + " name: ");
                        recipients[i] = scanner.nextLine();
                    }
                    addExpense(payer, category, amount, recipients);
                }
                case 3 -> showBalances();
                case 4 -> showWhoOwesWhom();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
