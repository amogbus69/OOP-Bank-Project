/**
 * 
 * A type of account where the user can withdraw (charge) money up to
 * a certain limit. The limit is based on their credit score.
 *
 * MEL-CODE CHANGE: The original class kept "availableCredit" as its own
 * field, completely disconnected from the inherited "balance" field
 * (which was always hardcoded to 0). That meant:
 *   - There was no way to load a customer's real "Credit Starting Balance"
 *     from the CSV (e.g. -786.93, meaning they've already charged $786.93).
 *   - There was no field for "Credit Max" at all, so the credit limit
 *     given at registration could never be persisted or displayed.
 *   - deposit() (inherited from Account) added straight to "balance",
 *     which was never used anywhere, so paying down a balance had no
 *     effect on availableCredit.
 *
 * Fix: balance (inherited from Account) now represents the amount already
 * charged, as a value <= 0 (matching the CSV's convention, e.g. -786.93).
 * creditMax is the credit limit. Available credit is derived as
 * (creditMax + balance) instead of being tracked separately, so it can
 * never drift out of sync with balance.
 */
public class Credit extends Account{

    private double creditMax;

    /**
     * @param accountNumber the account number
     * @param creditMax the credit limit assigned at registration
     * @param startingBalance amount already charged, as a value <= 0
     *                        (e.g. -786.93 means $786.93 already owed)
     */
    public Credit(int accountNumber, double creditMax, double startingBalance){
        super(accountNumber, startingBalance);

        this.creditMax = creditMax;
    }

    public double getCreditMax(){
        return creditMax;
    }

    /**
     * @return how much of the credit limit is still available to spend
     */
    public double getAvailableCredit(){
        return creditMax + balance;
    }

    /**
     * Charges an amount to the credit account. Cannot exceed the credit
     * max (assumption per assignment: no minimum payment, no interest).
     */
    @Override
    public boolean withdraw(double amount, String note){
        if(amount > 0 && amount <= getAvailableCredit()){

            balance -= amount;
            recordTransaction(Transaction.Type.WITHDRAW, amount, note);
            return true;

        }

        return false;
    }

    /**
     * Pays down the credit balance. Per assignment assumptions, all
     * payments go entirely towards the principal, and the balance should
     * never go positive (you can't "overpay" past $0 owed).
     */
    @Override
    public void deposit(double amount, String note){
        if(amount > 0){
            balance = Math.min(0, balance + amount);
            recordTransaction(Transaction.Type.DEPOSIT, amount, note);
        }
    }

}
