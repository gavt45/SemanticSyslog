package org.d0sl.model;

import org.d0sl.model.expression.Expression;
import org.d0sl.model.expression.ExpressionType;
import org.d0sl.model.expression.Formula;

import java.util.LinkedList;

/**
 * A call of the predicate with some arguments
 *
 * @version %I%,%G%
 */
public class PredicateCall extends Formula {
    private String predicateName;
    private String modelName;
    private LinkedList<Expression> args;

    public PredicateCall() {
        args = new LinkedList<>();
    }

    @Override
    public String toString() {
        String res = "";
        res += modelName + "." + predicateName + "(";
        for (int i = 0; i < args.size(); i++)
            if (i == 0) res += args.get(0);
            else res += "," + args.get(i);
        res += ")";
        return res;
    }

    public String getPredicateName() {
        return predicateName;
    }

    public void setPredicateName(String predicateName) {
        this.predicateName = predicateName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.PREDICATE_CALL;
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
