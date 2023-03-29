package com.thinkbox.test;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    public int doSomething(String component, String function, int input) {
    	MyInterface myInterface = MyInterface.getMyInterface(component);
        MyFunction myFunction = myInterface.getFunction(function);
    	return myFunction.apply(input);
    }
}