package org.d0sl.model.expression;

import org.d0sl.model.VariableRef;

public class Assignment extends Formula {
    private VariableRef variable;
    private Expression value;

    public Assignment() {
    }

    public Assignment(VariableRef var, Expression val) {
        value = val;
        variable = var;
    }

    @Override
    public ExpressionType type() {
        return null;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public Expression getOperand(int index) {
        if (index == 0)
            return variable;
        if (index == 1)
            return value;
        throw new IndexOutOfBoundsException("Error in Operand's indexing");
    }

    public VariableRef getVariable() {
        return variable;
    }

    public void setVariable(VariableRef variable) {
        this.variable = variable;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
