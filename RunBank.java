public class RunBank {
    public static MainMenu(){
         while (!input.equalsIgnoreCase("EXIT")) {
            System.out.println("\n~~~~~ TICKETMINER MAIN MENU ~~~~~");
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
        exit.program();
    }
    public static void showRegisterMenu() {
        System.out.println("\n===== REGISTER =====");
        System.out.println("1. As a Customer");
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
}
