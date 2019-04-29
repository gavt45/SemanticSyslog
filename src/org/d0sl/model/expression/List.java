package org.d0sl.model.expression;

import java.util.ArrayList;

/**
 * Represents a list of constants (objects, numeric, logical. string)
 *
 * @version %I%,%G%
 */
public class List extends Value<ArrayList<SemanticValue>> {

    public List() {
        value = new ArrayList<>();
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LIST;
    }

}
