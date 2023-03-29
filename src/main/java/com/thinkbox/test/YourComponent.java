package com.thinkbox.test;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component("YourComponent")
public class YourComponent implements MyInterface {

	@PostConstruct
	public void init() {
		MyInterface.register("yourcomponent", this);
	}
}
