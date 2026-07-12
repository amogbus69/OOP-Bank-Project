/**
 * Generates unique, sequential IDs for users and accounts.
 *
 * MEL-CODE CHANGE: Renamed file from "IDGenerator" to "IDGenerator.java" -
 * Java requires the file name to match the public class name with a .java
 * extension, or the class will not compile.
 *
 * MEL-CODE CHANGE: Added initialize() so the counters can be advanced past
 * whatever IDs/account numbers already exist in the Bank Users file. Without
 * this, newly registered users could collide with IDs/account numbers that
 * were loaded from the CSV.
 */
public class IDGenerator {

    private static int userID = 2000;
    private static int accountID = 100000;

    public static int nextUserID() {

        return userID++;

    }

    public static int nextAccountNumber() {

        return accountID++;

    }

    /**
     * MEL-CODE ADDITION: Advances the internal counters so that newly
     * generated IDs/account numbers never collide with IDs/account numbers
     * already loaded from the Bank Users CSV file. Should be called once,
     * right after the file is read in, with the highest values found.
     *
     * @param maxExistingUserID        highest user ID already in use
     * @param maxExistingAccountNumber highest account number already in use
     */
    public static void initialize(int maxExistingUserID, int maxExistingAccountNumber) {
        if (maxExistingUserID + 1 > userID) {
            userID = maxExistingUserID + 1;
        }
        if (maxExistingAccountNumber + 1 > accountID) {
            accountID = maxExistingAccountNumber + 1;
        }
    }

}