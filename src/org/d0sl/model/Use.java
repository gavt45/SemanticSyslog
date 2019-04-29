package org.d0sl.model;

/**
 * Represents an external model's dependency
 *
 * @version %I%,%G%
 */
public class Use {
    private String modelName;
    private String className;
    private DomainSpecificModel domainModel;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public DomainSpecificModel getDomainModel() {
        return domainModel;
    }

    public void setDomainModel(DomainSpecificModel domainModel) {
        this.domainModel = domainModel;
    }

    public Use() {
    }

    public Use(String modelName) {
        this.modelName = modelName;
    }

    public Use(String modelName, String className) {
        this.modelName = modelName;
        this.className = className;
    }

    public Use(String modelName, String className, DomainSpecificModel domainModel) {
        this.modelName = modelName;
        this.className = className;
        this.domainModel = domainModel;
    }
}
