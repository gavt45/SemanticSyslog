package org.d0sl.model;

import org.d0sl.model.expression.ExpressionType;

import java.util.LinkedHashMap;

/**
 * Represents an external function's definition with arguments
 *
 * @version %I%,%G%
 */
public class FunctionDef {
    private String modelName;
    private String functionName;
    private LinkedHashMap<String, ArgumentDef> args = new LinkedHashMap<>();
    private ExpressionType resultType;
    private String typeReference = "unknown";

    public String getTypeReference() {
        return typeReference;
    }

    public void setTypeReference(String typeReference) {
        this.typeReference = typeReference;
    }

    public FunctionDef(String modelName, String functionName, ExpressionType resultType, ArgumentDef... args) {
        this.modelName = modelName;
        this.functionName = functionName;
        this.resultType = resultType;
        for (ArgumentDef arg : args) {
            this.args.put(arg.getArgumentName(), arg);
        }
    }

    public FunctionDef(String functionName, ExpressionType resultType, ArgumentDef... args) {
        this.functionName = functionName;
        this.resultType = resultType;
        for (ArgumentDef arg : args) {
            this.args.put(arg.getArgumentName(), arg);
        }
    }

    public FunctionDef(String functionName, ExpressionType resultType) {
        this.functionName = functionName;
        this.resultType = resultType;
    }


    public FunctionDef(String modelName, String functionName, ExpressionType resultType) {
        this.modelName = modelName;
        this.functionName = functionName;
        this.resultType = resultType;
    }

    public ExpressionType getResultType() {
        return resultType;
    }

    public void setResultType(ExpressionType resultType) {
        this.resultType = resultType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String toString() {
        return "FunctionDef{" +
                "modelName='" + modelName + '\'' +
                ", functionName='" + functionName + '\'' +
                ", args=" + args +
                ", result=" + resultType +
                '}';
    }

    public int getDimension() {
        return args.size();
    }

    public void addArgument(ArgumentDef arg) {
        args.put(arg.getArgumentName(), arg);
    }

    public LinkedHashMap<String, ArgumentDef> getArgs() {
        return args;
    }
}
