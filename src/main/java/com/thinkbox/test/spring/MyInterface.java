package com.thinkbox.test.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public interface MyInterface extends ApplicationContextAware {

	ApplicationContext context = null;

	@Override
	public default void setApplicationContext(ApplicationContext context) {
		context = context;
	}
//    ApplicationContext applicationContext = SpringApplication.run(MyApp.class);
    static Map<String, MyInterface> myInterfaceMap = new HashMap<>();

    static void register(String type, MyInterface myInterface) {
        myInterfaceMap.put(type, myInterface);
    }

    static MyInterface getMyInterface(String type) {
        return myInterfaceMap.getOrDefault(type, null);
    }

    default MyFunction getFunction(String function) {
		if (function != null && function.equalsIgnoreCase("square")) {
			return (MyFunction)context.getBean("mycomponent-square");
		}
		return null;
//		return null;
    }

    default int myMethod(String function, int input) {
        System.out.println("myMethod");
        return 0;
    }
}