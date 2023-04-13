package com.thinkbox.test;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyRouteTest extends CamelTestSupport {

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testMyRoute() throws Exception {
        RouteDefinition definition = new RouteDefinition();
        definition.setId("myRoute");
        // Arrange
        context.adviceWith(definition, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                // Replace the actual endpoint with a mock endpoint
                replaceFromWith("direct:input");
                interceptSendToEndpoint("mock:output").skipSendToOriginalEndpoint().to("log:output");
            }
        });

        // Get the mock endpoint
        MockEndpoint mockEndpoint = getMockEndpoint("mock:output");

        // Expect a single message to be received by the mock endpoint
        mockEndpoint.expectedMessageCount(1);

        // Set the expected message body
        String expectedBody = "Hello, World!";
        mockEndpoint.expectedBodiesReceived(expectedBody);

        // Act
        template.sendBody("direct:input", expectedBody);

        // Assert
        assertMockEndpointsSatisfied();

        // Get the received message
        Exchange receivedExchange = mockEndpoint.getReceivedExchanges().get(0);

        // Check the received message body
        String receivedBody = receivedExchange.getIn().getBody(String.class);
        assertEquals(expectedBody, receivedBody);
    }
}
