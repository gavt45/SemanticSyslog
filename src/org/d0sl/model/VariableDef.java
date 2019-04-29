package org.d0sl.model;

import org.d0sl.model.expression.Expression;

/**
 * Represents a variable.
 * A variable must be uniquely defined by name, and by contract.
 *
 * @version %I%,%G%
 */

public class VariableDef {
    private Expression expression;
    private String name;

    public VariableDef() {
    }

    public VariableDef(String name, Expression exp) {
        this.name = name;
        expression = exp;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VariableDef{" +
                "expression=" + expression +
                ", name='" + name + '\'' +
                '}';
    }
}
