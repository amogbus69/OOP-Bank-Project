import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * The base for all account types.
 */
public abstract class Account {

    protected int accountNumber;
    protected double balance;
    private final List<Transaction> transactionHistory = new ArrayList<>();

    public Account(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * @return this account's transaction history, oldest first
     */
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    protected void recordTransaction(Transaction.Type type, double amount, String note) {
        transactionHistory.add(new Transaction(type, amount, note));
    }

    public void deposit(double amount) {
        deposit(amount, null);
    }

    public void deposit(double amount, String note) {

        if(amount > 0) {
            balance += amount;
            recordTransaction(Transaction.Type.DEPOSIT, amount, note);
        }

    }

    public final boolean withdraw(double amount) {
        return withdraw(amount, null);
    }

    public abstract boolean withdraw(double amount, String note);

}
