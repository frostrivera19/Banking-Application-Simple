package transport;

public class Account {

    private String name;
    private final String currency;
    private long number;
    private double currentAmount;
    private final History history = new History();

    public Account(String holder) {
        this(holder, Constants.DEF_CURRENCY);
    }

    public Account(String holder, String actCurrency) {
        name = holder;
        number = createAccountNum();
        currentAmount = 0;
        currency = actCurrency;
    }

    void deposit(double depositAmount) {
        setCurrentAmount(getCurrentAmount() + depositAmount);
        history.insert(Operation.DEPOSIT, depositAmount);
    }

    boolean withdraw(double withdrawAmount) {
        if (currentAmount < withdrawAmount) {
            System.out.println("\nINVALID: Not enough money to withdraw!");
            System.out.println("Your account balance is " + currency
                    + currentAmount + ".\n");
            return false;
        } else {
            currentAmount -= withdrawAmount;
            history.insert(Operation.WITHDRAW, withdrawAmount);
            return true;
        }
    }

    double checkBalance() {
        history.insert(Operation.CHECK_BALANCE, 0);
        return getCurrentAmount();
    }

    String getPreviousTransaction() {
        return history.getLatestTransaction();
    }

    String getHistory() {
        return history.listAllHistory();
    }

    void printAccoutInfo() {
        System.out.println("****************************************");
        System.out.println("Account Holder: " + name);
        System.out.println("Account Number: " + number);
        System.out.println("Current Amount: " + currency
                + String.format("%.2f", currentAmount));
        System.out.println("****************************************");
    }



    //====================helper==========================

    private long createAccountNum() {
        long newAccountNum;
        do {
            newAccountNum = (long) (Math.random()
                    * Math.pow(10, Constants.LEN_ACCT_NUM));
        } while (Bank.accounts.containsKey(newAccountNum));
        return newAccountNum;
    }

    //===================getters setters====================

    String getName() {
        return name;
    }

    long getNumber() {
        return number;
    }

    String getCurrency() {
        return currency;
    }

    double getCurrentAmount() {
        return currentAmount;
    }

    void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }
}
