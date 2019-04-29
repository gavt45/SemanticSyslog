package org.d0sl.machine;

import org.d0sl.model.SemanticModel;

/**
 * Interface for a Factory containing the Semantic Model
 *
 * @version %I%,%G%
 */
public interface SemanticModelFactory {
    SemanticModel getSemanticModel();
    String getModelName();
}
