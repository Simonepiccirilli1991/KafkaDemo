package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.UserSicRequest;
import com.kafka.orc.model.fragment.response.BaseDbResponse;
import com.kafka.orc.model.fragment.response.GetUserByUsernResponse;
import com.kafka.orc.model.fragment.response.StatusSicResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserWebClient {

    @Autowired
    WebClient webClient;

    @Value("${config.sic.url-user}")
    private String baseUrlUserSic; //localhost:8081/api/v1/user
    @Value("${config.sic.path-register}")
    private String apiPathRegister; // /register
    @Value("${config.sic.path-check}")
    private String apiPathCheck; // /check
    @Value("${config.sic.path-status}")
    private String apiPathStatus; // /status
    @Value("${config.sic.path-get-usrnm}")
    private String apiPathGetUsrnm; // /get/{userName}

    private final Logger logger = LogManager.getLogger(UserWebClient.class);

    public BaseDbResponse registerSic(UserSicRequest request) {

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


    public BaseDbResponse checkPinUser(UserSicRequest request) {

        logger.debug("Calling checkPinUser service");

        var resp =  webClient.post()
                .uri(baseUrlUserSic + apiPathCheck)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BaseDbResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on checkPinUser with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserCheckPinKO-01");
                });

        return resp.block();
    }

    public StatusSicResponse statusSic(UserSicRequest request) {

        logger.debug("Calling statusSic service");

        var resp =  webClient.post()
                .uri(baseUrlUserSic + apiPathStatus)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(StatusSicResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on statusSic with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserCheckStatusKO-01");
                });

        return resp.block();
    }

    public GetUserByUsernResponse getUserByUsername(String username){

        logger.debug("Calling statusSic service");

        var resp =  webClient.post()
                .uri(baseUrlUserSic + apiPathGetUsrnm + username)
                .retrieve()
                .bodyToMono(GetUserByUsernResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on statusSic with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserCheckStatusKO-01");
                });

        return resp.block();

    }
}
