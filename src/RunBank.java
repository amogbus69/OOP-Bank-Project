import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Course: CS 3331 - Advanced Object-Oriented Programming
 * Instructor: Dr. Bhanukiran Gurijala
 * Project: Part 1 - Bank
 * 
 

 * @author Natalia Mendoza, 
 */

public class RunBank {

    /** scanner for the program */
    static Scanner scanner = new Scanner(System.in);
    /** Date formatter */
    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/DD/YYYY");

    /**Time formatter */
    static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a");

    // Main Method
    /**
     * 
     * @param args
     * 
     */

    public static void main(String[] args){
        System.out.println("Bank Miner!");
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
                RegisterMenu();
            } else if (input.equals("2")) {
                LoginMenu();
            } else if (!input.equalsIgnoreCase("EXIT")) {
                System.out.println("Invalid option. Please try again.");
            }
        }

        exitProgram();
    }
    public static void RegisterMenu() {
        System.out.println("\nREGISTER");
        System.out.println("1. Customer");
        System.out.println("2. Back");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            registerCustomer();
        } 
        } else if (choice.equals("2")) {
            return;
        } else {
            System.out.println("Invalid option.");
        }
    }
    public static void registerCustomer(){
        System.out.println("Register:");
        
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Date of Birth: ");

        System.out.print("Address: ");
        String address = scanner.nextLine().trim();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();

    }
   
}
