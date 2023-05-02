package com.kafka.demodb.service;

import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.model.response.GetUserResponse;
import com.kafka.demodb.service.internal.UserCrudService;
import org.springframework.stereotype.Service;

@Service
public class GetUserService {

    private final UserCrudService userCrudService;

    public GetUserService(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    //TODO: usare poi i record nell'orchestratore, qui sono messi funzionanti ma piu come esempio, funzionano con gestione eccezzioni
    public UserSummary getUserFilter(UserRequest request){

        var tempResp = userCrudService.getUser(request.getEmail(), request.getUserKey());

        if(tempResp.getResult().equals("OK-00"))
            return new UserSummary(tempResp.getUser().getEmail(),tempResp.getUser().getUsername(), tempResp.getUser().getUserKey());

        else
            return new UserSummary("","","");
    }

    public GetUserResponse getUser(String email, String userKey) {

        var tempResp = userCrudService.getUser(email, userKey);

        return tempResp;
    }

    public UserSummary getUserFilterUsername(String username){

        var tempResp = userCrudService.getUserByUsername(username);

        if(tempResp.getResult().equals("OK-00"))
            return new UserSummary(tempResp.getUser().getEmail(),tempResp.getUser().getUsername(), tempResp.getUser().getUserKey());

        else
            return new UserSummary("","","");
    }

    public record UserSummary(String email, String username, String userKey){}

}
