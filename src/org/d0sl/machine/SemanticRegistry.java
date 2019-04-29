package org.d0sl.machine;

import org.d0sl.model.SemanticModel;

/**
 * Abstract registry for semantic models.
 *
 * @version %I%,%G%
 */
public interface SemanticRegistry {
    /**
     * Register new semantic model
     * @param model to be registered
     * @return model descriptor
     * @throws SemanticException
     */
    ModelDescriptor register(SemanticModel model) throws SemanticException;

    /**
     * Returns a semantic machine for the model specified by model's descriptor
     * @param modelDescriptor specifies the semantic model
     * @return the machine capable of performing the specified model
     * @throws SemanticException
     */
    SemanticMachine getSemanticMachine(ModelDescriptor modelDescriptor) throws SemanticException;
}
