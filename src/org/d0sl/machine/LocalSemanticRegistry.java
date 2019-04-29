package org.d0sl.machine;

import org.d0sl.model.SemanticModel;

import java.util.HashMap;

/**
 * Local implementation of the SemanticRegistry.
 *
 * @version %I%,%G%
 */
public class LocalSemanticRegistry implements SemanticRegistry {
    private HashMap<String, SemanticModel> semanticModels = new HashMap<>();

    @Override
    public ModelDescriptor register(SemanticModel model) throws SemanticException {
        ModelDescriptor descriptor = new ModelDescriptor(model.getName());
        semanticModels.put(descriptor.getId(), model);
        return descriptor;
    }

    @Override
    public LocalSemanticMachine getSemanticMachine(ModelDescriptor modelDescriptor) throws SemanticException {
        SemanticModel model = semanticModels.get(modelDescriptor.getId());
        if(model == null)
            throw new SemanticException("Semantic model not found for " + modelDescriptor);
        return new LocalSemanticMachine(model);
    }
}
