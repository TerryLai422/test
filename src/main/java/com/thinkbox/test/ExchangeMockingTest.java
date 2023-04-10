package com.thinkbox.test;
import org.apache.camel.EndpointInject;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
//import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangeMockingTest extends CamelTestSupport {

    @EndpointInject("mock:myEndpoint")
    private MockEndpoint mockEndpoint;

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("myEndpoint");
            }
        };
    }

    @Test
    public void testExchangeMocking() throws Exception {
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
}
