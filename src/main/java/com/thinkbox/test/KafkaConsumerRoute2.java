package com.thinkbox.test;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConsumer;
import org.apache.camel.component.kafka.KafkaConsumerListener;
import org.apache.camel.component.kafka.KafkaManualCommit;
import org.apache.camel.component.kafka.KafkaManualCommitListener;
import org.apache.camel.component.kafka.serde.DefaultKafkaHeaderDeserializer;
import org.apache.camel.component.kafka.serde.DefaultKafkaKeyDeserializer;
import org.apache.camel.component.kafka.serde.DefaultKafkaValueDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerRoute2 extends RouteBuilder {

    private String kafkaBootstrapServers = "localhost:9092";
    private String kafkaTopicName = "test-topic";

    @Override
    public void configure() throws Exception {

        // create KafkaConsumerListener instance
        KafkaConsumerListener listener = new KafkaConsumerListener() {

            @Override
            public void onConsume(Object message, String topic, int partition, long offset) {
                // handle successful consumption of a message
            }

            @Override
            public void onError(Object message, String topic, int partition, long offset, Exception exception) {
                // handle an error that occurred while consuming a message
            }

            @Override
            public void onPauseResume(String topic, int partition, boolean pause) {
                // handle pause/resume events
            }
        };

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

                // set consumer listener
                .setHeader(KafkaConstants.CONSUMER_LISTENER, listener)

                // set commit listener
                .setHeader(KafkaConstants.MANUAL_COMMIT_LISTENER, commitListener)

                // use a custom KafkaConsumer object to allow pausing and resuming consumption
                .process(exchange -> {
                    KafkaConsumer consumer = exchange.getContext().getComponent("kafka", KafkaComponent.class).createConsumer(exchange);
                    exchange.setProperty("kafkaConsumer", consumer);
                })
                .pausable(exchange -> {
                    KafkaConsumer consumer = exchange.getProperty("kafkaConsumer", KafkaConsumer.class);
                    return canContinue(consumer);
                })
                .process(exchange -> {
                    // process the message
                    Object message = exchange.getIn().getBody();
                    LOG.info("Got record from Kafka: {}", message);
                });
    }

    private boolean canContinue(KafkaConsumer consumer) {
        // check if the consumer should continue consuming messages
        // return true to continue, false to pause
    }
}
