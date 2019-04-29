package org.d0sl.model.expression;

/**
 * Helps to for logging expressions
 */
public class Logging implements Expression {
    private Expression origin;

    public Logging(Expression origin) {
        this.origin = origin;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LOGGING;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public Expression getOperand(int index) {
        if(index ==0)
            return origin;
        throw new IndexOutOfBoundsException("Error when operand's indexing!!!");
    }
}
