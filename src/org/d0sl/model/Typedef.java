package org.d0sl.model;

/**
 * Represents a domain specific type
 *
 * @version %I%,%G%
 */
public class Typedef {
    private String name;

    public Typedef() {
    }

    public Typedef(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
