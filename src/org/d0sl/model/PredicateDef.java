package org.d0sl.model;

import org.d0sl.model.expression.Formula;

import java.util.LinkedHashMap;

/**
 * Represents a predicate's definition with arguments
 *
 * @version %I%,%G%
 */
public class PredicateDef {
    private String modelName;
    private String predicateName;
    private LinkedHashMap<String, ArgumentDef> args = new LinkedHashMap<>();
    private Formula formula;
    private LinkedHashMap<String, VariableDef> localVariables = new LinkedHashMap<>();

    public PredicateDef(String modelName, String predicateName, Formula formula, ArgumentDef... args) {
        this.modelName = modelName;
        this.predicateName = predicateName;
        this.formula = formula;
        for (ArgumentDef arg : args) {
            this.args.put(arg.getArgumentName(), arg);
        }
    }

    public PredicateDef(String predicateName, Formula formula, ArgumentDef... args) {
        this.predicateName = predicateName;
        this.formula = formula;
        for (ArgumentDef arg : args) {
            this.args.put(arg.getArgumentName(), arg);
        }
    }

    public PredicateDef(String predicateName, Formula formula) {
        this.predicateName = predicateName;
        this.formula = formula;
    }

    public PredicateDef(String predicateName) {
        this.predicateName = predicateName;
        this.formula = null;
    }

    public PredicateDef(String modelName, String predicateName, Formula formula) {
        this.modelName = modelName;
        this.predicateName = predicateName;
        this.formula = formula;
    }

    public LinkedHashMap<String, VariableDef> getLocalVariables() {
        return localVariables;
    }

    public void addLocalVariable(VariableDef var) {
        localVariables.put(var.getName(), var);
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPredicateName() {
        return predicateName;
    }

    public void setPredicateName(String predicateName) {
        this.predicateName = predicateName;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return "PredicateDef{" +
                "modelName='" + modelName + '\'' +
                ", predicateName='" + predicateName + '\'' +
                ", args=" + args +
                ", formula=" + formula +
                ", variables=" + localVariables +
                '}';
    }

    public int getDimension() {
        return args.size();
    }

    public void addArgument(ArgumentDef arg) {
        args.put(arg.getArgumentName(), arg);
    }

    public LinkedHashMap<String, ArgumentDef> getArgs() {
        return args;
    }
}
