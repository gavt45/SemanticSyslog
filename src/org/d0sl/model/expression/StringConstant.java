package org.d0sl.model.expression;

import java.util.Objects;

/**
 * Represents a String constant.
 *
 * @version %I%,%G%
 */
public class StringConstant extends Value {
    private String value;

    public StringConstant(String value) {
        this.value = value;
    }

    public StringConstant() {
        this.value = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        StringConstant that = (StringConstant) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Appends a String to the current value.
     *
     * @param value a String to be appended to the current value.
     */
    public void append(String value) {
        this.value += value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.STRING_CONSTANT;
    }

}
