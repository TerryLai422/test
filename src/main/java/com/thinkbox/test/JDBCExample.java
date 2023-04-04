package com.thinkbox.test;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jdbc.JdbcComponent;
import org.apache.camel.component.reactive.streams.api.CamelReactiveStreams;
import org.apache.camel.component.reactive.streams.api.CamelReactiveStreamsService;
import org.apache.camel.impl.DefaultCamelContext;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JDBCExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCExample.class);

    public static void main(String[] args) throws Exception {
        // Create a new Camel context
        CamelContext context = new DefaultCamelContext();

        // Add the JDBC component to the context
        JdbcComponent jdbc = new JdbcComponent();
        jdbc.setDataSource(new MyDataSource("jdbc:mysql://localhost:3306/terry","root","Guess123tl$"));
        context.addComponent("jdbc", jdbc);

        // Start the context
        context.start();

        // Get the reactive streams service from the context
//        CamelReactiveStreamsService camelService = CamelReactiveStreams.get(context);
//
//        // Create a subscriber that listens to the "my-input" stream
//        Subscriber<Exchange> subscriber = camelService.streamSubscriber("my-input");

        // Create a route that reads from the JDBC database, modifies the data,
        // and writes it back to the database
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("jetty:http://0.0.0.0/8081/?httpMethodRestrict=GET")
                        .process( exchange -> {
                            exchange.getIn().setBody("select * from terry.test");
                        })
                        .to("jdbc:myDataSource")
//                        .process(exchange -> {
//                            List<Map<String, Object>> rows = exchange.getMessage().getBody(List.class);
//                            List<Map<String, Object>> updatedRows = new ArrayList<>();
//                            for (Map<String, Object> row : rows) {
//                                // Modify the row as needed
//                                row.put("column1", "new value");
//                                updatedRows.add(row);
//                            }
//                            exchange.getMessage().setBody(updatedRows);
//                        })
                        .log("${body}")
                        .to("log:test");
            }
        });

        // Create a route that sends data to the "my-input" stream
//        context.addRoutes(new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                from("direct:my-output")
//                        .to("reactive-streams:my-input");
//            }
//        });

        // Send some data to the "my-input" stream
//        context.createProducerTemplate().sendBody("direct:my-output", "select * from terry.test");

        // Wait for the subscriber to finish processing the data
        Thread.sleep(1000000000);

        // Stop the context
        context.stop();
    }
}