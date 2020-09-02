package model;

import exceptions.ImpossibleAmountException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class MoneyEntry implements Serializable {
    private BigDecimal amount;
    private LocalDate date;

    // EFFECTS: creates a new MoneyEntry object with amount 0 and date today
    public MoneyEntry() {
        amount = BigDecimal.ZERO;
        date = LocalDate.now();
    }

    // EFFECTS: returns this.amount
    public BigDecimal getAmount() {
        return amount;
    }

    // EFFECTS: returns this.date
    public LocalDate getDate() {
        return date;
    }

    // MODIFIES: this
    // EFFECTS: sets amount spent/gained on an entry, throws ImpossibleAmountException for negative amount
    public void setAmount(BigDecimal amount) throws ImpossibleAmountException {
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new ImpossibleAmountException();
        } else {
            this.amount = amount;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the date of an entry
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // EFFECTS: returns the details of the entry
    public String toString() {
        return beginningString() + " $" + amount + addCategoryString() + " on " + date;
    }

    // EFFECTS: returns beginning string for toString()
    public abstract String beginningString();

    // EFFECTS: returns category string for toString()
    public abstract String addCategoryString();
}
