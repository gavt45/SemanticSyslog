package com.gumirov;

import org.d0sl.domain.DomainFunction;
import org.d0sl.domain.DomainModel;
import org.d0sl.machine.SemanticException;
import org.d0sl.model.expression.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@DomainModel(name = "ArrayListDSL")
public class ArrayListDSL {

    @DomainFunction(name = "init string arrayList")
    public SemanticArrayList initStringArrayList(){
        return new SemanticArrayList(new ArrayList<String>());
    }

    @DomainFunction(name = "init numeric arrayList")
    public SemanticArrayList initNumericArrayList(){
        return new SemanticArrayList(new ArrayList<Double>());
    }

    @DomainFunction(name = "get string")
    public String get(ArrayList arrayList,double index) throws SemanticException{
        SemanticValue value = (SemanticValue) arrayList.get((int)index);
        return value.getValue();
        //if (value instanceof Double) return new NumericConstant(((Double) value).doubleValue());
//        if (value instanceof String) return new StringConstant((String) value);
//        throw new SemanticException("Error in ArrayListDSL: value has unknown type: "+value);
    }

    @DomainFunction(name = "get numeric")
    public double getNum(ArrayList arrayList,double index) throws SemanticException{
        SemanticValue value = (SemanticValue) arrayList.get((int)index);
        if (value instanceof StringConstant) return Double.parseDouble(((StringConstant)value).getValue());
        if (value instanceof NumericConstant) return ((NumericConstant) value).getValue();
        throw new SemanticException("Error in ArrayListDSL: value has unknown type: "+value);
    }

    @DomainFunction(name = "addString")
    public boolean addString(ArrayList arrayList, String val){
        return arrayList.add(new StringConstant(val)) || true;
    }

    @DomainFunction(name = "addNumeric")
    public boolean addNumeric(ArrayList arrayList, double val){
        arrayList.add(new NumericConstant(val));
        return true;
    }

    @DomainFunction(name = "addList")
    public boolean addList(ArrayList arrayList, ArrayList val){
        System.out.println("ADD LIST: "+ Arrays.deepToString(arrayList.toArray())+" append "+val);
        arrayList.add(val);
        return true;
    }

    @DomainFunction(name = "set value")
    public boolean setValue(ArrayList arrayList, double idx, String val){
        arrayList.set((int)idx, new StringConstant(val));
        return true;
    }

    @DomainFunction(name = "size")
    public double getSize(ArrayList arrayList){
        return (double) arrayList.size();
    }

    @DomainFunction(name = "containsString")
    public boolean containsStr(ArrayList arrayList, String value){
        return arrayList.contains(new StringConstant(value));
    }

    @DomainFunction(name = "asListOfStrings")
    public List asListOfStrs(ArrayList<SemanticArrayList> arrayList){
        List l = new List();
        for (Object o:arrayList.get(0).getList()) {
            if (o instanceof String){
                l.getValue().add(new StringConstant((String) o));
            }
        }
        return l;
    }

}
