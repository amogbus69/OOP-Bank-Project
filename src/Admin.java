import java.util.*;
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

    public void viewAllMembers(ArrayList<Person> users){
        for(Person user : users){
            System.out.println(user.toString());
        }
    }

    public void viewOneMember(ArrayList<Person> users, String query){
        Person user = this.searchMembers(users, query);
        if(user != null){
            System.out.println(user.toString());
        }
        else{
        System.out.println("User not found");
        }
    }

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
                    System.out.println("Enter new username: ");
                    String newUsername = input.nextLine();
                    user.setUsername(newUsername);
                    break;
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
