package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.UserAccRequest;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.fragment.response.BaseBankResponse;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.StatusBankAccResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BankAccWebClient {

    @Autowired
    WebClient webClient;

    private final Logger logger = LogManager.getLogger(UserWebClient.class);

    public BaseBankResponse registerBankUser(UserAccRequest request) {

        logger.debug("Calling registerBankUser service");

        //TODO: inserire endpoint
        var resp =  webClient.post()
                .uri("" + "")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BaseBankResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on registerBankUser with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserBankRegKO-01");
                });

        return resp.block();
    }

    public StatusBankAccResponse getStatus(String userKey) {

        logger.debug("Calling status Bank Acc service");

        //TODO: inserire endpoint
        var resp =  webClient.post()
                .uri("" + "")
                .bodyValue(userKey)
                .retrieve()
                .bodyToMono(StatusBankAccResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on status Bank Acc with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","StatusBankRegKO-01");
                });

        return resp.block();
    }

    public BaseBankResponse certifyBankAcc(String userKey) {

        logger.debug("Calling status Bank Acc service");

        //TODO: inserire endpoint
        var resp =  webClient.post()
                .uri("" + "")
                .bodyValue(userKey)
                .retrieve()
                .bodyToMono(BaseBankResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on status Bank Acc with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserBankCertKO-02");
                });

        return resp.block();
    }
}
