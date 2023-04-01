package com.thinkbox.test.spring;

import javax.annotation.PostConstruct;

import com.thinkbox.test.spring.MyInterface;
import org.springframework.stereotype.Component;

@Component("YourComponent")
public class YourComponent implements MyInterface {

	@PostConstruct
	public void init() {
		MyInterface.register("yourcomponent", this);
	}
}
