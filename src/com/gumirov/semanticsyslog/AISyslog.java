package com.gumirov.semanticsyslog;

import org.d0sl.machine.SemanticException;
import org.d0sl.machine.SemanticMachine;
import org.d0sl.model.expression.StringConstant;

import java.util.Scanner;

public class AISyslog {
    SemanticMachine semanticMachine;

    public AISyslog(SemanticMachine semanticMachine){
        this.semanticMachine = semanticMachine;
    }

    public boolean start(String callbackPredicate){
        System.out.println("Starting!");
        new Thread(){
            @Override
            public void run() {
                Scanner input = new Scanner(System.in);
                while(input.hasNext()){
                    try {
                        semanticMachine.callPredicate(callbackPredicate, new StringConstant(input.nextLine()));
                    } catch (SemanticException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }.start();
        return true;
    }
}
