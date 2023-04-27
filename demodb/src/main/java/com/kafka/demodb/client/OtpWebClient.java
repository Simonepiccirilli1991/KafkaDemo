package com.kafka.demodb.client;

import com.kafka.demodb.exception.CustomError;
import com.kafka.demodb.exception.GenericError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class OtpWebClient {

    WebClient webClient = WebClient.create();



    public void validateOtp(String trxId, String userKey, String otp){

        // TODO: da testare
        var resp = webClient.post().uri("api/v1/otpv/check")
                .body(Mono.just(new checkotpRequest(trxId, userKey, otp)),checkotpRequest.class)
                .retrieve().toEntity(CheckOtpvSummaryResponse.class);

        try{
            ResponseEntity<CheckOtpvSummaryResponse> response = resp.block();

            //TODO: controllare poi che torna servizio
            if(response.getBody().result.equals("Non me ric"))
                throw new CustomError("Invalid_Otp","InvalidOtp", LocalDateTime.now(), HttpStatus.FORBIDDEN);

        }catch(Exception e){
            throw new GenericError();
        }
    }

    record CheckOtpvSummaryResponse(String result, String responseMsg) {}

    record checkotpRequest(String trxId, String userKey, String otp){}
}
