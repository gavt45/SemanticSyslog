package org.d0sl.model.expression;

import java.util.Objects;

/**
 * Represents a basic numeric constant (double).
 *
 * @version %I%,%G%
 */
public class NumericConstant extends Value {
    private Double value;

    public NumericConstant(double value) {
        this.value = value;
    }

    public NumericConstant() {
        value = 0.0;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.NUMERIC_CONSTANT;
    }

    @Override
    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public boolean isLess(NumericConstant other) {return value < other.value;}
    public boolean isLessOrEqual(NumericConstant other) {return value <= other.value;}
    public boolean isGreater(NumericConstant other) {return value > other.value;}
    public boolean isGreaterOrEqual(NumericConstant other) {return value >= other.value;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        NumericConstant that = (NumericConstant) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
