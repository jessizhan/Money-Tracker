package model.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers;

    // EFFECTS: creates a new Subject with an empty observers list
    protected Subject() {
        observers = new ArrayList<>();
    }

    // EFFECTS: returns this.observers
    public List<Observer> getObservers() {
        return observers;
    }

    // MODIFIES: this
    // EFFECTS: add observer to this.observers if not already in the list
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    // EFFECTS: calls update() on each Observer o in this.observers, returns value of update()
    protected String notifyObservers() {
        String returnS = "";
        for (Observer o: observers) {
            returnS = o.update();
        }
        return returnS;
    }
}
