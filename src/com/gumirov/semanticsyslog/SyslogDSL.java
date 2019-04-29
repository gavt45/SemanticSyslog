package com.gumirov.semanticsyslog;

//import org.d0sl.base.DomainFunction;
import org.d0sl.domain.DomainFunction;
import org.d0sl.domain.DomainModel;
//import org.d0sl.base.SemanticLibrary;
import org.d0sl.machine.SemanticMachine;
import org.d0sl.model.expression.List;
import org.d0sl.model.expression.StringConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DomainModel(name = "SyslogDSL")
public class SyslogDSL {
    private AISyslog aiSyslog;

    public SyslogDSL(SemanticMachine semanticMachine){
        this.aiSyslog=new AISyslog(semanticMachine);
    }

    // some dsl funcs
    @DomainFunction(name = "alert tg")
    public boolean alertTg(String adminId, String msg){
        //todo: alert here
        System.out.println("ALERT: "+msg);
        return true;
    }

    @DomainFunction(name = "contains")
    public boolean contains(String str, String substr){
        return str.contains(substr);
    }

    @DomainFunction(name = "get str from list")
    public String getStrFromList(ArrayList list, double idx){
        try {
            return list.get((int) idx).toString();
        }catch (Exception e){
            return "null";
        }
    }

    @DomainFunction(name = "split get")
    public String splitGet(String str, String sep, double idx){
//        System.out.println("Split get: "+str+" "+sep+" "+idx+": "+ Arrays.toString(str.split(sep)));
        try {
            return str.split(sep)[(int) idx];
        }catch (Exception e){
            return "null";
        }
    }

    @DomainFunction(name = "match regex")
    public boolean matchRegex(String regex, String str){
        return str.matches(regex);
    }

    @DomainFunction(name = "find regex")
    public List findRegex(String regex, String str, double group){
//        System.out.println("Find regex: "+regex+" in \""+str+"\" g:"+(int)group);
        Matcher matcher = Pattern.compile(regex).matcher(str);
        List matches = new List();
        while (matcher.find()){
            matches.getValue().add(new StringConstant(matcher.group((int)group)));
        }
//        System.out.println("Results: "+matches.getValue());
        return matches;
    }

    @DomainFunction(name = "start")
    public boolean start(String callbackPredicateName){
        return aiSyslog.start(callbackPredicateName);
    }
}
