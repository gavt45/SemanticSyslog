package org.d0sl.model.expression;

import java.util.LinkedList;

/**
 * AND logical operation
 *
 * @version %I%,%G%
 */

public class And extends Formula {
    private LinkedList<Expression> operands;

    public And() {
        this.operands = new LinkedList<>();
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LOGICAL_AND;
    }

    @Override
    public int getDimension() {
        return operands.size();
    }

    @Override
    public Expression getOperand(int index) {
        return operands.get(index);
    }

    public void addOperand(Expression op) {
        operands.add(op);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < operands.size(); i++)
            if (i == 0) res.append(operands.get(i));
            else res.append(" AND ").append(operands.get(i));
        return res.toString();
    }
}
