import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MEL-CODE ADDITION: This whole class is new.
 *
 * Reads the Bank Users CSV file into memory as Person/Customer objects,
 * and writes the (possibly updated) user data back out to CSV on exit.
 *
 * Data structure choice: users are returned as a Map<Integer, Person>
 * keyed by user ID.
 *   - Time complexity: user IDs are already required to be unique, and
 *     the Admin "Update"/"Delete"/lookup-by-ID operations are performed
 *     very frequently relative to registration, so O(1) get/put/remove
 *     by ID (HashMap) beats O(n) linear search (ArrayList) once the bank
 *     has more than a handful of customers.
 *   - Space complexity: a HashMap has some overhead per entry versus an
 *     ArrayList, but for a bank's user base (hundreds to low thousands of
 *     rows) that overhead is negligible next to the lookup speed gained.
 *   - Search by name/username is still O(n) (see Admin.searchMembers),
 *     since those aren't used as the map key, but that's an acceptable
 *     trade-off since ID-based operations are the common case.
 */
public class BankFileManager {

    private static final String[] HEADER = {
            "Identification Number", "First Name", "Last Name", "Date of Birth",
            "Address", "Phone Number", "Username", "Password", "User Type",
            "Checking Account Number", "Checking Starting Balance",
            "Savings Account Number", "Savings Starting Balance",
            "Credit Account Number", "Credit Max", "Credit Starting Balance"
    };

    /**
     * Reads the Bank Users CSV file at the given path and builds the
     * appropriate Person subtype (Customer / BankManager / Admin) for each
     * row. Blank rows and rows with an unrecognized User Type are skipped.
     * After loading, IDGenerator is advanced past the highest user ID and
     * account number found, so newly registered users/accounts can never
     * collide with data loaded from the file.
     *
     * ASSUMPTION: the CSV does not store a credit score column, so
     * customers loaded from the file are given a placeholder credit score
     * of 0 (their real Credit Max/starting balance are still loaded
     * correctly - only the score used at *registration time* is unknown
     * for pre-existing customers).
     *
     * @param filePath path to the Bank Users CSV file
     * @return a map of userID -> Person for every valid row in the file
     */
    public static Map<Integer, Person> readUsers(String filePath) {
        Map<Integer, Person> users = new HashMap<>();
        int maxUserID = 0;
        int maxAccountNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // header row - skip it

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip blank trailing lines
                }

                String[] fields = parseCsvLine(line);
                if (fields.length < 9 || fields[0].trim().isEmpty()) {
                    continue; // skip malformed/blank rows
                }

                try {
                    int id = Integer.parseInt(fields[0].trim());
                    String first = fields[1].trim();
                    String last = fields[2].trim();
                    String dob = fields[3].trim();
                    String address = fields[4].trim();
                    String phone = fields[5].trim();
                    String username = fields[6].trim();
                    String password = fields[7].trim();
                    String userType = fields[8].trim();

                    Person person;

                    if (userType.equalsIgnoreCase("Customer")) {
                        int checkingNum = parseIntOrZero(get(fields, 9));
                        double checkingBal = parseDoubleOrZero(get(fields, 10));
                        int savingNum = parseIntOrZero(get(fields, 11));
                        double savingBal = parseDoubleOrZero(get(fields, 12));
                        int creditNum = parseIntOrZero(get(fields, 13));
                        double creditMax = parseDoubleOrZero(get(fields, 14));
                        double creditBal = parseDoubleOrZero(get(fields, 15));

                        Checking checking = new Checking(checkingNum, checkingBal);
                        Saving saving = new Saving(savingNum, savingBal);
                        Credit credit = new Credit(creditNum, creditMax, creditBal);

                        // Credit score isn't stored in the file - see class-level assumption note.
                        person = new Customer(id, first, last, dob, address, phone,
                                username, password, 0, saving, checking, credit);

                        maxAccountNumber = Math.max(maxAccountNumber,
                                Math.max(checkingNum, Math.max(savingNum, creditNum)));

                    } else if (userType.equalsIgnoreCase("Bank Manager")) {
                        person = new BankManager(id, first, last, dob, address, phone, username, password);

                    } else if (userType.equalsIgnoreCase("Admin")) {
                        person = new Admin(id, first, last, dob, address, phone, username, password);

                    } else {
                        System.out.println("Skipping row with unrecognized User Type \"" + userType + "\".");
                        continue;
                    }

                    users.put(id, person);
                    maxUserID = Math.max(maxUserID, id);

                } catch (NumberFormatException e) {
                    System.out.println("Skipping malformed row (bad number): " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Bank Users file not found at \"" + filePath + "\". Starting with no users.");
        } catch (IOException e) {
            System.out.println("Error reading Bank Users file: " + e.getMessage());
        }

        IDGenerator.initialize(maxUserID, maxAccountNumber);
        return users;
    }

    /**
     * Writes the current set of users back out to a Bank Users CSV file,
     * in the same column format as the original input file. Called when
     * the user selects Exit from the main menu.
     *
     * @param filePath destination path for the CSV file
     * @param users    the current users to write out
     */
    public static void writeUsers(String filePath, Map<Integer, Person> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.join(",", HEADER));
            writer.newLine();

            for (Person person : users.values()) {
                writer.write(toCsvRow(person));
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing Bank Users file: " + e.getMessage());
        }
    }

    private static String toCsvRow(Person person) {
        StringBuilder row = new StringBuilder();
        row.append(person.getUserID()).append(",");
        row.append(csvEscape(person.getFirstName())).append(",");
        row.append(csvEscape(person.getLastName())).append(",");
        row.append(csvEscape(person.getDateOfBirth())).append(",");
        row.append(csvEscape(person.getAddress())).append(",");
        row.append(csvEscape(person.getPhoneNumber())).append(",");
        row.append(csvEscape(person.getUsername())).append(",");
        row.append(csvEscape(person.getPassword())).append(",");
        row.append(csvEscape(person.getUserType()));

        if (person instanceof Customer) {
            Customer customer = (Customer) person;
            row.append(",").append(customer.getChecking().getAccountNumber());
            row.append(",").append(customer.getChecking().getBalance());
            row.append(",").append(customer.getSaving().getAccountNumber());
            row.append(",").append(customer.getSaving().getBalance());
            row.append(",").append(customer.getCredit().getAccountNumber());
            row.append(",").append(customer.getCredit().getCreditMax());
            row.append(",").append(customer.getCredit().getBalance());
        } else {
            row.append(",,,,,,,"); // 7 blank account columns for non-customers
        }

        return row.toString();
    }

    private static String csvEscape(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private static String get(String[] fields, int index) {
        return index < fields.length ? fields[index] : "";
    }

    private static int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static double parseDoubleOrZero(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Parses one line of CSV text, respecting double-quoted fields that
     * may themselves contain commas (e.g. addresses like
     * "500 W. University Ave, El Paso, TX 79968"). A naive split(",")
     * would incorrectly break addresses like that into extra fields.
     *
     * @param line a raw line of CSV text
     * @return the individual field values, in order
     */
    private static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        current.append('"'); // escaped quote inside a quoted field
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    current.append(c);
                }
            } else {
                if (c == '"') {
                    inQuotes = true;
                } else if (c == ',') {
                    fields.add(current.toString());
                    current.setLength(0);
                } else {
                    current.append(c);
                }
            }
        }
        fields.add(current.toString());

        return fields.toArray(new String[0]);
    }
}
