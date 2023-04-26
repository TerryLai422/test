package com.thinkbox.test;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.KafkaManualCommit;
import org.apache.camel.component.kafka.KafkaManualCommitListener;
import org.apache.camel.component.kafka.KafkaPauseResumeStrategy;
import org.apache.camel.component.kafka.KafkaResumeStrategy;
import org.apache.camel.component.kafka.KafkaUtils;
import org.apache.camel.component.kafka.listeners.KafkaConsumerListener;
import org.apache.camel.component.kafka.serde.DefaultKafkaHeaderDeserializer;
import org.apache.camel.component.kafka.serde.DefaultKafkaKeyDeserializer;
import org.apache.camel.component.kafka.serde.DefaultKafkaValueDeserializer;
import org.apache.camel.model.dataformat.JsonLibrary;

public class KafkaConsumerRoute extends RouteBuilder {

    private String kafkaBootstrapServers = "localhost:9092";
    private String kafkaTopicName = "test-topic";

    @Override
    public void configure() throws Exception {

        // create KafkaPauseResumeStrategy instance
        KafkaPauseResumeStrategy pauseResumeStrategy = new KafkaPauseResumeStrategy();

        // create KafkaConsumerListener instance
        KafkaConsumerListener listener = new KafkaConsumerListener();
        listener.setPauseResumeStrategy(pauseResumeStrategy);

        // create KafkaManualCommitListener instance
        KafkaManualCommitListener commitListener = new KafkaManualCommitListener();
        commitListener.setKafkaManualCommit(new KafkaManualCommit());

        // configure Kafka consumer endpoint
        from("kafka:" + kafkaTopicName + "?brokers=" + kafkaBootstrapServers)
                .routeId("kafka-consumer-route")

                // set deserializers for key and value
                .setHeader(KafkaConstants.KEY_DESERIALIZER_CLASS, DefaultKafkaKeyDeserializer.class)
                .setHeader(KafkaConstants.VALUE_DESERIALIZER_CLASS, DefaultKafkaValueDeserializer.class)

                // set deserializer for headers
                .setHeader(KafkaConstants.HEADER_DESERIALIZER_CLASS, DefaultKafkaHeaderDeserializer.class)

                // set pause and resume strategy
                .setHeader(KafkaConstants.PAUSE_RESUME_STRATEGY, pauseResumeStrategy)

                // set commit listener
                .setHeader(KafkaConstants.MANUAL_COMMIT_LISTENER, commitListener)

                // use JSON data format to deserialize value
                .unmarshal().json(JsonLibrary.Jackson, MyDataObject.class)

                // do something with the data
                .process(exchange -> {
                    MyDataObject data = exchange.getIn().getBody(MyDataObject.class);
                    // process the data
                });
    }
}