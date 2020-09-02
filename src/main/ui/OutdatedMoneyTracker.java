package ui;

import exceptions.ImpossibleAmountException;
import exceptions.InvalidOptionException;
import model.*;


import java.io.*;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// References: B04 SimpleCalculator, String to LocalDate: https://www.mkyong.com/java8/java-8-how-to-convert-string-to-localdate/

// ***
// NOTE: This class is NO LONGER OF USE to the project, it was the original UI class when using the console to
//       get user input. This is kept in to test the load() and save() methods that are not implemented in the GUI
// ***

public class OutdatedMoneyTracker implements Loadable, Saveable {

    private static final int NEWENTRY_CMD = 1;
    private static final int VIEWPREV_CMD = 2;
    private static final int VIEWBAL_CMD = 3;
    private static final int QUIT_CMD = 4;
    private static final String DEFAULT_MSG = "That's an invalid option, please try again.";
    private static final String PATH = "./data/saveFile.txt";
    private static final String NEWENTRY_MSG = "add a new entry";
    private static final String VIEWPREV_MSG = "view previous entries";
    private static final String VIEWBAL_MSG = "view current balance";
    private static final String QUIT_MSG = "quit";

    private Scanner scanner;
    private LogBook logbook;

    // EFFECTS: constructs a money tracker with entries
    public OutdatedMoneyTracker() {
        scanner = new Scanner(System.in);
        logbook = new LogBook();
        logbook.addObserver(new Printer());
    }

    // EFFECTS: returns logbook
    public LogBook getLogbook() {
        return logbook;
    }

    // EFFECTS: replace logbook with otherLogBook
    public void setLogbook(LogBook otherLogBook) {
        logbook = otherLogBook;
    }

    // MODIFIES: this
    // EFFECTS: allows user to choose one of the available options to do
    private void entryOptions() {
        while (true) {
            printOptions();

            try {
                int option = getOption();

                if (option == QUIT_CMD) {
                    break;
                } else {
                    startOption(option);
                }
            } catch (InvalidOptionException e) {
                System.out.println(DEFAULT_MSG);
            }


        }
    }

    // MODIFIES: this
    // EFFECTS: starts option chosen by user
    private void startOption(int option) {
        if (option == NEWENTRY_CMD) {
            try {
                addNewEntry();
            } catch (ImpossibleAmountException | DateTimeException e) {
                System.out.println("You've entered an invalid date or amount! Please try again.");
            }
        } else if (option == VIEWPREV_CMD) {
            System.out.println("Previous entries:" + logbook.getEntries());
        } else if (option == VIEWBAL_CMD) {
            System.out.println("Current balance: " + logbook.getBalance());
        }
    }

    // EFFECTS: gets user selected amount, if invalid, throws InvalidOptionException
    private int getOption() throws InvalidOptionException {
        int option = scanner.nextInt();

        if (option > QUIT_CMD) {
            throw new InvalidOptionException();
        }

        System.out.println("You selected: " + optionToText(option));
        return option;
    }

    // EFFECTS: prints the available options
    private void printOptions() {
        System.out.println("Please select an option by entering the corresponding number:");
        System.out.println("[" + NEWENTRY_CMD + "] " + NEWENTRY_MSG);
        System.out.println("[" + VIEWPREV_CMD + "] " + VIEWPREV_MSG);
        System.out.println("[" + VIEWBAL_CMD + "] " + VIEWBAL_MSG);
        System.out.println("[" + QUIT_CMD + "] " + QUIT_MSG);
    }

    // EFFECTS: returns a user chosen option as text
    private String optionToText(int option) {
        if (option == NEWENTRY_CMD) {
            return NEWENTRY_MSG;
        } else if (option == VIEWPREV_CMD) {
            return VIEWPREV_MSG;
        } else if (option == VIEWBAL_CMD) {
            return VIEWBAL_MSG;
        }
        return QUIT_MSG;
    }

    // MODIFIES: this
    // EFFECTS: loads the previous save file if it exists, otherwise does nothing
    private void loadFile() throws IOException, ClassNotFoundException {
        boolean canLoad;

        System.out.println("Do you have a previous file to load? Enter 'true' or 'false'.");
        canLoad = scanner.nextBoolean();

        if (canLoad) {
            load(PATH);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new moneyEntry with date, category and amount spent/gained
    private void addNewEntry() throws ImpossibleAmountException {
        MoneyEntry moneyEntry;

        System.out.println("If this is an expense, enter true. \nOtherwise, enter false for income.");
        boolean isExpense = scanner.nextBoolean();
        scanner.nextLine();

        if (isExpense) {
            moneyEntry = expenseME();
        } else {
            moneyEntry = incomeME();
        }

        System.out.println("You " + moneyEntry.toString());
    }

    // MODIFIES: this
    // EFFECTS: creates a new expense moneyEntry with date, category and amount spent, updates logbook
    private Expense expenseME() throws ImpossibleAmountException {
        Expense moneyEntry = new Expense();

        BigDecimal amount = getUserAmount(moneyEntry);
        getUserDate(moneyEntry);
        getUserCategory(moneyEntry);

        logbook.addEntry(moneyEntry);
        logbook.minus(amount);

        return moneyEntry;
    }

    // MODIFIES: this
    // EFFECTS: creates a new income moneyEntry with date and amount gained, updates logbook
    private Income incomeME() throws ImpossibleAmountException {
        Income moneyEntry = new Income();

        BigDecimal amount = getUserAmount(moneyEntry);
        getUserDate(moneyEntry);

        logbook.addEntry(moneyEntry);
        logbook.plus(amount);

        return moneyEntry;
    }

    // MODIFIES: moneyEntry
    // EFFECTS: gets and sets the user entered date for moneyEntry
    private void getUserDate(MoneyEntry moneyEntry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        System.out.println("Please enter the date in DD/MM/YYYY");
        String date = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(date, formatter);
        moneyEntry.setDate(localDate);
    }

    // MODIFIES: moneyEntry
    // EFFECTS: gets and sets the user entered amount for moneyEntry, returns amount
    private BigDecimal getUserAmount(MoneyEntry moneyEntry) throws ImpossibleAmountException {
        System.out.println("Please enter the amount of your entry");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine(); // clears line
        moneyEntry.setAmount(amount);
        return amount;
    }

    // MODIFIES: moneyEntry
    // EFFECTS: gets and sets the user entered category for moneyEntry
    private void getUserCategory(Expense moneyEntry) {
        System.out.println("Please enter the category of your expense");
        String category = scanner.nextLine();
        moneyEntry.setCategory(new Category(category));
    }


    // EFFECTS: saves the logbook to "saveFile.txt"
    public void save(String path) throws IOException {
        FileOutputStream saveFile = new FileOutputStream(path);
        ObjectOutputStream save = new ObjectOutputStream(saveFile);
        save.writeObject(logbook);
        save.close();
    }

    // MODIFIES: this
    // EFFECTS: loads previous logbook from path
    public void load(String path) throws IOException, ClassNotFoundException {
        FileInputStream saveFile = new FileInputStream(path);
        ObjectInputStream restore = new ObjectInputStream(saveFile);
        LogBook lb = (LogBook) restore.readObject();
        restore.close();

        logbook.plus(lb.getBalance());

        ArrayList<MoneyEntry> entries = lb.getEntries();
        for (MoneyEntry entry : entries) {
            logbook.addEntry(entry);
        }

        System.out.println("................................................................");
        System.out.println("Your previous logbook:\n");
        System.out.println("\tEntries: " + lb.getEntries());
        System.out.println("\tBalance: " + lb.getBalance());
        System.out.println("................................................................");
    }

    // MODIFIES: this
    // EFFECTS: starts the money tracker
    public void run() {
        System.out.println("Hello, welcome to Money Tracker!");
        try {
            loadFile();
            entryOptions();
            save(PATH);
        } catch (IOException ioe) {
            System.out.println("Error: file not found!");
        } catch (ClassNotFoundException ce) {
            System.out.println("Error: class you are trying to load from is not found!");
        } finally {
            System.out.println("Thank you for using Money Tracker.");
        }
    }
}
