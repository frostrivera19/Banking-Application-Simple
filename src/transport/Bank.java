package transport;

import java.util.HashMap;
import java.util.Scanner;

public class Bank {
    final static HashMap<Long, Account> accounts = new HashMap<>();

    public Account newAccount(String holder, String currency) {
        Account acct = new Account(holder, currency);
        accounts.put(acct.getNumber(), acct);
        System.out.println("New Account with ID " + acct.getNumber()
                + " for " + holder + " successfully created.\n");
        return acct;
    }

    public Account newAccount(String holder) {
        return newAccount(holder, Constants.DEF_CURRENCY);
    }

    public void openHomePage() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        System.out.println("WELCOME TO BANK OF AMERICA");
        System.out.println("+++++++++++++++++++++++++++++++++++++++\n");

        final Scanner scan = new Scanner(System.in);

        homePageOptions();
        String response = scan.nextLine();

        while (!response.equals("4")) {
            switch(response) {
                case "1" -> {
                    System.out.print("\nEnter your account number: ");
                    long acctNum = Long.parseLong(scan.nextLine());
                    if (!accounts.containsKey(acctNum)) {
                        System.out.println("\nINVALID: No Such Account.\n");
                    } else {
                        logIn(accounts.get(acctNum));
                    }
                }
                case "2" -> {
                    System.out.print("\nEnter the new account holder's name: ");
                    String holder = scan.nextLine();
                    newAccount(holder);
                }
                case "3" -> {
                    System.out.println("\nListing all accounts...\n");
                    listAllAccounts();
                }
                default -> System.out.println("\nInvalid Response.\n");
            }
            scan.nextLine();
            homePageOptions();
            response = scan.nextLine();
        }

        scan.close();
    }

    private void listAllAccounts() {
        System.out.println("Account Number | Holder Name");
        System.out.println("-----------------------------");
        int count = 1;
        for (long acctNum : accounts.keySet()) {
            System.out.println(count + ". " + acctNum + " | "
                    + accounts.get(acctNum).getName());
            count++;
        }
        System.out.println();
    }

    private void homePageOptions() {
        System.out.println("*****************************************");
        System.out.println("What would you like to do today?\n");
        System.out.println("1. Log into my account");
        System.out.println("2. Open a new account");
        System.out.println("3. List all accounts");
        System.out.println("4. Exit\n");
        System.out.print("Enter an option: ");
    }

    private void logIn(Account acct) {

        if (acct == null || !accounts.containsKey(acct.getNumber())) {
            System.out.println("\nINVALID: No such account.\n");
            return;
        }

        loginPage(acct);

        final Scanner scan = new Scanner(System.in);
        String response = scan.nextLine();

        while (!response.equals("G")) {
            switch (response) {
                // check balance
                case "A" -> {
                    System.out.println("\n----------------------------");
                    System.out.println("Your Balance: " + acct.checkBalance());
                    System.out.println("----------------------------\n");
                }
                // deposit
                case "B" -> deposit(acct, scan);
                // withdraw
                case "C" -> withdraw(acct, scan);
                // previous transaction
                case "D" -> System.out.println("\nPrevious transaction: "
                        + acct.getPreviousTransaction() + "\n");
                // view history
                case "E" -> {
                    System.out.println();
                    System.out.println(acct.getHistory());
                }
                // close acct
                case "F" -> {
                    closeAcct(acct, scan);
                    return;
                }
                default -> System.out.println("\nInvalid Response.\n");
            }
            scan.nextLine();
            loginPage(acct);
            response = scan.nextLine();
        }
        scan.close();
    }


    // ========================helpers============================


    private void loginPage(Account acct) {
        System.out.println("WELCOME " + acct.getName());
        System.out.println("Your ID is " + acct.getNumber());
        System.out.println();
        System.out.println("A. Check Balance");
        System.out.println("B. Deposit");
        System.out.println("C. Withdraw");
        System.out.println("D. Previous Transaction");
        System.out.println("E. View History");
        System.out.println("F. Close Account");
        System.out.println("G. Log Out");
        System.out.println("*************************************");
        System.out.println("ENTER AN OPTION");
        System.out.println("*************************************");
    }


    private void deposit(Account acct, Scanner scan) {
        System.out.println("\n----------------------------");
        System.out.println("Enter amount to deposit: ");
        System.out.println("----------------------------\n");
        System.out.print(acct.getCurrency());
        double dep = Double.parseDouble(scan.nextLine());
        acct.deposit(dep);
        System.out.println("Deposit of " + acct.getCurrency() + dep
                + " successful.");
        System.out.println("Your new account balance is "
                + acct.getCurrency()
                + acct.getCurrentAmount());
    }

    private void withdraw(Account acct, Scanner scan) {
        System.out.println("\n----------------------------");
        System.out.println("Enter amount to withdraw: ");
        System.out.println("----------------------------\n");
        System.out.print(acct.getCurrency());
        double with = Double.parseDouble(scan.nextLine());
        boolean withdrawn = acct.withdraw(with);
        if (withdrawn) {
            System.out.println("Withdrawal of " + acct.getCurrency() + with
                    + " successful.");
            System.out.println("Your new account balance is "
                    + acct.getCurrency()
                    + acct.getCurrentAmount());
        }
    }

    private void closeAcct(Account acct, Scanner scan) {
        System.out.print("\nDelete Account Permanently. Confirm? "
                + "Y/N ");
        String confirm = scan.nextLine();
        if (confirm.equals("Y")) {
            accounts.remove(acct.getNumber());
            System.out.println("\n----------------------------");
            System.out.println("Account successfully deleted.");
            System.out.println("----------------------------\n");
        } else {
            System.out.println("\nAction aborted. Account not "
                    + "deleted.\n");
        }
    }

}
