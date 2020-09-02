import exceptions.ImpossibleAmountException;
import model.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestIncome extends TestMoneyEntry {
    private Income income;

    @BeforeEach
    void runBefore() {
        income = new Income();
        moneyEntry = new Income();
    }

    @Test
    void testToString() {
        try {
            income.setAmount(AMOUNT);
            income.setDate(DATE);

            assertEquals("gained $" + AMOUNT + " on " + DATE, income.toString());
        } catch (Exception e) {
            //
        }
    }

}
