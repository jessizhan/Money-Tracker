import exceptions.ImpossibleAmountException;
import model.Category;
import model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExpense extends TestMoneyEntry {
    private static final String CATEGORY = "shopping";

    private Expense expense;

    @Override
    @BeforeEach
    void runBefore() {
        expense = new Expense();
        moneyEntry = new Expense();
    }

    @Test
    void testGetAndSetCategory() {
        Category c = new Category(CATEGORY);
        expense.setCategory(c);
        assertEquals(c, expense.getCategory());
        expense.setCategory(c);

    }

    @Override
    @Test
    void testToString() {
        try {
            expense.setAmount(AMOUNT);
            expense.setCategory(new Category(CATEGORY));
            expense.setDate(DATE);

            assertEquals("spent $" + AMOUNT + " in " + CATEGORY + " on " + DATE, expense.toString());
        } catch (Exception e) {
            //
        }
    }

}
