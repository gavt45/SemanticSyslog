package org.d0sl.model;

import org.d0sl.model.expression.Formula;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Main class defining Semantic Model
 *
 * @version %I%,%G%
 */
public class SemanticModel {
    private LinkedList<Use> uses = new LinkedList<>();
    private LinkedHashMap<String, VariableDef> globalVariables = new LinkedHashMap<>();
    private String name;
    private LinkedList<Formula> rules = new LinkedList<>();
    private LinkedHashMap<String, PredicateDef> predicates = new LinkedHashMap<>();

    public SemanticModel(String name) {
        this.name = name;
    }

    public LinkedList<Use> getUses() {
        return uses;
    }

    @Override
    public String toString() {
        return "DomainModel " + name + " {\n" +
                "    globalVariables=" + globalVariables + "\n" +
                "    rules=" + rules + "\n" +
                "    predicates=" + predicates + "\n" +
                '}';
    }

    public LinkedHashMap<String, VariableDef> getGlobalVariables() {
        return globalVariables;
    }

    public void addVariable(VariableDef variable) {
        if (variable != null)
            globalVariables.put(variable.getName(), variable);
    }

    public String getName() {
        return name;
    }

    public LinkedList<Formula> getRules() {
        return rules;
    }

    public void addRule(Formula rule) {
        if (rule != null)
            rules.add(rule);
    }

    public LinkedHashMap<String, PredicateDef> getPredicates() {
        return predicates;
    }

    public void addPredicateDef(PredicateDef def) {
        predicates.put(def.getPredicateName(), def);
    }
}
