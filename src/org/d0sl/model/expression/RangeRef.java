package org.d0sl.model.expression;

/**
 * Represents a range reference in ForAll statement
 *
 * @version %I%,%G%
 */
public class RangeRef implements Expression {
    private String name;

    public String getName() {
        return name;
    }

    public RangeRef(String name) {
        this.name = name;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.RANGE_REF;
    }

    @Override
    public int getDimension() {
        return 0;
    }

    @Override
    public Expression getOperand(int index) {
        return null;
    }
}
