package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.CertifyMailSicRequest;
import com.kafka.orc.model.fragment.request.CheckOtpvRequest;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.CheckOtpvResponse;
import com.kafka.orc.model.fragment.response.GenerateOtpvResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OtpvWebClient {

    @Autowired
    WebClient webClient;


    private final Logger logger = LogManager.getLogger(OtpvWebClient.class);

    public GenerateOtpvResponse generateOtpv(String userKey) {

        logger.debug("Calling generate otp service");

        var resp =  webClient.post()
                .uri("" + "")
                .bodyValue(userKey)
                .retrieve()
                .bodyToMono(GenerateOtpvResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on generate otp with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","GenerateOtpvKO-01");
                });

        return resp.block();
    }

    public CheckOtpvResponse checkOtpv(CheckOtpvRequest request) {

        logger.debug("Calling Check otpv service");

        var resp =  webClient.post()
                .uri("" + "")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CheckOtpvResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on Check otp with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","CheckOtpvKO-01");
                });

        return resp.block();
    }




}
