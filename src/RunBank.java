import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Course: CS 3331 - Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: Part 1 - Bank
 *
 * @author Natalia Mendoza, and team (mel-code branch fixes by Melanie)
 *
 * Honesty Statement: Team members collaborated on this project as
 * permitted by the assignment; no outside sources or non-team members
 * contributed code.
 */
public class RunBank {

    /** scanner for the program */
    static Scanner scanner = new Scanner(System.in);

    /** in-memory store of every user in the system, keyed by user ID */
    static Map<Integer, Person> users;

    /** path to the Bank Users CSV file, loaded at startup and rewritten on exit */
    static final String DATA_FILE = "BankUsers.csv";

    /**
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println("Welcome to El Paso Miners Bank!");
        users = BankFileManager.readUsers(DATA_FILE);
        mainMenu();
    }

    public static void mainMenu() {
        String input = "";
        while (!input.equalsIgnoreCase("EXIT")) {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("Type EXIT to quit");
            System.out.print("Enter choice: ");

            input = scanner.nextLine().trim();

            if (input.equals("1")) {
                registerMenu();
            } else if (input.equals("2")) {
                loginMenu();
            } else if (!input.equalsIgnoreCase("EXIT")) {
                System.out.println("Invalid option. Please try again.");
            }
        }

        exitProgram();
    }

    public static void registerMenu() {
        System.out.println("\nREGISTER");
        System.out.println("1. Customer");
        System.out.println("2. Back");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            registerCustomer();
            // Per assignment 4.a.v: after registering, redisplay this menu.
            registerMenu();
        } else if (choice.equals("2")) {
            return;
        } else {
            System.out.println("Invalid option.");
            registerMenu();
        }
    }

    public static void registerCustomer() {
        System.out.println("\nRegister as Customer:");

        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Date of Birth: ");
        String dob = scanner.nextLine().trim();

        System.out.print("Address: ");
        String address = scanner.nextLine().trim();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();

        int userID = IDGenerator.nextUserID();
        String username = generateUsername(firstName, lastName);
        String password = generatePassword();

        // ASSUMPTION: the assignment allows either accepting a credit score
        // from the user or randomly generating one - we randomly generate
        // one in the 300-850 range typical of real-world credit scores.
        int creditScore = new Random().nextInt(551) + 300; // 300-850 inclusive
        double creditLimit = randomCreditLimit(creditScore);

        Checking checking = new Checking(IDGenerator.nextAccountNumber());
        Saving saving = new Saving(IDGenerator.nextAccountNumber());
        Credit credit = new Credit(IDGenerator.nextAccountNumber(), creditLimit, 0);

        Customer customer = new Customer(userID, firstName, lastName, dob, address,
                phoneNumber, username, password, creditScore, saving, checking, credit);

        users.put(userID, customer);

        Logger1.log("New customer registered: " + username + " (ID " + userID + ")");

        System.out.println("\nRegistration complete!");
        System.out.println("User ID: " + userID);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Checking Account #: " + checking.getAccountNumber());
        System.out.println("Savings Account #: " + saving.getAccountNumber());
        System.out.println("Credit Account #: " + credit.getAccountNumber()
                + " (limit $" + String.format("%.2f", creditLimit) + ")");
    }

    public static void loginMenu() {
        System.out.println("\nLOGIN");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Person user = findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Invalid username or password.");
            return;
        }

        Logger1.log("User " + username + " logged in.");

        if (user.getUserType().equals("Customer")) {
            customerMenu((Customer) user);
        } else if (user.getUserType().equals("Admin")) {
            adminMenu((Admin) user);
        } else if (user.getUserType().equals("Bank Manager")) {
            // ASSUMPTION: Bank Manager functionality is explicitly called
            // out in the assignment as "Functionality in the next part."
            System.out.println("Bank Manager functionality is coming in the next part of the project.");
        } else {
            System.out.println("Unrecognized user type.");
        }
    }

    // ----------------------------------------------------------------
    // Customer menus
    // ----------------------------------------------------------------

    public static void customerMenu(Customer customer) {
        String choice = "";
        while (!choice.equals("4")) {
            System.out.println("\nCUSTOMER MENU (" + customer.getUsername() + ")");
            System.out.println("1. Manage Transactions");
            System.out.println("2. View Balance");
            System.out.println("3. Manage Profile");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // ASSUMPTION: Manage Transactions is deferred to a later
                    // part of the project (see teammate PR feedback).
                    System.out.println("Manage Transactions is coming in a later part of the project.");
                    break;
                case "2":
                    viewBalanceMenu(customer);
                    break;
                case "3":
                    manageProfileMenu(customer);
                    break;
                case "4":
                    Logger1.log("User " + customer.getUsername() + " logged out.");
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // MEL-CODE CHANGE: Manage Transactions (deposit/withdraw/transfer) is
    // deferred to a later part of the project per teammate feedback on the
    // Part 1 PR. The full implementation (manageTransactionsMenu and its
    // helpers) is preserved on the 'manage-transactions-wip' branch.

    public static void viewBalanceMenu(Customer customer) {
        String choice = "";
        while (!choice.equals("5")) {
            System.out.println("\nVIEW BALANCE");
            System.out.println("1. All Accounts");
            System.out.println("2. Checking Account");
            System.out.println("3. Saving Account");
            System.out.println("4. Credit Card");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    printCheckingBalance(customer);
                    printSavingBalance(customer);
                    printCreditBalance(customer);
                    break;
                case "2":
                    printCheckingBalance(customer);
                    break;
                case "3":
                    printSavingBalance(customer);
                    break;
                case "4":
                    printCreditBalance(customer);
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void printCheckingBalance(Customer customer) {
        Checking c = customer.getChecking();
        System.out.println("Checking - Account #" + c.getAccountNumber()
                + ", Balance: $" + String.format("%.2f", c.getBalance()));
    }

    private static void printSavingBalance(Customer customer) {
        Saving s = customer.getSaving();
        System.out.println("Saving - Account #" + s.getAccountNumber()
                + ", Balance: $" + String.format("%.2f", s.getBalance()));
    }

    private static void printCreditBalance(Customer customer) {
        Credit c = customer.getCredit();
        System.out.println("Credit - Account #" + c.getAccountNumber()
                + ", Balance: $" + String.format("%.2f", c.getBalance())
                + ", Credit Limit: $" + String.format("%.2f", c.getCreditMax())
                + ", Available Credit: $" + String.format("%.2f", c.getAvailableCredit()));
    }

    public static void manageProfileMenu(Customer customer) {
        String choice = "";
        while (!choice.equals("3")) {
            System.out.println("\nMANAGE PROFILE");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println(customer.toString());
                    break;
                case "2":
                    updateProfileMenu(customer);
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void updateProfileMenu(Customer customer) {
        String choice = "";
        while (!choice.equals("5")) {
            System.out.println("\nUPDATE PROFILE");
            System.out.println("1. Change username");
            System.out.println("2. Change password");
            System.out.println("3. Change address");
            System.out.println("4. Change phone number");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    customer.updateUsername(users.values(), customer, scanner);
                    Logger1.log("User " + customer.getUserID() + " updated their username.");
                    break;
                case "2":
                    System.out.print("Enter new password: ");
                    customer.setPassword(scanner.nextLine().trim());
                    Logger1.log("User " + customer.getUsername() + " changed their password.");
                    System.out.println("Password updated.");
                    break;
                case "3":
                    System.out.print("Enter new address: ");
                    customer.setAddress(scanner.nextLine().trim());
                    Logger1.log("User " + customer.getUsername() + " changed their address.");
                    System.out.println("Address updated.");
                    break;
                case "4":
                    System.out.print("Enter new phone number: ");
                    customer.setPhoneNumber(scanner.nextLine().trim());
                    Logger1.log("User " + customer.getUsername() + " changed their phone number.");
                    System.out.println("Phone number updated.");
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ----------------------------------------------------------------
    // Admin menus
    // ----------------------------------------------------------------

    public static void adminMenu(Admin admin) {
        String choice = "";
        while (!choice.equals("2")) {
            System.out.println("\nADMIN MENU (" + admin.getUsername() + ")");
            System.out.println("1. Manage Users");
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");

            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    manageUsersMenu(admin);
                    break;
                case "2":
                    Logger1.log("Admin " + admin.getUsername() + " logged out.");
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void manageUsersMenu(Admin admin) {
        String choice = "";
        while (!choice.equals("5")) {
            System.out.println("\nMANAGE USERS");
            System.out.println("1. Add");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addMemberFlow(admin);
                    break;
                case "2":
                    viewMemberFlow(admin);
                    break;
                case "3":
                    System.out.print("Enter ID, name, or username to search: ");
                    admin.updateMember(users, scanner.nextLine().trim(), scanner);
                    break;
                case "4":
                    System.out.print("Enter ID, name, or username to search: ");
                    admin.deleteMember(users, scanner.nextLine().trim(), scanner);
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addMemberFlow(Admin admin) {
        System.out.println("Add which type of member?");
        System.out.println("1. Customer");
        System.out.println("2. Bank Manager");
        System.out.println("3. Admin");
        System.out.print("Enter choice: ");
        String type = scanner.nextLine().trim();

        if (type.equals("1")) {
            // Adding a Customer follows the same flow as self-registration.
            registerCustomer();
            return;
        }

        if (!type.equals("2") && !type.equals("3")) {
            System.out.println("Invalid option.");
            return;
        }

        System.out.print("First name: ");
        String first = scanner.nextLine().trim();
        System.out.print("Last name: ");
        String last = scanner.nextLine().trim();
        System.out.print("Date of Birth: ");
        String dob = scanner.nextLine().trim();
        System.out.print("Address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine().trim();

        int id = IDGenerator.nextUserID();
        String username = generateUsername(first, last);
        String password = generatePassword();

        Person newMember;
        if (type.equals("2")) {
            newMember = new BankManager(id, first, last, dob, address, phone, username, password);
        } else {
            newMember = new Admin(id, first, last, dob, address, phone, username, password);
        }

        admin.addMembers(users, newMember);
        Logger1.log("Admin " + admin.getUsername() + " added new " + newMember.getUserType()
                + " " + username + " (ID " + id + ")");

        System.out.println("Member added. User ID: " + id + ", Username: " + username
                + ", Password: " + password);
    }

    private static void viewMemberFlow(Admin admin) {
        System.out.println("1. Display all members");
        System.out.println("2. Search for a member");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            admin.viewAllMembers(users);
        } else if (choice.equals("2")) {
            System.out.print("Enter ID, name, or username: ");
            admin.viewOneMember(users, scanner.nextLine().trim());
        } else {
            System.out.println("Invalid option.");
        }
    }

    // ----------------------------------------------------------------
    // Shared helpers
    // ----------------------------------------------------------------

    /**
     * Looks up a user by username. O(n) since users are keyed by ID, not
     * username - acceptable since login happens far less often than
     * ID-based admin operations.
     */
    private static Person findByUsername(String username) {
        for (Person person : users.values()) {
            if (person.getUsername().equals(username)) {
                return person;
            }
        }
        return null;
    }

    private static String generateUsername(String firstName, String lastName) {
        String base = (firstName + lastName).toLowerCase().replaceAll("[^a-z]", "");
        if (base.isEmpty()) {
            base = "user";
        }
        String candidate = base;
        int suffix = 1;
        while (!isUsernameUnique(candidate)) {
            candidate = base + suffix;
            suffix++;
        }
        return candidate;
    }

    private static boolean isUsernameUnique(String candidate) {
        for (Person person : users.values()) {
            if (person.getUsername().equals(candidate)) {
                return false;
            }
        }
        return true;
    }

    private static String generatePassword() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789!@#$";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Randomly generates a credit limit based on the ranges given in the
     * assignment's credit-score table.
     */
    private static double randomCreditLimit(int creditScore) {
        Random random = new Random();
        if (creditScore <= 580) {
            return 100 + random.nextInt(600); // $100 - $699
        } else if (creditScore <= 669) {
            return 700 + random.nextInt(4300); // $700 - $4999
        } else if (creditScore <= 739) {
            return 5000 + random.nextInt(2500); // $5000 - $7499
        } else if (creditScore <= 799) {
            return 7500 + random.nextInt(8500); // $7500 - $15999
        } else {
            return 16000 + random.nextInt(9001); // $16000 - $25000
        }
    }

    public static void exitProgram() {
        BankFileManager.writeUsers(DATA_FILE, users);
        Logger1.log("Program exited; Bank Users file updated.");
        System.out.println("Bank Users file saved. Goodbye!");
    }

}
