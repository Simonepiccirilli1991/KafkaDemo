package com.kafka.orc.client;

import com.kafka.orc.error.OrcError;
import com.kafka.orc.model.fragment.request.SessionRequest;
import com.kafka.orc.model.fragment.response.CreateSessionResponse;
import com.kafka.orc.model.fragment.response.GetSessionResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CacheWebClient {

    @Autowired
    WebClient webClient;

    @Value("config.client.cache")
    private String cacheEndPoint; // base endpoint

    private final Logger logger = LogManager.getLogger(CacheWebClient.class);

    public CreateSessionResponse createSession(SessionRequest request) {

        logger.debug("Calling create session service");

        var resp =  webClient.post()
                .uri(cacheEndPoint + "create")
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

    public Boolean checkValidSession(String sessionId){

        var resp =  webClient.post()
                .uri(cacheEndPoint + "check/session")
                .header("sessionId",sessionId)
                .retrieve().bodyToMono(Boolean.class)
                .onErrorMap(e -> {
                    logger.error("Error on check session with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","CheckSessKO-01");
                });

        var response =   resp.block();

        return response;
    }

    public SessionUpdate updateSession(String sessionId){

        var resp =  webClient.post()
                .uri(cacheEndPoint + "update")
                .header("sessionId",sessionId)
                .retrieve().bodyToMono(SessionUpdate.class)
                .onErrorMap(e -> {
                    logger.error("Error on update session with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","UpdateSessKO-01");
                });

        var response =   resp.block();

        return response;
    }

    public GetSessionResponse getSession(String sessionId){

        var resp =  webClient.get()
                .uri(cacheEndPoint + "getsession/" + sessionId)
                .retrieve().bodyToMono(GetSessionResponse.class)
                .onErrorMap(e -> {
                    logger.error("Error on get session with error: ", e.getMessage());
                    throw new OrcError("Generic Error", "PI_MS_5000: Generic error","GetSessKO-01");
                });

        var response =   resp.block();

        return response;
    }


    public record SessionUpdate(String sessionId,Boolean updated){}
}
