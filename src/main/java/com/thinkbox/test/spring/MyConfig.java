package com.thinkbox.test.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean
    public MyFunction plus() {
        return input -> input++;
    }
}