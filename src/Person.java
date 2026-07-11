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
