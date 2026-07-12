# OOP-Bank-Project — El Paso Miners Bank

Console-based, menu-driven banking application for CS 3331 (Project Part 1).

Make sure to send a message in Teams about what part you are working on so others don't work on the same part!

## How to run

From the repository root:

```bash
cd src
javac -d ../out *.java
cd ..
cp BankUsers.csv out/BankUsers.csv
cd out
java RunBank
```

The program reads `BankUsers.csv` (must be in the current working directory
when you run `java RunBank`) at startup, and rewrites it with any updates
when you choose **Exit** from the main menu.

## Project structure

| File | Purpose |
|---|---|
| `RunBank.java` | Main method and all menu-driven console flows |
| `Person.java` | Base class for anyone in the system (fields + shared behavior) |
| `Customer.java`, `BankManager.java`, `Admin.java` | `Person` subtypes |
| `Account.java` | Abstract base class for accounts |
| `Checking.java`, `Saving.java`, `Credit.java` | `Account` subtypes |
| `BankFileManager.java` | Reads `BankUsers.csv` into `Person`/`Customer` objects and writes it back out on exit |
| `IDGenerator.java` | Generates unique user IDs and account numbers |
| `Logger1.java` | Appends timestamped action entries to `log.txt` across sessions |
| `BankUsers.csv` | Seed data loaded at startup |

## Data structure notes

Users are stored in memory as `Map<Integer, Person>`, keyed by user ID.
User IDs are already required to be unique, and Admin operations
(add/update/delete/search-by-ID) are the most frequent operations after
login, so a `HashMap` gives O(1) lookup/add/remove by ID instead of the
O(n) linear scan an `ArrayList` would require. Search by name or username
(which isn't the map key) is still O(n), same as it would be with a list.

## mel-code branch — changes from amogbus-code

`amogbus-code` did not compile. This branch starts from `amogbus-code`
(the most complete branch at the time) and fixes the following so the
project builds and runs end-to-end. See inline `MEL-CODE CHANGE` /
`MEL-CODE ADDITION` comments in each file for details.

**Compile-breaking issues fixed:**
- `IDGenerator` and `Logger1` had no `.java` file extension.
- `RunBank.java` had a stray closing brace in `registerMenu()` that broke
  the `if`/`else if` chain.
- Missing `java.util` imports for `ArrayList`/`Scanner`/`Collection` in
  `Person.java`, `Customer.java`.
- `RunBank.java` called `LoginMenu()` and `exitProgram()`, neither of
  which was defined anywhere.

**Logic bugs fixed:**
- `Admin.updateMember()` was missing a `break` after the username-update
  case, so it fell through and immediately forced a password change too.
- `Credit.java` tracked `availableCredit` as a field completely
  disconnected from `balance` (which was hardcoded to 0), so a real
  starting balance from the CSV could never be loaded or displayed, and
  paying down a balance had no effect on available credit. Redesigned so
  `balance` (<= 0) represents the amount already charged and available
  credit is derived as `creditMax + balance`.
- `RunBank.registerCustomer()` never read Date of Birth into a variable,
  never created the `Customer`/accounts, never assigned a credit
  score/limit, and never stored the new user anywhere.

**Added:**
- `BankFileManager.java` — reads `BankUsers.csv` into `Person`/`Customer`
  objects (skipping blank rows, handling quoted commas in addresses) and
  writes the current users back out on exit.
- Full Register / Login / Exit flow, Customer menus (Manage Transactions,
  View Balance, Manage Profile), and Admin menus (Manage Users: Add,
  View, Update, Delete), wired to `IDGenerator` and `Logger1`.
- Overloaded constructors on `Checking`/`Saving` and a redesigned
  `Credit` constructor so accounts loaded from the CSV keep their real
  starting balances.

## Assumptions

- Credit score is randomly generated in the 300-850 range at
  registration (the CSV file doesn't include a credit score column, so
  customers loaded from the file default to a placeholder score of 0 —
  their real credit limit/balance are still loaded correctly).
- "Transfer to an external account" withdraws from the chosen internal
  account and logs the transfer; since external accounts are outside
  this system, there's nothing on our end to deposit into or validate.
- Bank Manager login currently shows a placeholder message, since Bank
  Manager functionality is explicitly scoped to a later part of the
  assignment.
