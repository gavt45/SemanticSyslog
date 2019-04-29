package org.d0sl.model;

import org.d0sl.model.expression.Expression;
import org.d0sl.model.expression.ExpressionType;
import org.d0sl.model.expression.StringConstant;

/**
 * Represents a reference to variable.
 * A variable must be uniquely defined by name, and by contract.
 *
 * @version %I%,%G%
 */

public class VariableRef implements Expression {
    private String name;
    private String modelName;

    public VariableRef() {
    }

    public VariableRef(String name, String modelName) {
        this.name = name;
        this.modelName = modelName;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.VARIABLE_REF;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public Expression getOperand(int index) {
        if (index == 0)
            return new StringConstant(modelName + "." + name);
        else
            throw new IndexOutOfBoundsException("Illegal access to StringConstant operand");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return modelName + "." + name;
    }
}
