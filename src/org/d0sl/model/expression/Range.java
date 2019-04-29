package org.d0sl.model.expression;

/**
 * Represents a range definition in ForAll statement
 *
 * @version %I%,%G%
 */
public class Range {
    private String name;

    public Range(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
