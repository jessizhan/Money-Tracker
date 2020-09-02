import model.Category;
import model.SubCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCategoryComponent {
    private static final String CAT_NAME1 = "shopping";
    private static final String CAT_NAME2 = "groceries";
    private static final String SUB_NAME1 = "Zara";
    private static final String SUB_NAME2 = "Walmart";

    private Category c1;
    private Category c2;
    private SubCategory sc1;
    private SubCategory sc2;

    @BeforeEach
    void runBefore() {
        c1 = new Category(CAT_NAME1);
        c2 = new Category(CAT_NAME2);
        sc1 = new SubCategory(SUB_NAME1);
        sc2 = new SubCategory(SUB_NAME2);
    }

    @Test
    public void testDisplayOneCOneSC() {
        assertEquals(CAT_NAME1, c1.display());
        c1.addCategoryComponent(sc1);
        assertEquals(CAT_NAME1 + SUB_NAME1, c1.display());
    }

    @Test
    public void testDisplayOneCTwoSC() {
        c1.addCategoryComponent(sc1);
        c1.addCategoryComponent(sc2);
        assertEquals(CAT_NAME1 + SUB_NAME1+SUB_NAME2, c1.display());
    }

    @Test
    public void testDisplayTwoCTwoSC() {
        c1.addCategoryComponent(sc1);
        c1.addCategoryComponent(c2);
        c2.addCategoryComponent(sc2);
        assertEquals(CAT_NAME1 + SUB_NAME1+CAT_NAME2+SUB_NAME2, c1.display());
    }
}
