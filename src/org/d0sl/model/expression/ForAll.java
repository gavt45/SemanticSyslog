package org.d0sl.model.expression;

import java.util.ArrayList;

/**
 * Represents a for all statement
 *
 * @version %I%,%G%
 */

public class ForAll extends Formula {
    private Expression list;
    private ArrayList<Range> ranges = new ArrayList<>(3);
    private Expression statement;

    public ForAll(Expression list, Expression statement) {
        this.list = list;
        this.statement = statement;
    }

    public Expression getList() {
        return list;
    }

    public ArrayList<Range> getRanges() {
        return ranges;
    }

    public Expression getStatement() {
        return statement;
    }

    public boolean add(Range range) {
        return ranges.add(range);
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.FOR_ALL;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public Expression getOperand(int index) {
        return null;
    }
}
