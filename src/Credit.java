/**
 * 
 * A type of account where the user can withdraw money up to
 * a certain limit. The limit is based on their credit score.
 */
public class Credit extends Account{
    
    private double creditLimit;
    private double availableCredit;

    public Credit(double creditLimit, double availableCredit, int accountNumber){
        super(accountNumber,0);

        this.creditLimit = creditLimit;
        this.availableCredit = availableCredit;
    }

    public boolean withdraw(double amount){
        if(amount <= availableCredit){

            availableCredit -= amount;
            return true;

        }

        return false;
    }
    
}
