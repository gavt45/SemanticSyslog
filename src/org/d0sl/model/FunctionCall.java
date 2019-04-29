package org.d0sl.model;

import org.d0sl.model.expression.Expression;
import org.d0sl.model.expression.ExpressionType;

import java.util.LinkedList;

/**
 * A call of the function with some arguments
 *
 * @version %I%,%G%
 */
public class FunctionCall implements Expression {
    private String functionName;
    private String modelName;
    private LinkedList<Expression> args;

    public FunctionCall() {
        args = new LinkedList<>();
    }

    @Override
    public String toString() {
        String res = "";
        res += modelName + "." + functionName + "(";
        for (int i = 0; i < args.size(); i++)
            if (i == 0) res += args.get(0);
            else res += "," + args.get(i);
        res += ")";
        return res;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.FUNCTION_CALL;
    }

    @Override
    public int getDimension() {
        return args.size();
    }

    @Override
    public Expression getOperand(int index) {
        return args.get(index);
    }

    public void addOperand(Expression op) {
        args.add(op);
    }
}
