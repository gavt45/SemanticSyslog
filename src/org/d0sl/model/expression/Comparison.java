package org.d0sl.model.expression;

/**
 * Comparison of two expressions.
 * <, >, <=, >=, !=, ==
 *
 * @version %I%,%G%
 */
public class Comparison extends Formula {
    private Expression left;
    private Expression right;
    private Operation operation = Operation.EQUAL;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LOGICAL_COMPARISON;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Expression getOperand(int index) {
        switch (index) {
            case 0:
                return left;
            case 1:
                return right;
            default:
                throw new IndexOutOfBoundsException("Error when operand's indexing!!!");
        }
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return left + " " + operation + " " + right;
    }

    public static enum Operation {
        LESS, GREATER, LESS_OR_EQUAL, GREATER_OR_EQUAL, NOT_EQUAL, EQUAL;

        @Override
        public String toString() {
            switch (this) {
                case LESS:
                    return "<";
                case GREATER:
                    return ">";
                case LESS_OR_EQUAL:
                    return "<=";
                case GREATER_OR_EQUAL:
                    return ">=";
                case NOT_EQUAL:
                    return "!=";
                case EQUAL:
                    return "==";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
