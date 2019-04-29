package org.d0sl.model.expression;

/**
 * Represents a parentheses that contains other Expression
 * Like (expression)
 *
 * @version %I%,%G%
 */
public class Parentheses implements Expression {
    private Expression expression;

    public Parentheses(Expression expression) {
        this.expression = expression;
    }

    public Parentheses() {
        this.expression = null;
    }


    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public final ExpressionType type() {
        return ExpressionType.PARENTHESES;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public Expression getOperand(int index) {
        if (index == 0) {
            return expression;
        }
        throw new IndexOutOfBoundsException("Error when operand's indexing!!!");
    }

    @Override
    public String toString() {
        return "(" + expression + ')';
    }
}
