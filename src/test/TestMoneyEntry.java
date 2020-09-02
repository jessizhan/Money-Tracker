import exceptions.ImpossibleAmountException;
import model.Expense;
import model.MoneyEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class TestMoneyEntry {
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(123.45);
    public static final BigDecimal AMOUNT_IMP = BigDecimal.valueOf(-5);
    public static final LocalDate DATE = LocalDate.of(2000,2,2);
    public MoneyEntry moneyEntry;

    @BeforeEach
    abstract void runBefore();

    @Test
    void testGetAndSetAmountPossible() {
        try {
            moneyEntry.setAmount(AMOUNT);
        } catch (ImpossibleAmountException ee) {
            fail("Threw ImpossibleAmountException when amount was valid.");
        }
        assertEquals(AMOUNT, moneyEntry.getAmount());
    }

    @Test
    void testGetAndSetAmountImpossible() {
        try {
            moneyEntry.setAmount(AMOUNT_IMP);
            fail("Did not throw ImpossibleAmountException when amount was invalid.");
        } catch (ImpossibleAmountException ee) {
            System.out.println("Caught the invalid amount!");
        }
        assertEquals(BigDecimal.ZERO, moneyEntry.getAmount());
    }

    @Test
    void testGetAndSetDate() {
        moneyEntry.setDate(DATE);
        assertEquals(DATE, moneyEntry.getDate());
    }

    @Test
    abstract void testToString() ;
}
