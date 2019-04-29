package org.d0sl.machine;

import org.d0sl.model.ArgumentDef;
import org.d0sl.model.PredicateDef;
import org.d0sl.model.expression.ExpressionType;

import java.util.Arrays;

/**
 * Describes and identifies a predicate in semantic model.
 *
 * @version %I%,%G%
 */

public class PredicateDescriptor {
    private PredicateDef definition;
    private ArgumentDef[] args;

    @Override
    public String toString() {
        return "PredicateDescriptor{" +
                "definition=" + definition +
                ", args=" + Arrays.toString(args) +
                '}';
    }

    public PredicateDescriptor(PredicateDef definition) {
        this.definition = definition;
        args = new ArgumentDef[definition.getArgs().values().size()];
        int i = 0;
        for (ArgumentDef arg: definition.getArgs().values()) {
            args[i++] = arg;
        }
    }

    public int dimension() {
        return definition.getDimension();
    }

    public ExpressionType getArgumentType(int index) {
        return args[index].getArgumentType();
    }

    public String getContractName() {
        return definition.getModelName();
    }

    public String getPredicateName() {
        return definition.getPredicateName();
    }
}
