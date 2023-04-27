package com.kafka.demodb.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OtpWebClient {

    WebClient webClient = WebClient.create();



    public void validateOtp(String trxId, String userKey, String otp){


    }
}
