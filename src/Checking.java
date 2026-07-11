public class Checking extends Account {

    public Checking(int accountNumber) {
        super(accountNumber,0);
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
