package org.d0sl.model.expression;

import java.util.Objects;

/**
 * Represents a logical constant with Logical value.
 *
 * @version %I%,%G%
 */
public class LogicalConstant extends Formula implements SemanticValue {
    private Logical value;

    public LogicalConstant() {
        this.value = Logical.FALSE;
    }

    public LogicalConstant(Logical value) {
        this.value = value;
    }

    public LogicalConstant(boolean b) {
        if(b)
            value = Logical.TRUE;
        else
            value = Logical.FALSE;
    }

    @Override
    public int getDimension() {
        return 0;
    }

    @Override
    public Logical getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        LogicalConstant that = (LogicalConstant) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public void setValue(Logical value) {
        this.value = value;
    }

    @Override
    public Expression getOperand(int index) {
        return null;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LOGICAL_CONSTANT;
    }
}
