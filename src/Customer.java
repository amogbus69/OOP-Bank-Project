import java.util.Collection;
import java.util.Scanner;

/**
 * 
 * a customer is a person who has a checking, saving, and credit 
 * account with the bank.
 *
 * MEL-CODE CHANGE: updateUsername() used ArrayList<Person> and Scanner
 * without importing java.util.ArrayList or java.util.Scanner, which would
 * not compile. Added the java.util.Scanner import and switched the
 * collection parameter to Collection<Person> to match Person.isUniqueUsername.
 */
public class Customer extends Person {

    private Saving saving;
    private Checking checking;
    private Credit credit;

    private int creditScore;

    public Customer(int id,
                    String first,
                    String last,
                    String dob,
                    String address,
                    String phone,
                    String username,
                    String password,
                    int creditScore,
                    Saving saving,
                    Checking checking,
                    Credit credit) {

        super(id,first,last,dob,address,
                phone,username,password,"Customer");

        this.creditScore = creditScore;
        this.saving = saving;
        this.checking = checking;
        this.credit = credit;

    }

    /**
     * 
     * Gets the customers saving account
     * @return the saving account associated with the customer
     */
    public Saving getSaving(){

        return saving;

    }

    /**
     * 
     * Gets the customers checking account
     * @return the checking account associated with the customer
     */
    public Checking getChecking(){

        return checking;

    }

    /**
     * 
     * Gets the customers credit account
     * @return the credit account associated with the customer
     */
    public Credit getCredit(){

        return credit;

    }

    /**
     * MEL-CODE ADDITION: no getter existed for creditScore even though it
     * is stored on every Customer - needed for View Profile / Admin views.
     * @return the customer's credit score
     */
    public int getCreditScore(){
        return creditScore;
    }

    public void updateUsername(Collection<Person> users, Person user, Scanner input){
        while(true){
            System.out.println("Enter new username: ");
            String newUsername = input.nextLine();
            if(user.isUniqueUsername(users, newUsername)){
                user.setUsername(newUsername);
                break;
            }
            else{
                System.out.println("Username taken, try again.");
            }
        }
    }

}
