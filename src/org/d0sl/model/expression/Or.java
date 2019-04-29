package org.d0sl.model.expression;

import java.util.LinkedList;

/**
 * OR logical operation
 *
 * @version %I%,%G%
 */
public class Or extends Formula {
    private LinkedList<Expression> operands;

    public Or() {
        this.operands = new LinkedList<>();
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LOGICAL_OR;
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
            else res.append(" OR ").append(operands.get(i));
        return res.toString();
    }
}
