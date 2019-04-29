package com.gumirov;

import org.d0sl.model.expression.ExpressionType;
import org.d0sl.model.expression.List;
import org.d0sl.model.expression.SemanticValue;

import java.util.ArrayList;

public class SemanticArrayList extends List implements SemanticValue {
    private ArrayList list;
    SemanticArrayList(ArrayList l){list=l;}

    public ArrayList getList() {
        return list;
    }

    @Override
    public ArrayList<SemanticValue> getValue() {
        ArrayList a = new ArrayList<>();
        a.add(this);
        return a;// .add(this);
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.LIST;
    }
}
