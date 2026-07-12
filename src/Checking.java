/**
 * 
 * A type of account where the owner of the account can withdraw
 * their money.
 */
public class Checking extends Account {

    public Checking(int accountNumber) {
        super(accountNumber,0);
    }

    /**
     * MEL-CODE ADDITION: the CSV file gives us a "Checking Starting
     * Balance" for existing customers - the original constructor always
     * hardcoded the balance to 0, so there was no way to load existing
     * customers with their real balance. This overload lets the file
     * reader pass that starting balance in.
     * @param accountNumber the account number
     * @param startingBalance the starting balance from the Bank Users file
     */
    public Checking(int accountNumber, double startingBalance) {
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
