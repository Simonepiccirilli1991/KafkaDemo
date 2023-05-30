package com.kafka.demodb.client;

import com.kafka.demodb.exception.GenericError;
import com.kafka.demodb.model.response.CheckOtpvSummaryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class OtpWebClient {

    WebClient webClient = WebClient.create();

    @Value("config.endpoint.otpv")
    private String otpEndotpoint;

    //TODO: una volta testato provare ad aggiungere servizio cryptato di stringa , decrypti e mappi su object!

    public Boolean validateOtp(String trxId, String userKey, String otp){

        // TODO: NOTA BENE, se servizio torna classe, non puoi mappare su record, altrimenti il servizio esplode
        var resp = webClient.post().uri(otpEndotpoint + "check")
                .body(Mono.just(new checkotpRequest(trxId, userKey, otp)),checkotpRequest.class)
                .retrieve().toEntity(CheckOtpvSummaryResponse.class);

        try{
            ResponseEntity<CheckOtpvSummaryResponse> response = resp.block();
            if(response.getBody().getResult().equals("Otp validate"))
                return true;
            else
                return false;

        }catch(Exception e){
            throw new GenericError();
        }
    }

    //record CheckOtpvSummaryResponse(String result, String responseMsg) {}
    //puoi passare record come request, ma non come response.
    //TODO: da testare nel caso anche il servizio invocato rispondesse con un record
    record checkotpRequest(String trxId, String userKey, String otp){}
}
