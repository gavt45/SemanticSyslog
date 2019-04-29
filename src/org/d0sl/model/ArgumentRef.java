package org.d0sl.model;


import org.d0sl.model.expression.Expression;
import org.d0sl.model.expression.ExpressionType;

/**
 * Represents a reference to argument of predicate
 *
 * @version %I%,%G%
 */
public class ArgumentRef implements Expression {
    private String modelName;
    private String callName;
    private String argumentName;

    public ArgumentRef(String argumentName) {
        this.argumentName = argumentName;
    }

    public ArgumentRef(String modelName, String callName, String argumentName) {
        this.modelName = modelName;
        this.callName = callName;
        this.argumentName = argumentName;
    }

    public ArgumentRef(String callName, String argumentName) {
        this.callName = callName;
        this.argumentName = argumentName;
    }

    @Override
    public String toString() {
        return "" + modelName + '.' + callName + '.' + argumentName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getArgumentName() {
        return argumentName;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.ARGUMENT_REF;
    }

    @Override
    public int getDimension() {
        return 0;
    }

    @Override
    public Expression getOperand(int index) {
        return null;
    }
}
