package com.gumirov;

import org.d0sl.model.BaseObject;
import org.d0sl.model.expression.ExpressionType;
import org.d0sl.model.expression.SemanticValue;

import java.util.HashMap;

public class SemanticHashMap extends BaseObject {
    private HashMap<String, Object> hashMap;

    /**
     * SemanticHashMap -- base object for hashmap
     * @param hashMap
     */
    SemanticHashMap(HashMap<String, Object> hashMap){this.hashMap=hashMap;}

    @Override
    public String toString(){
        return "SemanticHashMap{"+hashMap.toString()+"}";
    }

    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }
}
