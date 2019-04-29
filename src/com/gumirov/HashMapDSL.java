package com.gumirov;

import org.d0sl.domain.DomainFunction;
import org.d0sl.domain.DomainModel;
import org.d0sl.machine.SemanticMachine;
import org.d0sl.model.expression.StringConstant;

import java.util.HashMap;

@DomainModel(name = "HashMapDSL")
public class HashMapDSL {
//    HashMap<String,String> stringHM = new HashMap<>();
//    HashMap<String,Double> numericHM = new HashMap<>();

    /**
     * Standard constructor of DSL
     * @param semantic
     */
    public HashMapDSL(SemanticMachine semantic) {}

    /**
     * Creates new semantic hashmap instance
     * @return SemanticHashMap
     */
    @DomainFunction(name = "create new semantic hashmap")
    public SemanticHashMap newHashMap(){
        return new SemanticHashMap(new HashMap<>());
    }

    /**
     * Gets numeric value from semantic hashmap by key
     * @param semanticHashMap
     * @param key
     * @return
     */
    @DomainFunction(name = "get numeric value")
    public double getNumericValue(SemanticHashMap semanticHashMap, String key){
        Object val = semanticHashMap.getHashMap().get(key);
        if (val instanceof Double) return (double)val;
        else if (val instanceof String){
            try{
                return Double.parseDouble((String) val);
            }catch (NumberFormatException e){
                return 0;
            }
        }
        return 0;
    }

    /**
     * Sets numeric value of semantic hashmap by key
     * @param semanticHashMap
     * @param key
     * @param value
     * @return
     */
    @DomainFunction(name = "set numeric value")
    public boolean setNumericValue(SemanticHashMap semanticHashMap, String key, double value){
        semanticHashMap.getHashMap().put(key, (Double) value);
        return true;
    }

    /**
     * Gets string value by key from semantic hashmap
     * @param semanticHashMap
     * @param key
     * @return
     */
    @DomainFunction(name = "get string value")
    public String getStringValue(SemanticHashMap semanticHashMap,String key){
        Object val = semanticHashMap.getHashMap().get(key);
        if (val instanceof String) return (String)val;
        else if (val instanceof Double) return String.valueOf(val);
        return "";
    }

    /**
     * Sets string value of semantic hashmap by key
     * @param semanticHashMap
     * @param key
     * @param value
     * @return
     */
    @DomainFunction(name = "set string value")
    public boolean setStringValue(SemanticHashMap semanticHashMap, String key, String value){
        semanticHashMap.getHashMap().put(key, value);
        return true;
    }

    /**
     * Prints contents of semantic hashmap
     * @param semanticHashMap
     * @return
     */
    @DomainFunction(name = "print hashmaps")
    public boolean print(SemanticHashMap semanticHashMap){
        System.out.println("Displaying hashmap: "+semanticHashMap+";\n");
        return true;
    }

}
