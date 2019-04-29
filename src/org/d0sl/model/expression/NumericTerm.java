package org.d0sl.model.expression;

import java.util.LinkedList;

/**
 * Represents a basic numeric Expression (or term).
 * Like 345.6 + varname + 56 + 67 + (3-6)
 *
 * @version %I%,%G%
 */
public class NumericTerm implements Expression {
    private LinkedList<Expression> operands = new LinkedList<>();
    private Operation operation = Operation.PLUS;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public int getDimension() {
        return operands.size();
    }

    @Override
    public Expression getOperand(int index) {
        return operands.get(index);
    }

    public void addOperand(Expression operand) {
        operands.add(operand);
    }

    @Override
    public final ExpressionType type() {
        return ExpressionType.NUMERIC_TERM;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < operands.size(); i++)
            if (i == 0) res += operands.get(i);
            else res += " " + operation + " " + operands.get(i);
        return res;
    }

    public static enum Operation {
        PLUS, MINUS, MULTIPLICATION, DIVISION;

        @Override
        public String toString() {
            switch (this) {
                case PLUS:
                    return "+";
                case MINUS:
                    return "-";
                case DIVISION:
                    MINUS:
                    return "/";
                case MULTIPLICATION:
                    MINUS:
                    return "*";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
