package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.GetBalanceAccResponse;
import com.kafka.orc.model.fragment.request.UserAccRequest;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.fragment.response.AmountBankResponse;
import com.kafka.orc.model.fragment.response.BaseBankResponse;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.StatusBankAccResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BankAccWebClient {

    @Autowired
    WebClient webClient;

    @Value("config.client.iwacc")
    private String bankAccEndPoint; // base endpoint

    private final Logger logger = LogManager.getLogger(UserWebClient.class);

    public BaseBankResponse registerBankUser(UserAccRequest request) {

        logger.debug("Calling registerBankUser service");

        //TODO: inserire endpoint
        var resp =  webClient.post()
                .uri(bankAccEndPoint + "register")
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
                .uri(bankAccEndPoint + "status")
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
                .uri(bankAccEndPoint + "certify")
                .bodyValue(userKey)
                .retrieve()
                .bodyToMono(BaseBankResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on status Bank Acc with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserBankCertKO-01");
                });

        return resp.block();
    }

    public GetBalanceAccResponse getBalanceAcc(String userKey) {

        logger.debug("Calling getBalance Bank Acc service");

        //TODO: inserire endpoint
        var resp =  webClient.get()
                .uri(bankAccEndPoint + "getbalance/" + userKey)
                .retrieve()
                .bodyToMono(GetBalanceAccResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on getBalance Bank Acc with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserBankBalanceKO-01");
                });

        return resp.block();
    }


    public AmountBankResponse addORemoveBalance(String userKey, Double amount, Boolean isRemove){

        logger.debug("Calling addORemoveBalance Bank Acc service");

        //TODO: inserire endpoint con pqueery paramether
        var resp =  webClient.get()
                .uri(bankAccEndPoint + "/amount", userKey,amount,isRemove)
                .retrieve()
                .bodyToMono(AmountBankResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on addORemoveBalance Bank Acc with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserBankBalanceKO-01");
                });

        return resp.block();

    }

}
