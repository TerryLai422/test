package com.thinkbox.test;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.reactive.streams.ReactiveStreamsCamelSubscriber;
//import org.apache.camel.component.reactive.streams.api.ExchangeConverter;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.reactive.streams.ReactiveStreamsComponent;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CountDownLatch;

public class ReactiveStreamsExample3 {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
//        context.addComponent("reactive-streams", new ReactiveStreamsComponent(context));
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("timer:tick?period=1000")
                        .setBody().constant("Hello, world!")
                        .to("reactive-streams:my-input");

                from("reactive-streams:my-output")
                        .process(exchange -> {
                            // Do some processing on the message
                            exchange.getMessage().setBody("Processed message: " + exchange.getMessage().getBody());
                        })
                        .to("log:output");
            }
        });
        context.start();

//        Subscriber<String> subscriber = new Subscriber<>() {
//            private Subscription subscription;
//            private int messageCount;
//
//            public void onSubscribe(Subscription subscription) {
//                this.subscription = subscription;
//                subscription.request(1);
//            }
//
//            public void onNext(String message) {
//                System.out.println("Received message: " + message);
//                messageCount++;
//                if (messageCount >= 10) {
//                    subscription.cancel();
//                    System.out.println("All messages received");
//                    context.stop();
//                } else {
//                    subscription.request(1);
//                }
//            }
//
//            public void onError(Throwable throwable) {
//                System.out.println("Error occurred: " + throwable.getMessage());
//            }
//
//            public void onComplete() {
//                System.out.println("Subscriber completed");
//            }
//        };

        ReactiveStreamsCamelSubscriber camelSubscriber = new ReactiveStreamsCamelSubscriber("test");
        ReactiveStreamsComponent reactiveStreams = context.getComponent("reactive-streams", ReactiveStreamsComponent.class);
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        CountDownLatch latch = new CountDownLatch(1);
//        ExchangeConverter<String> exchangeConverter = new ExchangeConverter<String>() {
//            public String fromExchange(org.apache.camel.Exchange exchange) {
//                return exchange.getIn().getBody(String.class);
//            }
//        };
//        reactiveStreams.fromStream("my-output", String.class)
//                .subscribe(camelSubscriber);

        latch.await();
    }

}
