package model;

import model.observer.Observer;

public class Printer implements Observer {
    private static final String TEXT = "Added entry to Logbook!";

    // EFFECTS: creates a new Printer
    public Printer() {
    }

    // EFFECTS: prints and returns TEXT
    @Override
    public String update() {
        System.out.println(TEXT);
        return TEXT;
    }
}
