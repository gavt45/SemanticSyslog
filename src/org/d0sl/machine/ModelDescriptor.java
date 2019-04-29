package org.d0sl.machine;

/**
 * Describes and identifies a semantic model.
 *
 * @version %I%,%G%
 */
public class ModelDescriptor {
    private String id;
    private String version;

    public ModelDescriptor(String id, String version) {
        this.id = id;
        this.version = version;
    }

    public ModelDescriptor(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
