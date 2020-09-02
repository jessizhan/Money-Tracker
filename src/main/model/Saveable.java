package model;

import java.io.IOException;

public interface Saveable {

    // EFFECTS: saves content to a file
    void save(String path) throws IOException;
}
