import exceptions.ImpossibleAmountException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.OutdatedMoneyTracker;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSaveable {
    private static final String PATH = "./data/testSaveFile.txt";
    private static final String PATH_NO = "./data/nonexistent.txt";
    private static final BigDecimal AMOUNT_ME1 = BigDecimal.valueOf(500);
    private static final BigDecimal AMOUNT_ME2 = BigDecimal.valueOf(200);
    private static final LocalDate DATE = LocalDate.of(2000,2,2);
    private static final String CATEGORY = "shopping";

    private OutdatedMoneyTracker mt;
    private LogBook lb;
    private Income me1;
    private Expense me2;

    @BeforeEach
    void runBefore() {
        mt = new OutdatedMoneyTracker();
        lb = new LogBook();
        me1 = new Income();
        me2 = new Expense();
    }

    @Test
    void testSaveNoException() {
        try {
            setup();

            mt.save(PATH);

            FileInputStream saveFile = new FileInputStream(PATH);
            ObjectInputStream restore = new ObjectInputStream(saveFile);
            LogBook lbSave = (LogBook) restore.readObject();
            restore.close();

            assertEquals(lb.getBalance(), lbSave.getBalance());

            ArrayList<MoneyEntry> lbSaveEntries = lbSave.getEntries();
            assertEquals("gained $" + AMOUNT_ME1 + " on " + DATE, lbSaveEntries.get(0).toString());
            assertEquals("spent $" + AMOUNT_ME2 + " in " + CATEGORY + " on " + DATE, lbSaveEntries.get(1).toString());
        } catch (Exception e) {
            //
        }
    }

    void setup() throws ImpossibleAmountException {
        lb.setBalance(AMOUNT_ME1.subtract(AMOUNT_ME2));
        me1.setAmount(AMOUNT_ME1);
        me1.setDate(DATE);
        me2.setAmount(AMOUNT_ME2);
        me2.setDate(DATE);
        me2.setCategory(new Category(CATEGORY));
        lb.addEntry(me1);
        lb.addEntry(me2);
        mt.setLogbook(lb);
    }
}
