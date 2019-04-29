package org.d0sl.xml;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.d0sl.model.*;
import org.d0sl.model.expression.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Serializes and deserializes the semantic model into and from XML
 * @version %I%,%G%
 */
public class XMLSerializer {
    private static final Logger log = LogManager.getLogger(XMLSerializer.class);

    public static final String MODEL_TAG = "semantic-model";
    public static final String MODEL_NAME_ATTR = "name";
    public static final String PREDICATE_NAME_ATTR = "name";
    public static final String PREDICATE_TAG = "predicate";
    public static final String ARG_DEF_TAG = "arg-def";
    public static final String ARG_DEF_NAME_ATTR = "arg-def";
    public static final String ARG_DEF_TYPE_ATTR = "type";
    public static final String ARG_DEF_TYPE_REF_ATTR = "type-ref";
    public static final String UNKNOWN_TAG = "UNKNOWN";
    public static final String NUMERIC_TERM_TAG = "numeric-term";
    public static final String NUMERIC_TERM_OPERATION_ATTR = "operation";
    public static final String NUMERIC_CONSTANT_TAG = "numeric";
    public static final String NUMERIC_CONSTANT_VALUE_ATTR = "value";
    public static final String AND_TAG = "and";
    public static final String REF_TAG = "ref";
    public static final String REF_TYPE_ATTR = "type";
    public static final String REF_NAME_ATTR = "name";
    public static final String REF_MODEL_NAME_ATTR = "modelName";
    public static final String COMPARISON_TAG = "comparison";
    public static final String COMPARISON_OPERATION_ATTR = "operation";
    public static final String IMPLICATION_TAG = "if";
    public static final String IMPLICATION_THEN_TAG = "then";
    public static final String IMPLICATION_ELSE_TAG = "else";
    public static final String NOT_TAG = "not";
    public static final String OR_TAG = "or";
    public static final String LOGICAL_CONSTANT_TAG = "logical";
    public static final String LOGICAL_CONSTANT_VALUE_ATTR = "value";
    public static final String STRING_TAG = "string";
    public static final String STRING_VALUE_ATTR = "value";
    public static final String LIST_TAG = "list";
    public static final String FOR_ALL_TAG = "for-all";
    public static final String RANGES_TAG = "ranges";
    public static final String RANGE_TAG = "range";
    public static final String RANGE_NAME_ATTR = "name";
    public static final String RANGE_FROM_TAG = "from";
    public static final String PARENTHESES_TAG = "parentheses";
    public static final String LOG_TAG = "log";
    public static final String OBJECT_TAG = "object";
    public static final String OBJECT_NAME_ATTR = "name";
    public static final String OBJECT_MODEL_NAME = "modelName";
    public static final String PROPERTY_TAG = "property";
    public static final String PROPERTY_NAME_ATTR = "name";
    public static final String VAR_DEF_TAG = "variable";
    public static final String VAR_DEF_NAME_ATTR = "name";
    public static final String FUNCTION_CALL_ATTR = "function";
    public static final String USE_TAG = "use";
    public static final String USE_MODEL_NAME_ATTR = "modelName";
    public static final String USE_CLASS_NAME_ATTR = "className";
    public static final String DOMAIN_TAG = "domain";
    public static final String TYPEDEF_TAG = "typedef";
    public static final String TYPEDEF_NAME_TAG = "name";
    public static final String FUN_TAG = "fun";
    public static final String FUN_NAME_TAG = "name";
    public static final String RETURN_TAG = "return";
    public static final String RETURN_TYPE_ATTR = "type";
    public static final String RETURN_TYPE_REFERENCE_ATTR = "typeReference";

    /**
     * Serializes the semantic model into XML DOM
     * @param model semantic model
     * @return XML DOM
     */
    public static Document serialize(SemanticModel model) throws SerializationException {
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder =
                    dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root model element
            Element modelElement = doc.createElement(MODEL_TAG);
            modelElement.setAttribute(MODEL_NAME_ATTR, model.getName());
            doc.appendChild(modelElement);

            // adding 'use' references to external DomainSpecificModel
            LinkedList<Use> uses = model.getUses();
            for (Use use: uses) {
                modelElement.appendChild(XMLSerializer.serialize(use, doc));
            }

            // adding predicates definitions
            LinkedHashMap<String, PredicateDef> predicates = model.getPredicates();
            for (PredicateDef def: predicates.values()) {
                modelElement.appendChild(XMLSerializer.serialize(def, doc));
            }
            return doc;
        } catch (Exception e) {
            throw new SerializationException(
                    "Error when serializing the semantic model [" + model.getName() + "]", e);
        }
    }

    /**
     * Serializes the semantic model into writer
     * @param model semantic model
     * @param writer writer for serialization
     */
    public static void serialize(SemanticModel model, Writer writer) throws SerializationException {
        try {
            Document doc = serialize(model);
            // write the content into writer
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer =
                    transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(writer));
        } catch (TransformerConfigurationException e) {
            throw new SerializationException(
                    "TransformerConfiguration Error when serializing the semantic model ["
                            + model.getName() + "]", e);
        } catch (TransformerException e) {
            throw new SerializationException(
                    "Transformer Error when serializing the semantic model ["
                            + model.getName() + "]", e);
        }
    }

    /**
     * Serializes the semantic model into XML string
     * @param model semantic model
     * @return XML string
     */
    public static String toXmlString(SemanticModel model) throws SerializationException {
        StringWriter res = new StringWriter();
        serialize(model, res);
        return  res.toString();
    }

    /**
     * Deserializes the semantic model from XML
     * @param xmlModel XML representation of a semantic model
     * @return semantic model
     */
    public static SemanticModel deserialize(String xmlModel)
            throws SerializationException {
        return deserialize(new ByteArrayInputStream(xmlModel.getBytes(StandardCharsets.UTF_8)));
    }


    /**
     * Deserializes the semantic model from XML
     * @param in XML input
     * @return semantic model
     */
    public static SemanticModel deserialize(InputStream in)
            throws SerializationException {
        // parsing XML
        Document doc = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(in));
        } catch (ParserConfigurationException e) {
            throw new SerializationException(
                    "ParserConfiguration Error when parsing the semantic model", e);
        } catch (SAXException e) {
            throw new SerializationException(
                    "SAX Error when parsing the semantic model", e);
        } catch (IOException e) {
            throw new SerializationException(
                    "IO Error when parsing the semantic model", e);
        }

        if(doc == null)
            return null;

        return deserialize(doc);
    }

    /**
     * Deserializes the semantic model from XML
     * @param doc XML input
     * @return semantic model
     */
    public static SemanticModel deserialize(Document doc)
            throws SerializationException {
        SemanticModel model;
        Element element = doc.getDocumentElement();
        if(element.getTagName().equals(MODEL_TAG)) {
            model = new SemanticModel(element.getAttribute(MODEL_NAME_ATTR));

            // loading uses
            NodeList uses = element.getElementsByTagName(USE_TAG);
            for (int i = 0; i < uses.getLength() ; i++) {
                Use use = loadUse((Element)uses.item(i));
                model.getUses().add(use);
            }
            // loading predicates
            NodeList predicates = element.getElementsByTagName(PREDICATE_TAG);
            for (int i = 0; i < predicates.getLength() ; i++) {
                Element predicateElement =  (Element)predicates.item(i);
                PredicateDef predicate = loadPredicate(predicateElement, model);
                model.addPredicateDef(predicate);
            }
        } else
            throw new SerializationException(
                    "Error when parsing the semantic model. Root element should be <"
                            + MODEL_TAG + ">");
        return model;
    }

    private static Use loadUse(Element element) {
        Use use = new Use();
        use.setModelName(element.getAttribute(USE_MODEL_NAME_ATTR));
        use.setClassName(element.getAttribute(USE_CLASS_NAME_ATTR));
        DomainSpecificModel domain = new DomainSpecificModel(use.getModelName());
        use.setDomainModel(domain);
        // adding typedefs
        NodeList typeDefs = element.getElementsByTagName(TYPEDEF_TAG);
        for (int i = 0; i < typeDefs.getLength(); i++) {
            Element typedefElement = (Element)typeDefs.item(i);
            domain.getTypedefs().add(new Typedef(typedefElement.getAttribute(TYPEDEF_NAME_TAG)));
        }
        //adding functions
        NodeList funDefs = element.getElementsByTagName(FUN_TAG);
        for (int i = 0; i < funDefs.getLength(); i++) {
            Element funElement = (Element)funDefs.item(i);
            FunctionDef functionDef = loadFunction(funElement, use.getModelName());
            domain.getFunctions().add(functionDef);
        }
        return use;
    }

    private static FunctionDef loadFunction(Element element, String modelName) {
        String functionName = element.getAttribute(FUN_NAME_TAG);
        Element returnElement = (Element)element.getElementsByTagName(RETURN_TAG).item(0);
        String type = returnElement.getAttribute(RETURN_TYPE_ATTR);
        FunctionDef fdef = new FunctionDef(modelName, functionName, ExpressionType.valueOf(type));
        if(returnElement.hasAttribute(RETURN_TYPE_REFERENCE_ATTR))
            fdef.setTypeReference(returnElement.getAttribute(RETURN_TYPE_REFERENCE_ATTR));
        // getting arguments if any
        NodeList args = element.getElementsByTagName(ARG_DEF_TAG);
        for (int i = 0; i < args.getLength(); i++) {
            Element argumentElement = (Element)args.item(i);
            String argumentName = argumentElement.getAttribute(ARG_DEF_NAME_ATTR);
            ArgumentDef argumentDef = new ArgumentDef(modelName, functionName, argumentName);
            argumentDef.setArgumentType(
                    ExpressionType.valueOf(argumentElement.getAttribute(ARG_DEF_TYPE_ATTR)));
            argumentDef.setTypeReference(argumentElement.getAttribute(ARG_DEF_TYPE_REF_ATTR));
            fdef.addArgument(argumentDef);
        }

        return fdef;
    }

    private static PredicateDef loadPredicate(Element element, SemanticModel model)
            throws SerializationException {
        String predicateName = element.getAttribute(PREDICATE_NAME_ATTR);
        // getting arguments if any
        NodeList args = element.getElementsByTagName(ARG_DEF_TAG);
        ArgumentDef arguments[] = new ArgumentDef[args.getLength()];
        for (int i = 0; i < arguments.length; i++) {
            Element argumentElement = (Element)args.item(i);
            String argumentName = argumentElement.getAttribute(ARG_DEF_NAME_ATTR);
            arguments[i] = new ArgumentDef(model.getName(), predicateName, argumentName);
            arguments[i].setArgumentType(
                    ExpressionType.valueOf(argumentElement.getAttribute(ARG_DEF_TYPE_ATTR)));
            arguments[i].setTypeReference(argumentElement.getAttribute(ARG_DEF_TYPE_REF_ATTR));
        }
        // getting formula
        NodeList childes = element.getChildNodes();
        PredicateDef predicate = new PredicateDef(model.getName(), predicateName, null, arguments);
        Formula formula = new LogicalConstant(true);
        for (int i = 0; i < childes.getLength(); i++) {
            if(childes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element)childes.item(i);
                if(!e.getTagName().equals(ARG_DEF_TAG)) {
                    formula = (Formula)loadExpression(e, model, predicate);
                    predicate.setFormula(formula);
                }
            }
        }
        return predicate;
    }

    private static Expression loadExpression(Element element, SemanticModel model,
                                             PredicateDef predicate)
            throws SerializationException {
        Expression res = new LogicalConstant(true);
        switch(element.getTagName()) {
            case VAR_DEF_TAG:
                String name = element.getAttribute(VAR_DEF_NAME_ATTR);
                // getting initializer
                NodeList children = element.getChildNodes();
                VariableDef var = null;
                Expression init = new LogicalConstant(true);
                for (int i = 0; i < children.getLength(); i++) {
                    if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element)children.item(i);
                        init = loadExpression(e, model, predicate);
                        var = new VariableDef(name, init);
                        break;
                    }
                }
                if(var != null) {
                    predicate.addLocalVariable(var);
                }
                break;
            case LOGICAL_CONSTANT_TAG:
                Logical lc = Logical.valueOf(
                        element.getAttribute(LOGICAL_CONSTANT_VALUE_ATTR));
                res = new LogicalConstant(lc);
                break;
            case STRING_TAG:
                if(element.hasAttribute(STRING_VALUE_ATTR)) {
                    res = new StringConstant(element.getAttribute(STRING_VALUE_ATTR));
                } else {
                    StringTerm term = new StringTerm(null);
                    children = element.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            Expression e = loadExpression((Element)children.item(i),
                                    model, predicate);
                            if(term.getOperand(0) == null && e instanceof StringConstant) {
                                term.setStringConstant((StringConstant)e);
                            } else {
                               term.getAdditions().add(e);
                            }
                        }
                    }
                    res = term;
                }
                break;
            case NUMERIC_CONSTANT_TAG:
                String value = element.getAttribute(NUMERIC_CONSTANT_VALUE_ATTR);
                Double d = 0.0;
                try {
                    Double.parseDouble(value);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                res = new NumericConstant(d);
                break;
            case NUMERIC_TERM_TAG:
                NumericTerm term = new NumericTerm();
                term.setOperation(NumericTerm.Operation.valueOf(
                        element.getAttribute(NUMERIC_TERM_OPERATION_ATTR)));
                children = element.getChildNodes();
                //noinspection Duplicates
                for (int i = 0; i < children.getLength(); i++) {
                    if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Expression e = loadExpression((Element)children.item(i),
                                model, predicate);
                        term.addOperand(e);
                    }
                }
                res = term;
                break;
            case REF_TAG:
                name = element.getAttribute(REF_NAME_ATTR);
                switch(element.getAttribute(REF_TYPE_ATTR)) {
                    case VAR_DEF_TAG:
                        res = new VariableRef(name, model.getName());
                        break;
                    case ARG_DEF_TAG:
                        res = new ArgumentRef(model.getName(), predicate.getPredicateName(), name);
                        break;
                    case RANGE_TAG:
                        res = new RangeRef(name);
                        break;
                    case FUNCTION_CALL_ATTR:
                        FunctionCall fc = new FunctionCall();
                        fc.setModelName(element.getAttribute(REF_MODEL_NAME_ATTR));
                        fc.setFunctionName(name);
                        children = element.getChildNodes();
                        //noinspection Duplicates
                        for (int i = 0; i < children.getLength(); i++) {
                            if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                                Expression e = loadExpression((Element) children.item(i),
                                        model, predicate);
                                fc.addOperand(e);
                            }
                        }
                        res = fc;
                        break;
                    case PREDICATE_TAG:
                        PredicateCall pc = new PredicateCall();
                        pc.setModelName(element.getAttribute(REF_MODEL_NAME_ATTR));
                        pc.setPredicateName(name);
                        children = element.getChildNodes();
                        //noinspection Duplicates
                        for (int i = 0; i < children.getLength(); i++) {
                            if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                                Expression e = loadExpression((Element) children.item(i),
                                        model, predicate);
                                pc.addOperand(e);
                            }
                        }
                        res = pc;
                        break;
                    default:
                        log.error("Error: in XML reference type \"" +
                                element.getAttribute(REF_TYPE_ATTR) + '"');
                }
                break;
            case AND_TAG:
                And and = new And();
                children = element.getChildNodes();
                //noinspection Duplicates
                for (int i = 0; i < children.getLength(); i++) {
                    if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Expression e = loadExpression((Element) children.item(i),
                                model, predicate);
                        and.addOperand(e);
                    }
                }
                res = and;
                break;
            case OR_TAG:
                Or or = new Or();
                children = element.getChildNodes();
                //noinspection Duplicates
                for (int i = 0; i < children.getLength(); i++) {
                    if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Expression e = loadExpression((Element) children.item(i),
                                model, predicate);
                        or.addOperand(e);
                    }
                }
                res = or;
                break;
            case NOT_TAG:
                Not not = new Not();
                children = element.getChildNodes();
                //noinspection Duplicates
                for (int i = 0; i < children.getLength(); i++) {
                    if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Expression e = loadExpression((Element) children.item(i),
                                model, predicate);
                        not.setOriginal(e);
                        break;
                    }
                }
                res = not;
                break;
            case PARENTHESES_TAG:
                Parentheses p = new Parentheses();
                children = element.getChildNodes();
                //noinspection Duplicates
                for (int i = 0; i < children.getLength(); i++) {
                    if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Expression e = loadExpression((Element) children.item(i),
                                model, predicate);
                        p.setExpression(e);
                        break;
                    }
                }
                res = p;
                break;
            case FOR_ALL_TAG:
                Expression from = null;
                Expression statement = null;
                children = element.getChildNodes();
                //noinspection Duplicates
                for (int i = 0; i < children.getLength(); i++) {
                    if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element)children.item(i);
                        if(e.getTagName().equals(RANGE_FROM_TAG)) {
                            Element fromStatement =
                                    (Element) e.getElementsByTagName("*").item(0);
                            from = loadExpression(fromStatement, model, predicate);
                        } else if(!e.getTagName().equals(RANGE_TAG)) {
                            statement = loadExpression(e, model, predicate);
                        }
                    }
                    if(from != null && statement != null)
                        break;
                }
                ForAll all = new ForAll(from, statement);
                NodeList ranges = element.getElementsByTagName(RANGE_TAG);
                for (int i = 0; i < ranges.getLength(); i++) {
                    Element e = (Element)ranges.item(i);
                    Range range = new Range(e.getAttribute(RANGE_NAME_ATTR));
                    all.add(range);
                }
                res = all;
                break;
            case IMPLICATION_TAG:
                Implication imp = new Implication();
                children = element.getElementsByTagName("*");
                //noinspection Duplicates
                for (int i = 0; i < children.getLength(); i++) {
                    Element e = (Element)children.item(i);
                    if(e.getTagName().equals(IMPLICATION_THEN_TAG)) {
                        Element thenStatement =
                                (Element) e.getElementsByTagName("*").item(0);
                        Formula thenF = (Formula)loadExpression(thenStatement, model, predicate);
                        imp.setThenFormula(thenF);
                    } else if(e.getTagName().equals(IMPLICATION_ELSE_TAG)) {
                        Element elseStatement =
                                (Element) e.getElementsByTagName("*").item(0);
                        Formula elseF = (Formula)loadExpression(elseStatement, model, predicate);
                        imp.setThenFormula(elseF);
                    } else {
                        imp.setCondition((Formula)loadExpression(e, model, predicate));
                    }
                }
                res = imp;
                break;
            case COMPARISON_TAG:
                try {
                    Comparison cmp = new Comparison();
                    Comparison.Operation op = Comparison.Operation.valueOf(
                            element.getAttribute(COMPARISON_OPERATION_ATTR));
                    cmp.setOperation(op);
                    children = element.getElementsByTagName("*");
                    Expression left = loadExpression((Element)children.item(0), model, predicate);
                    Expression right = loadExpression((Element)children.item(1), model, predicate);
                    cmp.setLeft(left);
                    cmp.setRight(right);
                    res = cmp;
                } catch (IllegalArgumentException ex) {
                    throw new SerializationException("Error: unknown operation attribute " +
                            COMPARISON_OPERATION_ATTR + "=" +
                            element.getAttribute(COMPARISON_OPERATION_ATTR), ex);
                } catch (Exception ex) {
                    throw new SerializationException("Error: when parsing comparison element", ex);
                }
                break;
            default:
                throw new SerializationException("Error: UNKNOWN XML TAG " + element.getTagName());
        }
        return res;
    }

    private static Element serialize(Use use, Document doc) {
        Element useElement = doc.createElement(USE_TAG);
        useElement.setAttribute(USE_CLASS_NAME_ATTR, use.getClassName());
        useElement.setAttribute(USE_MODEL_NAME_ATTR, use.getModelName());
        useElement.appendChild(serialize(use.getDomainModel(), doc));
        return useElement;
    }

    private static Element serialize(DomainSpecificModel model, Document doc) {
        Element domainElement = doc.createElement(DOMAIN_TAG);
        for (Typedef typedef: model.getTypedefs()) {
            domainElement.appendChild(serialize(typedef, doc));
        }
        for (FunctionDef fdef: model.getFunctions()) {
            domainElement.appendChild(serialize(fdef, doc));
        }
        return domainElement;
    }

    private static Element serialize(Typedef typedef, Document doc) {
        Element typedefElement = doc.createElement(TYPEDEF_TAG);
        typedefElement.setAttribute(TYPEDEF_NAME_TAG, typedef.getName());
        return typedefElement;
    }

    private static Element serialize(FunctionDef fdef, Document doc) {
        Element fdefElement = doc.createElement(FUN_TAG);
        fdefElement.setAttribute(FUN_NAME_TAG, fdef.getFunctionName());
        // adding arguments
        for (ArgumentDef argumentDef: fdef.getArgs().values()) {
            fdefElement.appendChild(serialize(argumentDef, doc));
        }
        // adding return type
        Element ret = doc.createElement(RETURN_TAG);
        ret.setAttribute(RETURN_TYPE_ATTR, fdef.getResultType().toString());
        if(fdef.getResultType() == ExpressionType.BASE_OBJECT)
            ret.setAttribute(RETURN_TYPE_REFERENCE_ATTR, fdef.getTypeReference());
        fdefElement.appendChild(ret);
        return fdefElement;
    }

    private static Element serialize(ArgumentDef arg, Document doc) {
        Element argDefElement = doc.createElement(ARG_DEF_TAG);
        argDefElement.setAttribute(ARG_DEF_NAME_ATTR, arg.getArgumentName());
        argDefElement.setAttribute(ARG_DEF_TYPE_ATTR, arg.getArgumentType().toString());
        if(arg.getArgumentType() == ExpressionType.BASE_OBJECT)
            argDefElement.setAttribute(ARG_DEF_TYPE_REF_ATTR, arg.getTypeReference());
        return argDefElement;
    }

    private static Element serialize(PredicateDef def, Document doc) {
        Element defElement = null;
        try {
            // root model element
            defElement = doc.createElement(PREDICATE_TAG);
            defElement.setAttribute(PREDICATE_NAME_ATTR, def.getPredicateName());
            LinkedHashMap<String, ArgumentDef> args = def.getArgs();
            for (ArgumentDef argumentDef: args.values()) {
                defElement.appendChild(serialize(argumentDef, doc));
            }
            for (VariableDef varDef: def.getLocalVariables().values()) {
                Element varDefElement = XMLSerializer.serialize(varDef, doc);
                defElement.appendChild(varDefElement);
            }
            Element formula = XMLSerializer.serialize(def.getFormula(), doc);
            defElement.appendChild(formula);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defElement;
    }

    private static Element serialize(Expression exp, Document doc) {
        Element res = doc.createElement(UNKNOWN_TAG);
        switch (exp.type()) {
            case NUMERIC_TERM:
                NumericTerm op = (NumericTerm) exp;
                res = doc.createElement(NUMERIC_TERM_TAG);
                res.setAttribute(NUMERIC_TERM_OPERATION_ATTR, op.getOperation().toString());
                for (int i = 0; i < op.getDimension(); i++) {
                    Element e = XMLSerializer.serialize(op.getOperand(i), doc);
                    res.appendChild(e);
                }
                break;
            case NUMERIC_CONSTANT:
                res = doc.createElement(NUMERIC_CONSTANT_TAG);
                res.setAttribute(NUMERIC_CONSTANT_VALUE_ATTR, exp.toString());
                break;
            case LOGICAL_AND:
                res = doc.createElement(AND_TAG);
                for (int i = 0; i < exp.getDimension(); i++) {
                    Element e = XMLSerializer.serialize(exp.getOperand(i), doc);
                    res.appendChild(e);
                }
                break;
            case PREDICATE_CALL:
                PredicateCall call = (PredicateCall) exp;
                res = doc.createElement(REF_TAG);
                res.setAttribute(REF_TYPE_ATTR, PREDICATE_TAG);
                res.setAttribute(REF_NAME_ATTR, call.getPredicateName());
                res.setAttribute(REF_MODEL_NAME_ATTR, call.getModelName());
                for (int i = 0; i < call.getDimension(); i++) {
                    Element e = XMLSerializer.serialize(call.getOperand(i), doc);
                    res.appendChild(e);
                }
                break;
            case LOGICAL_COMPARISON:
                Comparison cmp = (Comparison) exp;
                res = doc.createElement(COMPARISON_TAG);
                res.setAttribute(COMPARISON_OPERATION_ATTR, cmp.getOperation().toString());
                for (int i = 0; i < cmp.getDimension(); i++) {
                    Element e = XMLSerializer.serialize(cmp.getOperand(i), doc);
                    res.appendChild(e);
                }
                break;
            case LOGICAL_IMPLICATION:
                Implication impl = (Implication) exp;
                res = doc.createElement(IMPLICATION_TAG);

                Element condition = XMLSerializer.serialize(impl.getCondition(), doc);
                res.appendChild(condition);

                Element trueBranch = doc.createElement(IMPLICATION_THEN_TAG);
                res.appendChild(trueBranch);
                trueBranch.appendChild(XMLSerializer.serialize(impl.getThenFormula(), doc));

                Expression els = impl.getElseFormula();
                if(els != null) {
                    Element elseBranch = doc.createElement(IMPLICATION_ELSE_TAG);
                    res.appendChild(elseBranch);
                    elseBranch.appendChild(XMLSerializer.serialize(els, doc));
                }
                break;
            case LOGICAL_NOT:
                res = doc.createElement(NOT_TAG);
                res.appendChild(XMLSerializer.serialize(exp.getOperand(0), doc));
                break;
            case LOGICAL_OR:
                res = doc.createElement(OR_TAG);
                for (int i = 0; i < exp.getDimension(); i++) {
                    Element e = XMLSerializer.serialize(exp.getOperand(i), doc);
                    res.appendChild(e);
                }
                break;
            case LOGICAL_CONSTANT:
                res = doc.createElement(LOGICAL_CONSTANT_TAG);
                res.setAttribute(LOGICAL_CONSTANT_VALUE_ATTR, exp.toString());
                break;
            case STRING_TERM:
                res = doc.createElement(STRING_TAG);
                for (int i = 0; i < exp.getDimension(); i++) {
                    res.appendChild(XMLSerializer.serialize(exp.getOperand(0), doc));
                }
                break;
            case STRING_CONSTANT:
                res = doc.createElement(STRING_TAG);
                res.setAttribute(STRING_VALUE_ATTR, exp.toString());
                break;
            case FUNCTION_CALL:
                FunctionCall fcall = (FunctionCall) exp;
                res = doc.createElement(REF_TAG);
                res.setAttribute(REF_TYPE_ATTR, FUNCTION_CALL_ATTR);
                res.setAttribute(REF_NAME_ATTR, fcall.getFunctionName());
                res.setAttribute(REF_MODEL_NAME_ATTR, fcall.getModelName());
                for (int i = 0; i < fcall.getDimension(); i++) {
                    Element e = XMLSerializer.serialize(fcall.getOperand(i), doc);
                    res.appendChild(e);
                }
                break;
            case LIST:
                List l = (List)exp;
                res = doc.createElement(LIST_TAG);
                for (SemanticValue v: l.getValue()) {
                    res.appendChild(XMLSerializer.serialize((Expression)v, doc));
                }
                break;
            case FOR_ALL:
                ForAll all = (ForAll)exp;
                res = doc.createElement(FOR_ALL_TAG);

                for (Range r: all.getRanges()) {
                    Element rangeElement = doc.createElement(RANGE_TAG);
                    rangeElement.setAttribute(RANGE_NAME_ATTR, r.getName());
                    res.appendChild(rangeElement);
                }

                Element from = doc.createElement(RANGE_FROM_TAG);
                from.appendChild(XMLSerializer.serialize(all.getList(), doc));
                res.appendChild(from);

                res.appendChild(XMLSerializer.serialize(all.getStatement(), doc));
                break;
            case RANGE_REF:
                RangeRef range = (RangeRef) exp;
                res = doc.createElement(REF_TAG);
                res.setAttribute(REF_TYPE_ATTR, RANGE_TAG);
                res.setAttribute(RANGE_NAME_ATTR, range.getName());
                break;
            case VARIABLE_REF:
                VariableRef variableRef = (VariableRef) exp;
                res = doc.createElement(REF_TAG);
                res.setAttribute(REF_TYPE_ATTR, VAR_DEF_TAG);
                res.setAttribute(REF_NAME_ATTR, variableRef.getName());
                break;
            case PARENTHESES:
                res = doc.createElement(PARENTHESES_TAG);
                res.appendChild(XMLSerializer.serialize(exp.getOperand(0), doc));
                break;
            case ARGUMENT_REF:
                ArgumentRef argRef = (ArgumentRef) exp;
                res = doc.createElement(REF_TAG);
                res.setAttribute(REF_TYPE_ATTR, ARG_DEF_TAG);
                res.setAttribute(REF_NAME_ATTR, argRef.getArgumentName());
                break;
            case LOGGING:
                res = doc.createElement(LOG_TAG);
                res.appendChild(XMLSerializer.serialize(exp.getOperand(0), doc));
                break;
            case BASE_OBJECT:
                BaseObject bobj = (BaseObject)exp;
                res = doc.createElement(OBJECT_TAG);
                res.setAttribute(OBJECT_NAME_ATTR, bobj.getName());
                res.setAttribute(OBJECT_MODEL_NAME, bobj.getModelName());
                for (String key: bobj.keySet()) {
                    Expression e = (Expression)bobj.get(key);
                    Element prop = doc.createElement(PROPERTY_TAG);
                    prop.setAttribute(PROPERTY_NAME_ATTR, key);
                    res.appendChild(prop);
                    prop.appendChild(XMLSerializer.serialize(e, doc));
                }
                break;
        }
        return res;
    }

    private static Element serialize(VariableDef varDef, Document doc) {
        Element varDefElement = doc.createElement(VAR_DEF_TAG);
        varDefElement.setAttribute(VAR_DEF_NAME_ATTR, varDef.getName());
        Element expression = XMLSerializer.serialize(varDef.getExpression(), doc);
        varDefElement.appendChild(expression);
        return varDefElement;
    }
}
