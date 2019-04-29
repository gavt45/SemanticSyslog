package org.d0sl.model.expression;

/**
 * Represents abstract value (or constant) on semantic models.
 *
 * @version %I%,%G%
 */
public abstract class Value<V> implements Expression, SemanticValue {
    protected V value;

    @Override
    public final int getDimension() {
        return 0;
    }

    @Override
    public final Expression getOperand(int index) {
        return null;
    }

    @Override
    public V getValue() {
        return value;
    }
}
