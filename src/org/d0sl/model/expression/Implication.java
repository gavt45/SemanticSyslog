package org.d0sl.model.expression;

/**
 * Implication: A->B
 * If-then-[else] statement
 *
 * @version %I%,%G%
 */
public class Implication extends Formula {
    private Expression condition;
    private Expression thenFormula;
    private Expression elseFormula;

    public Implication(Expression condition, Expression thenFormula, Expression elseFormula) {
        this.condition = condition;
        this.thenFormula = thenFormula;
        this.elseFormula = elseFormula;
    }

    public Implication(Expression condition, Expression thenFormula) {
        this.condition = condition;
        this.thenFormula = thenFormula;
        this.elseFormula = null;
    }

    public Implication(Expression condition) {
        this.condition = condition;
        this.thenFormula = null;
        this.elseFormula = null;
    }

    public Implication() {
        this.condition = null;
        this.thenFormula = null;
        this.elseFormula = null;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LOGICAL_IMPLICATION;
    }

    @Override
    public int getDimension() {
        if (elseFormula == null)
            return 2;
        else
            return 3;
    }

    @Override
    public Expression getOperand(int index) {
        switch (index) {
            case 0:
                return condition;
            case 1:
                return thenFormula;
            case 2:
                return elseFormula;
            default:
                throw new IndexOutOfBoundsException("Error when operand's indexing!!!");
        }
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Formula condition) {
        this.condition = condition;
    }

    public Expression getThenFormula() {
        return thenFormula;
    }

    public void setThenFormula(Formula thenFormula) {
        this.thenFormula = thenFormula;
    }

    public Expression getElseFormula() {
        return elseFormula;
    }

    public void setElseFormula(Formula elseFormula) {
        this.elseFormula = elseFormula;
    }

    @Override
    public String toString() {
        return "IF " + condition + " then " + thenFormula + (elseFormula != null ? "else " + elseFormula : "");
    }
}
