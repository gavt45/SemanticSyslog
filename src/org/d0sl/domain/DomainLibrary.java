package org.d0sl.domain;

import org.d0sl.machine.LocalSemanticMachine;
import org.d0sl.machine.SemanticException;
import org.d0sl.machine.SemanticMachine;
import org.d0sl.model.BaseObject;
import org.d0sl.model.expression.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Represents a library for domain predicates and functions
 *
 * @version %I%,%G%
 * @author kuruhuru
 */
public class DomainLibrary {
    private HashMap<String, Object> domainLibs = new HashMap<>();
    private HashMap<String, Method> domainFunctions = new HashMap<>();
    private HashMap<String, Method> domainPredicates = new HashMap<>();
    private SemanticMachine machine;

    public void loadBaseLibrary(String classname) throws SemanticException {
        try {
            // Check if the class is marked as @DomainModel
            // Then put it to domainLibs HashMap with 'name' parameter as a key
            Class libClass = Class.forName(classname);
            DomainModel libAnnotation =
                    (DomainModel) libClass.getAnnotation(DomainModel.class);
            if(libAnnotation != null) {
                Object lib = null;
                try {
                    lib = libClass.getConstructor(SemanticMachine.class).newInstance(machine);
                } catch(Exception e) {}
                if(lib == null)
                    lib = libClass.newInstance();
                domainLibs.put(libAnnotation.name(), lib);
            }
            // Search predicates and functions declarations
            // and add them to domainFunctions and domainPredicates
            loadFunctions(libClass);
        } catch (Exception ex) {
            throw new SemanticException("Error when loading the domain library for " + classname);
        }
    }

    public void loadBaseLibrary(Object lib) throws SemanticException {
        try {
            // Check if the class is marked as @DomainModel
            // Then put it to domainLibs HashMap with 'name' parameter as a key
            Class libClass = lib.getClass();
            DomainModel libAnnotation =
                    (DomainModel) libClass.getAnnotation(DomainModel.class);
            if(libAnnotation != null) {
                domainLibs.put(libAnnotation.name(), lib);
            }
            // Search predicates and functions declarations
            // and add them to domainFunctions and domainPredicates
            loadFunctions(libClass);
        } catch (Exception ex) {
            throw new SemanticException("Error when loading the domain library for " + lib);
        }
    }

    public DomainLibrary(LocalSemanticMachine machine) {
        this.machine = machine;
    }

    private void loadFunctions(Class libClass) {
        // Search predicates and functions declarations
        // and add them to domainFunctions and domainPredicates
        Method[] methods = libClass.getMethods();
        for (Method m: methods) {
            DomainPredicate predicateAnnotation = m.getAnnotation(DomainPredicate.class);
            if(predicateAnnotation != null) {
                if(!m.getReturnType().equals(Logical.class))
                    continue;
                if(validateMethod(m)) {
                    domainPredicates.put(predicateAnnotation.name(), m);
                }
            } else {
                DomainFunction functionAnnotation = m.getAnnotation(DomainFunction.class);
                if(functionAnnotation != null) {
                    if(validateMethod(m)) {
                        domainFunctions.put(functionAnnotation.name(), m);
                    }
                }
            }
        }
    }

    public LogicalConstant callBasePredicate(String libName,
                                             String predicateName,
                                             SemanticValue... args)
            throws SemanticException {
        Method predicate = domainPredicates.get(predicateName);
        Object libInstance = domainLibs.get(libName);
        if(predicate == null)
            throw new SemanticException("The domain predicate "
                    + predicateName + " not found!");
        try {
            LogicalConstant res = (LogicalConstant) predicate.invoke(libInstance, (Object[]) args);
            return res;
        } catch (Exception ex) {
            throw new SemanticException("Error when calling the domain predicate "
                    + predicateName);
        }
    }

    public SemanticValue callBaseFunction(String libName,
                                          String functionName,
                                          SemanticValue... args)
            throws SemanticException {
//        System.out.println("Calling base function: "+libName+"."+functionName+"("+ Arrays.toString(args) +")");
//        System.out.println("Domain funcs: "+domainFunctions);
        Method func = domainFunctions.get(functionName);
        Object libInstance = domainLibs.get(libName);
//        System.out.println("Func: "+func+"; lib:"+libInstance);
        if(func == null)
            throw new SemanticException("The domain function "
                    + functionName + " not found!");
        try {
            Object[] values = new Object[args.length];
            for (int i=0; i<values.length; i++) {
                values[i] = args[i].getValue();
            }
//            System.out.println("Call "+libInstance+"."+functionName+"("+ (Arrays.toString(values)) +")");
//            System.out.print("Param types: "+functionName+"(");
            for (Object val:values) {
                //System.out.print(val.getClass().getName()+" ");
            }
//            System.out.println(");");
            Object res = func.invoke(libInstance, values);
            if(res instanceof SemanticValue)
                return (SemanticValue) res;
            if(res instanceof Logical)
                return new LogicalConstant((Logical) res);
            if((res instanceof Boolean) || (res.getClass().equals(boolean.class)))
                return new LogicalConstant((Boolean) res);
            if((res instanceof Double) || (res.getClass().equals(double.class)))
                return new NumericConstant((Double) res);
            return new StringConstant((String)res);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SemanticException("Error when calling the domain function "
                    + functionName);
        }
    }

    /*
     * Checks the method for correct parameters and returned type.
     */
    private boolean validateMethod(Method m) {
        //modification by gav: now you can pass List to a base function
        Class returnType = m.getReturnType();
        if(!returnType.equals(Logical.class)
                && !returnType.equals(boolean.class)
                && !returnType.equals(Boolean.class)
                && !returnType.equals(double.class)
                && !returnType.equals(Double.class)
                && !returnType.equals(String.class)
                && !BaseObject.class.isAssignableFrom(returnType)
                && !List.class.isAssignableFrom(returnType)) {
            return false;
        }

        Class[] parameters = m.getParameterTypes();
        boolean validParameters = true;
        for (Class param: parameters) {
            if(!param.equals(Logical.class)
                    && !param.equals(double.class)
                    && !param.equals(Double.class)
                    && !param.equals(String.class)
                    && !param.equals(ArrayList.class) ///dfksdkfskdjfskfjskdf
                    //&& !param.equals(List.class) ///dfksdkfskdjfskfjskdf
                    && !param.equals(Object.class) ///dfksdkfskdjfskfjskdf
                    && !BaseObject.class.isAssignableFrom(param)
                    && !BaseObject.class.isAssignableFrom(returnType)) {
                validParameters = false;
                break;
            }
        }
        return validParameters;
    }
}
