package com.thinkbox.test;

import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;

//@RunWith(CamelSpringBootRunner.class)
//@ContextConfiguration(classes = MyCamelRoute.class)
public class MyCamelRouteTest extends CamelSpringTestSupport {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testMyCamelRoute() throws Exception {
        // Define input file path
        String inputFile = "file:src/test/resources/input/test.txt";

        // Define expected output
        String expectedOutput = "Hello, world!";

        // Define mock endpoint
        MockEndpoint mockEndpoint = getMockEndpoint("mock:result");
        mockEndpoint.expectedBodiesReceived(expectedOutput);

        // Write test input to input file
        template.sendBodyAndHeader(inputFile, expectedOutput, Exchange.FILE_NAME, "test.txt");

        // Wait for route to complete
        mockEndpoint.assertIsSatisfied();
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return null;
    }
}
