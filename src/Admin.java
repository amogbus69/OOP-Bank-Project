import java.util.*;
/**
 * 
 * An admin is a person who is able to manage all other members
 * of the bank.
 */
public class Admin extends Person{

    public Admin(int userID,
                 String firstName,
                 String lastName,
                 String dateOfBirth,
                 String address,
                 String phoneNumber,
                 String username,
                 String password){

        super(userID, firstName, lastName, dateOfBirth, address,
                phoneNumber, username, password, "Admin");

    }

    public void addMembers(ArrayList<Person> users, Person newUser){
        users.add(newUser);
    }

    /**
     * 
     * Allows the Admin to see all members in the system.
     * Will print all information for each member.
     * @param users all the users in the system
     */
    public void viewAllMembers(ArrayList<Person> users){
        for(Person user : users){
            System.out.println(user.toString());
        }
    }

    /**
     * 
     * Allows the Admin to search for one member.
     * If the member is found all their information will be shown.
     * @param users all the users in the system.
     * @param query the information the admin is using to search
     */
    public void viewOneMember(ArrayList<Person> users, String query){
        Person user = this.searchMembers(users, query);
        if(user != null){
            System.out.println(user.toString());
        }
        else{
        System.out.println("User not found");
        }
    }

    /**
     * 
     * A private method to facilitate searching for members based on
     * their userID, name, or username.
     * @param users all users in the system
     * @param query the the information the admin is using to search
     * @return the user if found, or null if not found
     */
    private Person searchMembers(ArrayList<Person> users, String query){
        for(Person user : users){
            if((String.valueOf(user.getUserID())).equals(query)){
                return user;
            }
            if(user.getFirstName().equals(query) || user.getLastName().equals(query)){
                return user;
                }
            if(user.getUsername().equals(query)){
                return user;
            }
        }
        return null;
    }

    /**
     * 
     * Allows the admin to update a members name, username,
     * or password.
     * @param users all users in the system.
     * @param query the information the admin is using to search
     * @param input the scanner being used by the system from RunBank
     */
    public void updateMember(ArrayList<Person> users, String query, Scanner input){
        Person user = this.searchMembers(users, query);
        if(user != null){
            System.out.println("Enter a number from 1-4");
            System.out.println("1: Update first name");
            System.out.println("2: Update last name");
            System.out.println("3: Update username");
            System.out.println("4: Update password");

            int choice = input.nextInt();
            input.nextLine();

            switch(choice){
                case 1:
                    System.out.println("Enter new first name: ");
                    String newFirstName = input.nextLine();
                    user.setFirstName(newFirstName);
                    break;
                case 2:
                    System.out.println("Enter new last name: ");
                    String newLastName = input.nextLine();
                    user.setLastName(newLastName);
                    break;
                case 3:
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
                case 4:
                    System.out.println("Enter new password: ");
                    String newPassword = input.nextLine();
                    user.setPassword(newPassword);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
        else{
        System.out.println("User not found");
        }
    }

    /**
     * 
     * Allows the admin to delete a member after confirming
     * they want to delete them.
     * @param users all users in the system.
     * @param query the information the admin is using to search
     * @param input the scanner being used by the system from RunBank
     */
    public void deleteMember(ArrayList<Person> users, String query, Scanner input){
        Person user = this.searchMembers(users, query);
        if(user != null){
            System.out.println("Do you want to delete this member?");
            System.out.println("Enter Yes to confirm");
            String choice = input.nextLine();

            if(choice.toLowerCase().equals("yes")){
                users.remove(user);
                System.out.println("Member deleted.");
            }
        }
        else{
        System.out.println("User not found");
        }
    }
}
