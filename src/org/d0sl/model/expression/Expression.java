package org.d0sl.model.expression;

/**
 * Represents a basic Expression type.
 * In general, an expression can be a tree consisting of constants, operations,
 * variables and predicate calls.
 * <p>
 * The value of the expression can be the following types:
 * Logical (true, false, none), Numeric (like 12121.22), String (any text),
 * Variable, Call of OldPredicateImpl, Parentheses, ArgumentRef.
 *
 * @version %I%,%G%
 */
public interface Expression {
    /**
     * @return Type of the expression
     */
    ExpressionType type();

    /**
     * @return Number of operands in the expression. In case of constant expression, dimension is 0.
     */
    int getDimension();

    /**
     * @param index Index of operand's place.
     * @return Operand in the corresponding place.
     */
    Expression getOperand(int index);
}
