package org.d0sl.model.expression;

/**
 * NOT logical operation
 *
 * @version %I%,%G%
 */
public class Not extends Formula {
    private Expression original;

    public Not(Expression original) {
        this.original = original;
    }

    public Not() {
        original = null;
    }

    public void setOriginal(Expression original) {
        this.original = original;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LOGICAL_NOT;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public Expression getOperand(int index) {
        switch (index) {
            case 0:
                return original;
            default:
                throw new IndexOutOfBoundsException("Error when operand's indexing!!!");
        }
    }

    @Override
    public String toString() {
        return "NOT(" + original + ')';
    }
}
