package com.thinkbox.test;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.debezium.DebeziumConstants;
import org.apache.camel.component.debezium.DebeziumEndpoint;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.HashMap;
import java.util.Map;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // configure Kafka component
        KafkaComponent kafka = new KafkaComponent();
        kafka.setBrokers("localhost:9092");
        getContext().addComponent("kafka", kafka);

        // configure Debezium endpoint
        DebeziumEndpoint debeziumEndpoint = getContext().getEndpoint("debezium-mysql:localhost:3306", DebeziumEndpoint.class);
        debeziumEndpoint.setDatabaseName("mydb");
        debeziumEndpoint.setOffsetStorage("org.apache.kafka.connect.storage.MemoryOffsetBackingStore");
        debeziumEndpoint.setAdditionalProperties(getDebeziumProperties());

        from(debeziumEndpoint)
                .routeId("my-route")
                .setHeader(KafkaConstants.KEY, simple("${body.id}"))
                .to("kafka:mytopic")
                .to("log:mytopic");

        // configure Kafka consumer
        from("kafka:mytopic")
                .routeId("my-consumer")
                .unmarshal().json(JsonLibrary.Jackson, MyClass.class)
                .process(exchange -> {
                    MyClass myClass = exchange.getIn().getBody(MyClass.class);
                    System.out.println("Received message: " + myClass);
                })
                .to(debeziumEndpoint)
                .to("log:mytable");

    }

    private Map<String, Object> getDebeziumProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(DebeziumConstants.CONTINUE_ON_ERROR, true);
        properties.put(DebeziumConstants.SNAPSHOT_MODE, "always");
        return properties;
    }
}
