import exceptions.ImpossibleAmountException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.OutdatedMoneyTracker;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TestLoadable {
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
    void testLoadAllItemsNoException() {
        try {
            setup();
            helpLoadNoException(mt);
            LogBook lbLoad = mt.getLogbook();
            assertEquals(lb.getBalance(), lbLoad.getBalance());

            ArrayList<MoneyEntry> lbLoadEntries = lbLoad.getEntries();
            assertEquals("gained $" + AMOUNT_ME1 + " on " + DATE, lbLoadEntries.get(0).toString());
            assertEquals("spent $" + AMOUNT_ME2 + " in " + CATEGORY + " on " + DATE, lbLoadEntries.get(1).toString());
        } catch (ClassNotFoundException e) {
            fail("Error: ClassNotFoundException when class exists.");
        } catch (IOException e) {
            fail("Error: IOException when path exists.");
        } catch (Exception e) {
            //
        }
    }

    @Test
    void testLoadAllItemsException() {
        try {
            setup();
            helpLoadException(mt);
            fail("Did not catch IOException or ClassNotFoundException");
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException caught!");
        } catch (IOException e) {
            System.out.println("IOException caught!");
        } catch (Exception e) {
            //
        }
    }

    void helpLoadNoException(Loadable mt) throws IOException, ClassNotFoundException {
        mt.load(PATH);
    }

    void helpLoadException(Loadable mt) throws IOException, ClassNotFoundException {
        mt.load(PATH_NO);
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

