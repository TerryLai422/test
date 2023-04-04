package com.thinkbox.test.spring;

import javax.annotation.PostConstruct;

import com.thinkbox.test.spring.MyInterface;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

//@Component("YourComponent")
public class YourComponent implements MyInterface {

	@PostConstruct
	public void init() {
		MyInterface.register("yourcomponent", this);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}
}
