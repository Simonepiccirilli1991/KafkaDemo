package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.request.PaymentRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PaymentOrcWebClient {

    @Autowired
    WebClient webClient;

    private final Logger logger = LogManager.getLogger(PaymentOrcWebClient.class);

    //TODO: finire in maniera corretta una volta implementato il client del saga Orchestrator
    public Boolean payment(PaymentRequest request){

        var resp = webClient.post().uri("")
                .bodyValue(request)
                .retrieve().bodyToMono(Boolean.class)
                .onErrorMap(e -> {
                    logger.error("Error on payment with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","PaymentKO-01");
                });

        var response = resp.block();

        return response;
    }

}
