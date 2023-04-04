package com.thinkbox.test;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JdbcRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jetty:http://0.0.0.0/8082/?httpMethodRestrict=GET")
                .process( exchange -> {
                    exchange.getIn().setBody("select * from terry.test");
                })
                .log("${headers}")
                .log("${body}")
                .to("jdbc:myDataSource")
                .log("${headers}")
                .log("${body}")
                ;
    }
}
