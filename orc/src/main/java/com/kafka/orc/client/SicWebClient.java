package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.CertifyMailSicRequest;
import com.kafka.orc.model.fragment.request.ChangePswSicRequest;
import com.kafka.orc.model.fragment.request.RetrivePswRequest;
import com.kafka.orc.model.fragment.request.UserSicRequest;
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

    @Value("${config.sic.url-user-sic}")
    private String baseUrlUserSic; //localhost:8081/api/v1/securety
    @Value("${config.sic.path-ceritfy}")
    private String apiPathCertify; // /cetify
    @Value("${config.sic.path-change}")
    private String apiPathChange; // /change
    @Value("${config.sic.path-retrive}")
    private String apiPathRetrive; // /retrive   -- retrive psw


    private final Logger logger = LogManager.getLogger(SicWebClient.class);

    public BaseDbResponse cerityUserSic(CertifyMailSicRequest request) {

        logger.debug("Calling cetify UserSic service");

        var resp =  webClient.post()
                .uri(baseUrlUserSic + apiPathCertify)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BaseDbResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on cetify User with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserCertKO-01");
                });

        return resp.block();
    }

    public BaseDbResponse changePsw(ChangePswSicRequest request) {

        logger.debug("Calling changePsw UserSic service");

        var resp =  webClient.post()
                .uri(baseUrlUserSic + apiPathChange)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BaseDbResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on changePsw User with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserChangeKO-01");
                });

        return resp.block();
    }

    public BaseDbResponse retrivePsw(RetrivePswRequest request) {

        logger.debug("Calling retrivePsw UserSic service");

        var resp =  webClient.post()
                .uri(baseUrlUserSic + apiPathRetrive)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BaseDbResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on retrivePsw User with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UserRetriveKO-01");
                });

        return resp.block();
    }

}
