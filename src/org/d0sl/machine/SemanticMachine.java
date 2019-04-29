package org.d0sl.machine;

import org.d0sl.model.expression.LogicalConstant;
import org.d0sl.model.expression.SemanticValue;

/**
 * Represents abstract semantic machine performing predicates on semantic models.
 *
 * @version %I%,%G%
 */
public interface SemanticMachine {
    /**
     * @return returns the model's descriptor
     */
    ModelDescriptor getDescriptor();

    /**
     * @return descriptors of defined predicates
     */
    PredicateDescriptor[] getPredicatesList();

    /**
     * Calls a predicate defined in the model
     *
     * @param predicateName name of the predicate
     * @param args          arguments for the predicate
     * @return
     * @throws SemanticException
     */
    LogicalConstant callPredicate(String predicateName, SemanticValue... args) throws SemanticException;
}
