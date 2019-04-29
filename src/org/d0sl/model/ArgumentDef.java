package org.d0sl.model;

import org.d0sl.model.expression.ExpressionType;

/**
 * Represents an argument of predicate
 *
 * @version %I%,%G%
 */
public class ArgumentDef {
    private String modelName;
    private String callName;
    private String argumentName;
    private ExpressionType argumentType;
    private String typeReference;

    public String getTypeReference() {
        return typeReference;
    }

    public void setTypeReference(String typeReference) {
        this.typeReference = typeReference;
    }

    public ArgumentDef(String modelName, String callName, String argumentName) {
        this.modelName = modelName;
        this.callName = callName;
        this.argumentName = argumentName;
    }

    public ArgumentDef(String callName, String argumentName) {
        this.callName = callName;
        this.argumentName = argumentName;
    }

    public ArgumentDef(String argumentName) {
        this.argumentName = argumentName;
    }

    public ExpressionType getArgumentType() {
        return argumentType;
    }

    public void setArgumentType(ExpressionType argumentType) {
        this.argumentType = argumentType;
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
    public String toString() {
        return "" + argumentType + ':' + argumentName;
    }
}
