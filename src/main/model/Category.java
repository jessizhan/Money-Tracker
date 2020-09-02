package model;

import model.composite.CategoryComponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable, CategoryComponent {
    private String name;
    private List<Expense> expenses;
    private List<CategoryComponent> categoryComponents;

    // EFFECTS: constructs new category with given name, empty list of expenses, empty categoryComponents list
    public Category(String name) {
        this.name = name;
        expenses = new ArrayList<>();
        categoryComponents = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: sets name of category to string name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns string name
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds given expense to list of expenses
    public void addExpense(Expense expense) {
        if (!expenses.contains(expense)) {
            expenses.add(expense);
            expense.setCategory(this);
        }
    }

    // EFFECTS: returns this.expenses
    public List<Expense> getExpenses() {
        return expenses;
    }

    // EFFECTS: override Category objects of the same name to be equal
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;

        return name.equals(category.getName());
    }

    // EFFECTS: override Category objects of the same name to have the same hash code
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    // MODIFIES: this
    // EFFECTS: adds cc to this.categoryComponents
    public void addCategoryComponent(CategoryComponent cc) {
        categoryComponents.add(cc);
    }

    // EFFECTS: prints name of this and all this.categoryComponents
    @Override
    public String display() {
        System.out.println(name);
        StringBuilder returnString = new StringBuilder(name);

        for (CategoryComponent c: categoryComponents) {
            returnString.append(c.display());
        }
        return returnString.toString();
    }
}
