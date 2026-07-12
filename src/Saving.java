/**
 * 
 * A type of account where the owner of the account can store and 
 * withdraw their money.
 */
public class Saving extends Account {

    public Saving(int accountNumber) {
        super(accountNumber,0);
    }

    /**
     * MEL-CODE ADDITION: mirrors the Checking.java fix - lets the file
     * reader load a customer's real "Savings Starting Balance" from the
     * CSV instead of always starting at 0.
     * @param accountNumber the account number
     * @param startingBalance the starting balance from the Bank Users file
     */
    public Saving(int accountNumber, double startingBalance) {
        super(accountNumber, startingBalance);
    }

    @Override
    public boolean withdraw(double amount) {

        if(amount <= balance){

            balance -= amount;
            return true;

        }

        return false;

    }

}
