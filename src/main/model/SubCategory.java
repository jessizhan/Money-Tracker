package model;

import model.composite.CategoryComponent;

public class SubCategory implements CategoryComponent {
    private String name;

    // EFFECTS: creates new SubCategory with name
    public SubCategory(String name) {
        this.name = name;
    }

    @Override
    // EFFECTS: prints and returns this.name
    public String display() {
        System.out.println(name);
        return name;
    }
}
