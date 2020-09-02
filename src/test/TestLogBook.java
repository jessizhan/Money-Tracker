import model.LogBook;
import model.Income;
import model.MoneyEntry;
import model.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestLogBook {
    private static final BigDecimal BALANCE = BigDecimal.valueOf(123.45);
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(678.91);

    private LogBook lb;
    private MoneyEntry me;

    @BeforeEach
    void runBefore() {
        lb = new LogBook();
    }

    @Test
    void testGetAndSetBalance() {
        lb.setBalance(BALANCE);
        assertEquals(BALANCE, lb.getBalance());
    }

    @Test
    void testGetAndAddEntriesNoObserver() {
        me = new Income();
        assertEquals("", lb.addEntry(me));
        ArrayList<MoneyEntry> entries = lb.getEntries();
        assertEquals(1, entries.size());
    }

    @Test
    void testPlusNotZero() {
        lb.plus(AMOUNT);
        assertEquals(AMOUNT, lb.getBalance());
    }

    @Test
    void testPlusEqualsZero() {
        lb.setBalance(BigDecimal.ZERO.subtract(AMOUNT));
        lb.plus(AMOUNT);
        assertEquals(BigDecimal.ZERO, lb.getBalance());
    }

    @Test
    void testMinusEqualsZero() {
        lb.setBalance(AMOUNT);
        lb.minus(AMOUNT);
        assertEquals(BigDecimal.ZERO, lb.getBalance());
    }

    @Test
    void testMinusNotZero() {
        lb.minus(AMOUNT);
        assertEquals(BigDecimal.ZERO.subtract(AMOUNT), lb.getBalance());
    }

    @Test
    void testNotifyWithObserver() {
        lb.addObserver(new Printer());
        me = new Income();
        assertEquals("Added entry to Logbook!", lb.addEntry(me));
    }

    @Test
    void testCantAddSameObserverTwice() {
        Printer printer = new Printer();
        assertEquals(0, lb.getObservers().size());
        lb.addObserver(printer);
        assertEquals(1, lb.getObservers().size());
        lb.addObserver(printer);
        assertEquals(1, lb.getObservers().size());
    }


}
