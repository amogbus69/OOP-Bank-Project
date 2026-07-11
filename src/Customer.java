/**
 * 
 * a customer is a person who has a checking, saving, and credit 
 * account with the bank.
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

    public void updateUsername(ArrayList<Person> users, Person user, Scanner input){
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
