package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.UserRequest;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SicWebClient {

    @Autowired
    WebClient webClient;

    @Value("${config.sic.url-get}")
    private String baseUrlUserSic; //localhost:8081/api/v1/user
    @Value("${config.sic.path-get}")
    private String apiPathRegister; // /register


    private final Logger logger = LogManager.getLogger(SicWebClient.class);

    public BaseDbResponse registerSic(UserRequest request) {

        logger.debug("Calling register UserSic service");

        var resp =  webClient.post()
                .uri(baseUrlUserSic + apiPathRegister)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BaseDbResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on register User with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserRegKO-01");
                });

        return resp.block();
    }

}
