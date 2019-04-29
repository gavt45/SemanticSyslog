package org.d0sl.model.expression;

/**
 * Represents a possible value of expressions, variables and arguments.
 * It can be String, Logical and Numeric type.
 *
 * @version %I%,%G%
 */
public interface SemanticValue {
    <V> V getValue();

    ExpressionType type();
}
