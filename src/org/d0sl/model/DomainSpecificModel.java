package org.d0sl.model;

import java.util.LinkedList;

/**
 * Represents a Domain Specific Model
 *
 * @version %I%,%G%
 */
public class DomainSpecificModel {
    private String name;
    private LinkedList<Typedef> typedefs = new LinkedList<>();
    private LinkedList<FunctionDef> functions = new LinkedList<>();

    public DomainSpecificModel() {
    }

    public DomainSpecificModel(String name) {
        this.name = name;
    }

    public DomainSpecificModel(String name, LinkedList<Typedef> typedefs, LinkedList<FunctionDef> functions) {
        this.name = name;
        this.typedefs = typedefs;
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Typedef> getTypedefs() {
        return typedefs;
    }

    public LinkedList<FunctionDef> getFunctions() {
        return functions;
    }
}
