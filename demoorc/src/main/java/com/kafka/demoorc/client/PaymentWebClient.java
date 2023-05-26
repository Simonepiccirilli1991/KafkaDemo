package com.kafka.demoorc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PaymentWebClient {

    @Autowired
    WebClient webClient;
}
