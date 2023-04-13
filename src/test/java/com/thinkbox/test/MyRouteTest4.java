package com.thinkbox.test;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
//import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@CamelSpringBootTest
public class MyRouteTest4 {

//    @Autowired
//    private MyRouteBuilder routeBuilder;
//
//    @EndpointInject(uri = "mock:myEndpoint")
//    private MockEndpoint mockEndpoint;
//
//    @Test
//    public void testMyRoute() throws Exception {
//        // Set up expectations
//        mockEndpoint.expectedBodiesReceived("Test message");
//
//        // Send a message to the route
//        routeBuilder.getContext().createProducerTemplate().sendBody("direct:start", "Test message");
//
//        // Wait for expectations to be satisfied
//        mockEndpoint.assertIsSatisfied();
//
//        // Verify that the mock endpoint received the message
//        assertEquals(1, mockEndpoint.getExchanges().size());
//        assertEquals("Test message", mockEndpoint.getExchanges().get(0).getIn().getBody());
//    }

}
