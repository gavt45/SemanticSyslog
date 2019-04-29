package org.d0sl.model;

import org.d0sl.model.expression.ExpressionType;
import org.d0sl.model.expression.SemanticValue;
import org.d0sl.model.expression.Value;

import java.util.HashMap;
import java.util.Set;

/**
 * Represent a domain specific object
 */
public class BaseObject extends Value {
    private String modelName;
    private String name;
    private HashMap<String, SemanticValue> properties = new HashMap<>();

    public SemanticValue get(Object key) {
        return properties.get(key);
    }

    public Set<String> keySet() {
        return properties.keySet();
    }

    public SemanticValue put(String key, SemanticValue value) {
        return properties.put(key, value);
    }

    public BaseObject(String modelName, String name) {
        this.modelName = modelName;
        this.name = name;
    }

    public BaseObject() {
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getModelName() {
        return modelName;
    }

    @Override
    public ExpressionType type() {
        return ExpressionType.BASE_OBJECT;
    }

    @Override
    public BaseObject getValue() {
        return this;
    }
}
