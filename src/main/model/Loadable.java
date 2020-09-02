package model;

import java.io.IOException;

public interface Loadable {

    // EFFECTS: loads content from a file
    void load(String path) throws IOException, ClassNotFoundException;
}

