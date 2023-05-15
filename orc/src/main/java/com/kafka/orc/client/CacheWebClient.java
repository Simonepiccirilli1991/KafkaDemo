package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.SessionRequest;
import com.kafka.orc.model.fragment.response.CreateSessionResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CacheWebClient {

    @Autowired
    WebClient webClient;

    private final Logger logger = LogManager.getLogger(CacheWebClient.class);

    public CreateSessionResponse createSession(SessionRequest request) {

        logger.debug("Calling create session service");

        var resp =  webClient.post()
                .uri("" + "")
                .bodyValue(request)
                .retrieve().toEntity(ResponseEntity.class)
                .onErrorMap(e -> {
                    logger.error("Error on create session with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","CreateSessKO-01");
                });

      var response =   resp.block();

        var header = response.getHeaders().getFirst("sessionId");
        Boolean result = response.getBody().hasBody();

        return new CreateSessionResponse(header,result);

    }
}
