package org.camel.example;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {
/*
                from("file:src/data?noop=true")
                    .choice()
                        .when(xpath("/person/city = 'London'"))
                            .log("UK message")
                            .to("file:target/messages/uk")
                        .otherwise()
                            .log("Other message")
                            .to("file:target/messages/others");

 */
        from("timer:foo?period=5000")
                .setBody(simple("resource:classpath:postCodeRequest.json"))
                .log("--&gt; Sending: [${body}]")
                .to("direct:input-producer")
                .to("atlasmap:atlasmap-mapping.adm")
                .log("--&lt; Received: [${body}]");

        from("direct:input-producer")
                .setProperty("input-schema", simple("resource:classpath:inputRequest.json"))
                .log("-->; Input: [${exchangeProperty.input-schema}]");



    }

}
