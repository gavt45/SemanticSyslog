package org.d0sl.machine;

import org.d0sl.model.ArgumentDef;
import org.d0sl.model.PredicateDef;
import org.d0sl.model.expression.SemanticValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents a stack used by Semantic Machine when calling predicates.
 * Keeps local variables, arguments values and predicate info for the current call.
 *
 * @version %I%,%G%
 */
public class CalculationStack {
    private LinkedList<LocalContext> stack = new LinkedList<>();

    /**
     * pushes arguments needed to predicate's call
     *
     * @param args arguments to call a predicate (StringConstant, NumericConstant, LogicalConstant)
     *             in correspondence to predicate's arguments order.
     */
    public void push(PredicateDef predicateDef, SemanticValue[] args) throws SemanticException {
        stack.push(new LocalContext(predicateDef, args));
    }

    /**
     * pops the last context
     */
    public Collection<SemanticValue> pop() {
        if (stack.size() == 0) return null;
        LocalContext ctx = stack.pop();
        return ctx.arguments.values();
    }

    /**
     * @return Current predicate in CalculationContext
     */
    public PredicateDef getPredicate() {
        if (stack.isEmpty())
            return null;
        return stack.getFirst().getPredicateDef();
    }

    /**
     * @param name name of predicate's argument
     * @return returns value of the argument
     */
    public SemanticValue getArgument(String name) {
        if (stack.size() == 0)
            return null;
        return stack.getFirst().getArgument(name);
    }

    /**
     * @param name name of the local variable
     * @return returns value of the local variable
     */
    public SemanticValue getLocalVariable(String name) {
        if (stack.size() == 0)
            return null;
        return stack.getFirst().getLocalVariable(name);
    }

    /**
     * @param name  name of the local variable
     * @param value value of the local variable
     */
    public void setLocalVariable(String name, SemanticValue value) {
        stack.getFirst().setLocalVariable(name, value);
    }

    /**
     * @param name name of the range variable
     * @return returns value of the range variable
     */
    public SemanticValue getRangeVariable(String name) {
        if (stack.size() == 0)
            return null;
        return stack.getFirst().getRangeVariable(name);
    }

    /**
     * @param name  name of the range variable
     * @param value value of the range variable
     */
    public void setRangeVariable(String name, SemanticValue value) {
        if (stack.size() != 0) {
            stack.getFirst().setRangeVariable(name, value);
        }
    }

    /**
     * @param name name of the range variable
     */
    public void removeRangeVariable(String name) {
        if (stack.size() != 0) {
            stack.getFirst().removeRangeVariable(name);
        }
    }

    private class LocalContext {
        private HashMap<String, SemanticValue> localVariables = new HashMap<>();
        private HashMap<String, SemanticValue> arguments = new HashMap<>();
        private HashMap<String, SemanticValue> rangeVariables = new HashMap<>();
        private PredicateDef predicateDef;

        public LocalContext(PredicateDef predicateDef, SemanticValue... arguments)
                throws SemanticException {
            this.predicateDef = predicateDef;
            Iterator<ArgumentDef> iter = predicateDef.getArgs().values().iterator();
            for (int i = 0; i < arguments.length; i++) {
                if (iter.hasNext()) {
                    this.arguments.put(iter.next().getArgumentName(), arguments[i]);
                } else throw
                        new SemanticException("The number of call's arguments exceeds the predicate's dimension!");
            }
        }

        public PredicateDef getPredicateDef() {
            return predicateDef;
        }

        public SemanticValue getLocalVariable(String name) {
            return localVariables.get(name);
        }

        public void setLocalVariable(String name, SemanticValue value) {
            localVariables.put(name, value);
        }

        public SemanticValue getRangeVariable(String name) {
            return rangeVariables.get(name);
        }

        public void setRangeVariable(String name, SemanticValue value) {
            rangeVariables.put(name, value);
        }

        public void removeRangeVariable(String name) {
            rangeVariables.remove(name);
        }

        public SemanticValue getArgument(String name) {
            return arguments.get(name);
        }
    }
}
