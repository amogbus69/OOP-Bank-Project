import java.util.Collection;

/**
 * 
 * The base for all other members of the bank. Has all identifying 
 * information for a person.
 *
 * MEL-CODE CHANGE: isUniqueUsername() used ArrayList<Person> without ever
 * importing java.util.ArrayList, which would not compile. Changed the
 * parameter type to Collection<Person> (and added the java.util import)
 * so this works no matter which collection type RunBank stores users in
 * (we now use a HashMap, see RunBank/BankFileManager for why).
 */
public class Person {

    protected int userID;
    protected String firstName;
    protected String lastName;
    protected String dateOfBirth;
    protected String address;
    protected String phoneNumber;
    protected String username;
    protected String password;
    protected String userType;

    public Person(int userID, String firstName, String lastName,
                  String dateOfBirth, String address,
                  String phoneNumber, String username,
                  String password, String userType) {

        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    /**
     * MEL-CODE ADDITION: getDateOfBirth/getAddress/getPhoneNumber were
     * missing entirely, even though the field is set in the constructor.
     * These are needed for the "View Profile" menu option and for writing
     * the updated Bank Users CSV back out on exit.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String newFirstName){
        this.firstName = newFirstName;
    }

    public void setLastName(String newLastName){
        this.lastName = newLastName;
    }

    public boolean isUniqueUsername(Collection<Person> users, String newUsername){
        for(Person user: users){
            if(user.getUsername().equals(newUsername)){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {

        return "User ID: " + userID +
                "\nName: " + firstName + " " + lastName +
                "\nDOB: " + dateOfBirth +
                "\nAddress: " + address +
                "\nPhone: " + phoneNumber +
                "\nUsername: " + username +
                "\nPassword: " + password +
                "\nUser Type: " + userType;
    }
}
