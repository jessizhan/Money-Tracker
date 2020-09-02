package model;

public class Income extends MoneyEntry {

    // EFFECTS: constructs a money entry with the amount spent and date
    public Income() {
        super();
    }

    // EFFECTS: returns the beginning part for toString()
    @Override
    public String beginningString() {
        return "gained";
    }

    // EFFECTS: returns empty string for no category
    @Override
    public String addCategoryString() {
        return "";
    }
}
