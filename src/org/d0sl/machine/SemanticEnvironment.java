package org.d0sl.machine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.d0sl.model.expression.Logical;
import org.d0sl.model.expression.SemanticValue;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * Represents a semantic environment for local SemanticRegistry, Models and SemanticMachines
 *
 * @author kuruhuru
 * @version %I%,%G%
 */
public class SemanticEnvironment {
    private static final Logger log = LogManager.getLogger(SemanticEnvironment.class);
    public static final String SEMANTIC_MODELS = "semantic.models";
    public static final String DOMAIN_MODELS = "domain.models";
    private LocalSemanticRegistry semanticRegistry = new LocalSemanticRegistry();
    private String[] baseLibraries;

    /**
     * Initializes the environment from properties file
     *
     * @param configFile properties file, where should be:
     *                   semantic.models = [classname1, classname2, ...]
     *                   domain.models = [classname1, classname2, ...]
     */
    public void init(String configFile) throws ClassNotFoundException, SemanticException,
            InstantiationException, IllegalAccessException, IOException {
        log.info("Semantic Environment initializing...");
        Properties props = new Properties();
        try(FileInputStream input = new FileInputStream(new File(configFile))) {
            props.load(input);
            init(props);
        } catch (FileNotFoundException e) {
            log.info("File " + configFile + " not found.");
        }

        log.info("Trying to find " + configFile + " in resources...");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(configFile).getFile());
        try(FileInputStream input = new FileInputStream(file)) {
            props.load(input);
            init(props);
        }
    }

    /**
     * Initializes the environment from properties URL
     *
     * @param config properties, where should be:
     *               semantic.models = [classname1, classname2, ...]
     *               domain.models = [classname1, classname2, ...]
     */

    public void init(URL config) throws ClassNotFoundException, SemanticException,
            InstantiationException, IllegalAccessException, IOException {
        Properties props = new Properties();
        try(InputStream input = config.openStream()) {
            props.load(input);
            init(props);
        }
    }

    /**
     * Initializes the environment from properties
     *
     * @param props properties, where should be:
     *              semantic.models = [classname1, classname2, ...]
     *              domain.models = [classname1, classname2, ...]
     */

    public void init(Properties props) throws ClassNotFoundException,
            SemanticException, InstantiationException, IllegalAccessException {
        // loading semantic models
        String semanticModels = props.getProperty(SEMANTIC_MODELS);
        if(semanticModels == null) {
            throw new NullPointerException("There are no semantic models defined in properties");
        }
        String[] models = semanticModels.split(",");
        for (String className : models) {
            className = className.trim();
            Class clazz = Class.forName(className);
            SemanticModelFactory factory = (SemanticModelFactory) clazz.newInstance();
            semanticRegistry.register(factory.getSemanticModel());
        }

        // loading domain models
        String domainModels = props.getProperty(DOMAIN_MODELS);
        if(domainModels == null) {
            throw new NullPointerException("There are no domain models defined in properties");
        }
        baseLibraries = domainModels.split(",");

        log.info("SemanticEnvironment successfully initialized!");
    }

    /**
     * Calls a predicate on the specified model with optional arguments
     *
     * @param modelName     model's name
     * @param predicateName predicate's name
     * @param args          optional arguments
     * @return true, false or none
     * @throws SemanticException
     */
    public Logical callPredicate(String modelName, String predicateName, SemanticValue... args) throws SemanticException {
        LocalSemanticMachine sm = semanticRegistry.getSemanticMachine(new ModelDescriptor(modelName));
        for (String libClassName : baseLibraries) {
            sm.loadBaseLibrary(libClassName);
        }
        return sm.callPredicate(predicateName, args).getValue();
    }

}
