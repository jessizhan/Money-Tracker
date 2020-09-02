package model;

import model.observer.Subject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class LogBook extends Subject implements Serializable {
    private BigDecimal balance;
    private ArrayList<MoneyEntry> entries;

    // EFFECTS: constructs a LogBook with a balance and entries list
    public LogBook() {
        balance = BigDecimal.ZERO;
        entries = new ArrayList<>();
    }

    // EFFECTS: returns this.balance
    public BigDecimal getBalance() {
        return balance;
    }

    // EFFECTS: returns this.entries
    public ArrayList<MoneyEntry> getEntries() {
        return entries;
    }

    // MODIFIES: this
    // EFFECTS: sets this.balance to be balance
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // MODIFIES: this
    // EFFECTS: adds amount to balance
    public void plus(BigDecimal amount) {
        BigDecimal newBalance = balance.add(amount);
        fixZeroFormat(newBalance);
    }

    // MODIFIES: this
    // EFFECTS: subtracts amount from balance
    public void minus(BigDecimal amount) {
        BigDecimal newBalance = balance.subtract(amount);
        fixZeroFormat(newBalance);
    }

    // MODIFIES: this
    // EFFECTS: checks if newBalance is zero to enter it in the correct format, balance = newBalance
    private void fixZeroFormat(BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
            balance = BigDecimal.ZERO;
        } else {
            balance = newBalance;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds entry to entries, returns value of notifyObservers()
    public String addEntry(MoneyEntry entry) {
        entries.add(entry);
        return notifyObservers();
    }

}
