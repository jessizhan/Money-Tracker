package model;

public class Expense extends MoneyEntry {
    private Category category;

    // EFFECTS: constructs a money entry with the amount spent, category and date
    public Expense() {
        super();
        category = new Category("misc.");
    }

    // MODIFIES: this
    // EFFECTS: sets this.category to be category
    public void setCategory(Category category) {
        if (!(this.category == category)) {
            this.category = category;
            category.addExpense(this);
        }
    }

    // EFFECTS: returns this.category
    public Category getCategory() {
        return category;
    }

    // EFFECTS: returns the beginning part for toString()
    @Override
    public String beginningString() {
        return "spent";
    }

    // EFFECTS: returns category part for toString()
    @Override
    public String addCategoryString() {
        return " in " + category.getName();
    }
}
