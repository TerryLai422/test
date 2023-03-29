package com.thinkbox.test;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("mycomponent ")
public class MyComponent implements MyInterface {

	@Autowired
	ApplicationContext context;
	@PostConstruct
	public void init() {
		MyInterface.register("mycomponent", this);
	}

//	public MyFunction getFunction(String function) {
//		if (function != null && function.equalsIgnoreCase("square")) {
//			return (MyFunction)applicationContext.getBean("mycomponent-square");
//		}
//		return null;
//	}

	@Bean("mycomponent-square")
	public MyFunction square() {
		return (x) -> x * x;
	}
}
