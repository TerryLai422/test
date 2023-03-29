package com.thinkbox.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("")
@Slf4j
public class MyController<myService> {

	@Autowired
	private MyService myService;

	@PostMapping
	private ResponseEntity<Object> process(@RequestBody Map<String, Object> map) {
		log.info("Received map: {}", map.toString());
		String component = (String) map.get("component");
		String function = (String) map.get("function");
		Integer input = (Integer) map.get("input");
		
		log.info("myservice:" +  myService.doSomething(component, function, input));
		map.put("result", "ok");

		return ResponseEntity.ok(map);
	}

}