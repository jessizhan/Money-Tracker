import model.Category;
import model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCategory {
    private static final String NAME = "misc.";
    private static final String NAME_2 = "shopping";

    private Category category;

    @BeforeEach
    void runBefore() {
        category = new Category(NAME);
    }

    @Test
    void testGetAndSetName() {
        assertEquals(NAME, category.getName());
        category.setName(NAME_2);
        assertEquals(NAME_2, category.getName());
    }

    @Test
    void testGetAndAddExpense() {
        assertEquals(0, category.getExpenses().size());
        Expense e = new Expense();
        category.addExpense(e);
        assertEquals(1, category.getExpenses().size());
        assertTrue(category.getExpenses().contains(e));
    }

    @Test
    void testEquals() {
        Expense e = new Expense();
        Category c1 = new Category(NAME);
        Category c2 = new Category("fail");
        assertNotEquals(null, category);
        assertNotEquals(category, e);
        assertEquals(category, c1);
        assertNotEquals(category, c2);
    }

    @Test
    void testHashCode() {
        Category c1 = new Category(NAME);
        Category c2 = new Category("fail");
        assertEquals(category.hashCode(), c1.hashCode());
        assertNotEquals(category.hashCode(),c2.hashCode());
    }
}
