package org.d0sl.model.expression;


import java.util.LinkedList;

/**
 * Represents an operation of concatenation.
 *
 * @version %I%,%G%
 */

public class StringTerm implements Expression {
    private StringConstant stringConstant; // First String value required
    private LinkedList<Expression> additions;// Some expressions to concatenate

    public StringTerm(String value) {
        this.stringConstant = new StringConstant(value);
        additions = new LinkedList<>();
    }

    public StringConstant getStringConstant() {
        return stringConstant;
    }

    public void setStringConstant(StringConstant stringConstant) {
        this.stringConstant = stringConstant;
    }

    public LinkedList<Expression> getAdditions() {
        return additions;
    }

    public void setAdditions(LinkedList<Expression> additions) {
        this.additions = additions;
    }

    @Override
    public String toString() {
        String res = stringConstant.getValue();
        for (Expression exp : additions) {
            res += " + " + exp;
        }
        return res;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.STRING_TERM;
    }

    @Override
    public int getDimension() {
        return additions.size() + 1;
    }

    @Override
    public Expression getOperand(int index) {
        if (index == 0)
            return stringConstant;
        return additions.get(index - 1);
    }
}
