package com.thinkbox.test;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class MyRouteTest1 extends CamelSpringTestSupport {

    @EndpointInject(uri = "mock:myEndpoint")
    private MockEndpoint mockEndpoint;

    @Test
    public void testMyRoute() throws Exception {
        // Set up expectations
        mockEndpoint.expectedBodiesReceived("Test message");

        // Send a message to the route
        template.sendBody("direct:start", "Test message");

        // Wait for expectations to be satisfied
        mockEndpoint.assertIsSatisfied();

        // Verify that the mock endpoint received the message
        assertEquals(1, mockEndpoint.getExchanges().size());
        assertEquals("Test message", mockEndpoint.getExchanges().get(0).getIn().getBody());
    }

    @Override
    protected AnnotationConfigApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(MyConfiguration.class);
    }

    @Configuration
    public static class MyConfiguration {

        @Bean
        public MyService myService() {
            return new MyService();
        }

        @Bean
        public MyRouteBuilder myRouteBuilder() {
            return new MyRouteBuilder();
        }
    }
}
