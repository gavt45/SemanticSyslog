package org.d0sl.machine;

import org.d0sl.domain.DomainLibrary;
import org.d0sl.model.*;
import org.d0sl.model.expression.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Local implementation of the Semantic Machine.
 *
 * @version %I%,%G%
 */
public class LocalSemanticMachine implements SemanticMachine {
    private SemanticModel model;
    private CalculationStack stack = new CalculationStack();
    private Calculator calculator = new Calculator();
    private DomainLibrary domainLibrary = new DomainLibrary(this);

    public LocalSemanticMachine(SemanticModel model) {
        this.model = model;
    }

    public void loadBaseLibrary(String classname) throws SemanticException {
        domainLibrary.loadBaseLibrary(classname);
    }

    public SemanticValue getArgument(String name) {
        return stack.getArgument(name);
    }

    public LogicalConstant callBasePredicate(String libName, String predicateName, SemanticValue... args)
            throws SemanticException {
        return domainLibrary.callBasePredicate(libName, predicateName, args);
    }

    public void loadBaseLibrary(Object lib) throws SemanticException {
        domainLibrary.loadBaseLibrary(lib);
    }

    public SemanticValue callBaseFunction(String libName, String functionName, SemanticValue... args)
            throws SemanticException {
        return domainLibrary.callBaseFunction(libName, functionName, args);
    }

    public SemanticValue getRangeVariable(String name) {
        return stack.getRangeVariable(name);
    }

    public void setRangeVariable(String name, SemanticValue value) {
        stack.setRangeVariable(name, value);
    }

    public void removeRangeVariable(String name) {
        stack.removeRangeVariable(name);
    }

    public SemanticValue getLocalVariable(String name) throws SemanticException {
//        System.out.println("Get local variable: "+name);
        SemanticValue value = stack.getLocalVariable(name);
        if (value == null) {
            PredicateDef predicate = stack.getPredicate();
//            System.out.println("Predicate: "+predicate.getPredicateName());
            if (predicate == null)
                return null;
            VariableDef varDef = predicate.getLocalVariables().get(name);
            if (varDef != null) {
                value = calculator.calculate(varDef.getExpression());
                stack.setLocalVariable(name, value);
            }
        }
//        System.out.println("Value: "+value+" of type: "+value.type());
        return value;
    }

    @Override
    public ModelDescriptor getDescriptor() {
        return new ModelDescriptor(model.getName());
    }

    @Override
    public PredicateDescriptor[] getPredicatesList() {
        Collection<PredicateDef> predicates = model.getPredicates().values();
        PredicateDescriptor[] descriptors = new PredicateDescriptor[predicates.size()];
        Iterator<PredicateDef> iter = predicates.iterator();
        for (int i = 0; i < descriptors.length; i++) {
            descriptors[i] = new PredicateDescriptor(iter.next());
        }
        return descriptors;
    }

    @Override
    public LogicalConstant callPredicate(String predicateName, SemanticValue... args) throws SemanticException {
        PredicateDef predicate = model.getPredicates().get(predicateName);
        if (predicate == null)
            return new LogicalConstant(Logical.NONE);
        stack.push(predicate, args);
        LogicalConstant res = (LogicalConstant) calculator.calculate(predicate.getFormula());
        stack.pop();
        return res;
    }

    class Calculator {
        SemanticValue calculate(Expression exp) throws SemanticException {
            switch (exp.type()) {
                case NUMERIC_TERM:
                    return calculate((NumericTerm) exp);
                case NUMERIC_CONSTANT:
                    return (NumericConstant) exp;
                case LOGICAL_AND:
                    return calculate((And) exp);
                case PREDICATE_CALL:
                    return calculate((PredicateCall) exp);
                case LOGICAL_COMPARISON:
                    return calculate((Comparison) exp);
                case LOGICAL_IMPLICATION:
                    return calculate((Implication) exp);
                case LOGICAL_NOT:
                    return calculate((Not) exp);
                case LOGICAL_OR:
                    return calculate((Or) exp);
                case LOGICAL_CONSTANT:
                    return (LogicalConstant) exp;
                case STRING_TERM:
                    return calculate((StringTerm) exp);
                case STRING_CONSTANT:
                    return (StringConstant) exp;
                case LIST:
                    return (List) exp;
                case FOR_ALL:
                    return calculate((ForAll)exp);
                case RANGE_REF:
                    return getRangeVariable(((RangeRef)exp).getName());
                case VARIABLE_REF:
                    return calculate((VariableRef) exp);
                case PARENTHESES:
                    return calculate(exp.getOperand(0));
                case ARGUMENT_REF:
                    return getArgument(((ArgumentRef) exp).getArgumentName());
                case FUNCTION_CALL:
                    return calculate((FunctionCall) exp);
                case LOGGING:
                    return calculate((Logging) exp);
                case BASE_OBJECT:
                    return (BaseObject)exp;
                default:
                    throw new SemanticException("Unknown type of Expression: " + exp);
            }
        }

        NumericConstant calculate(NumericTerm term) throws SemanticException {
            Double[] values = new Double[term.getDimension()];
            Double result = 0.0;
//            System.out.println("Calculating term: "+term);
            for (int i = 0; i < term.getDimension(); i++) {
                Object termOperand = term.getOperand(i);
//                System.out.println("Term operand: "+((Expression) termOperand).type());
                Object calcRes = calculate((Expression) termOperand);
//                System.out.println("i: "+i+"; calc res: "+calcRes+" calcRes type: "+((SemanticValue) calcRes).type());
                NumericConstant v = (NumericConstant) calcRes;
                values[i] = v.getValue();
            }
            switch (term.getOperation()) {
                case PLUS:
                    for (Double v : values) result += v;
                    break;
                case MINUS:
                    if (values.length > 0) {
                        result = values[0];
                        for (int i = 1; i < values.length; i++)
                            result -= values[i];
                    }
                    break;
                case MULTIPLICATION:
                    result = 1.0;
                    for (Double v : values) result *= v;
                    break;
                case DIVISION:
                    if (values.length > 0) {
                        result = values[0];
                        for (int i = 1; i < values.length; i++)
                            result /= values[i];
                    }
                    break;
            }
            return new NumericConstant(result);
        }

        LogicalConstant calculate(And lexp) throws SemanticException {
            for (int i = 0; i < lexp.getDimension(); i++) {
                Expression operand = lexp.getOperand(i);
                if(operand != null) {
                    LogicalConstant l = (LogicalConstant) calculate(operand);
                    if (l.getValue() != Logical.TRUE)
                        return l;
                }
            }
            return new LogicalConstant(Logical.TRUE);
        }

        LogicalConstant calculate(Or lexp) throws SemanticException {
            for (int i = 0; i < lexp.getDimension(); i++) {
                LogicalConstant l = (LogicalConstant) calculate(lexp.getOperand(i));
                if (l.getValue() != Logical.FALSE)
                    return l;
            }
            return new LogicalConstant(Logical.FALSE);
        }

        LogicalConstant calculate(Not lexp) throws SemanticException {
            LogicalConstant l = (LogicalConstant) calculate(lexp.getOperand(0));
            switch (l.getValue()) {
                case TRUE:
                    return new LogicalConstant(Logical.FALSE);
                case FALSE:
                    return new LogicalConstant(Logical.TRUE);
                case NONE:
                    return l;
                default:
                    throw new SemanticException("Unknown type of Expression: " + l.getValue());
            }
        }

        LogicalConstant calculate(Implication lexp) throws SemanticException {
            LogicalConstant condition = (LogicalConstant) calculate(lexp.getCondition());
            switch (condition.getValue()) {
                case TRUE:
                    return (LogicalConstant) calculate(lexp.getThenFormula());
                case FALSE:
                    if (lexp.getElseFormula() != null)
                        return (LogicalConstant) calculate(lexp.getElseFormula());
                    else
                        return new LogicalConstant(Logical.TRUE);
                case NONE:
                    return new LogicalConstant(Logical.NONE);
                default:
                    throw new SemanticException("Unknown type of Expression: " + condition.getValue());
            }
        }

        LogicalConstant calculate(Comparison lexp) throws SemanticException {
            SemanticValue value1 = calculate(lexp.getLeft());
            SemanticValue value2 = calculate(lexp.getRight());
            if (value1.type() != value2.type())
                throw new SemanticException("Incompatible types in comparison expression! " +
                        value1.type() + " and " + value2.type());
            switch (value1.type()) {
                case NUMERIC_CONSTANT:
                    NumericConstant v1 = (NumericConstant) value1;
                    NumericConstant v2 = (NumericConstant) value2;
                    switch (lexp.getOperation()) {
                        case LESS:
                            return new LogicalConstant(v1.isLess(v2));
                        case GREATER:
                            return new LogicalConstant(v1.isGreater(v2));
                        case LESS_OR_EQUAL:
                            return new LogicalConstant(v1.isLessOrEqual(v2));
                        case GREATER_OR_EQUAL:
                            return new LogicalConstant(v1.isGreaterOrEqual(v2));
                        case NOT_EQUAL:
                            return new LogicalConstant(v1.equals(v2));
                        case EQUAL:
                            return new LogicalConstant(v1.equals(v2));
                    }
                    break;
                case LOGICAL_CONSTANT:
                case STRING_CONSTANT:
                    switch (lexp.getOperation()) {
                        case LESS_OR_EQUAL:
                        case GREATER_OR_EQUAL:
                        case EQUAL:
                            return new LogicalConstant(value1.equals(value2));
                        case NOT_EQUAL:
                            return new LogicalConstant(!value1.equals(value2));
                    }
                    break;
                default:
                    switch (lexp.getOperation()) {
                        case EQUAL:
                            return new LogicalConstant(value1.equals(value2));
                        case NOT_EQUAL:
                            return new LogicalConstant(!value1.equals(value2));
                    }
                    throw new SemanticException("Incompatible types! Expected only constants to be compared");
            }
            return new LogicalConstant(Logical.NONE);
        }

        LogicalConstant calculate(PredicateCall lexp) throws SemanticException {
            if (lexp.getDimension() == 0)
                return callPredicate(lexp.getPredicateName());
            SemanticValue[] arguments = new SemanticValue[lexp.getDimension()];
            for (int i = 0; i < lexp.getDimension(); i++) {
                arguments[i] = calculate(lexp.getOperand(i));
            }
            return callPredicate(lexp.getPredicateName(), arguments);
        }

        SemanticValue calculate(FunctionCall lexp) throws SemanticException {
            if (lexp.getDimension() == 0)
                return callBaseFunction(lexp.getModelName(), lexp.getFunctionName());
            SemanticValue[] arguments = new SemanticValue[lexp.getDimension()];
            for (int i = 0; i < lexp.getDimension(); i++) {
                arguments[i] = calculate(lexp.getOperand(i));
            }
            return callBaseFunction(lexp.getModelName(), lexp.getFunctionName(), arguments);
        }

        SemanticValue calculate(VariableRef varRef) throws SemanticException {
            return getLocalVariable(varRef.getName());
        }

        StringConstant calculate(StringTerm strExp) throws SemanticException {
            String res = strExp.getStringConstant().getValue();
            for (Expression exp : strExp.getAdditions()) {
                res += calculate(exp);
            }
            return new StringConstant(res);
        }

        SemanticValue calculate(Logging log) throws SemanticException {
            SemanticValue res = calculate(log.getOperand(0));
            return new LogicalConstant(true);
        }

        SemanticValue calculate(ForAll forAll) throws SemanticException {
            List list = null;
            SemanticValue l = calculate(forAll.getList());
            if(l.type() == ExpressionType.LIST)
                list = (List)l;
            if(list != null) {
                final ArrayList<Range> ranges = forAll.getRanges();
                final ArrayList<SemanticValue> values = list.getValue();
                final Expression statement = forAll.getStatement();
                if(values.size() >= ranges.size()) {
                    class RangeCalculator {
                        Logical calculate(LinkedList<Integer> indexes,
                                          int rangeIndex) throws SemanticException {
                            if (rangeIndex == ranges.size()) {
                                SemanticValue res = Calculator.this.calculate(statement);
                                if(res.type() == ExpressionType.LOGICAL_CONSTANT) {
                                    return ((LogicalConstant)res).getValue();
                                }
                                return Logical.FALSE;
                            } else {
                                for (int i = 0; i < values.size(); i++) {
                                    if (!indexes.contains(i)) {
                                        setRangeVariable(ranges.get(rangeIndex).getName(),
                                                values.get(i));
                                        indexes.push(i);
                                        Logical res = calculate(indexes, rangeIndex + 1);
                                        indexes.pop();
                                        if(res != Logical.TRUE)
                                            return res;
                                    }
                                }
                            }
                            return Logical.TRUE;
                        }
                    }
                    RangeCalculator rc = new RangeCalculator();
                    Logical res = rc.calculate(new LinkedList<Integer>(), 0);
                    return new LogicalConstant(res);
                }
            }
            return new LogicalConstant(true);
        }

    }
}
