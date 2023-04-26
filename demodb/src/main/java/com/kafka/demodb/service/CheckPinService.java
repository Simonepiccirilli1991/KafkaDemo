package com.kafka.demodb.service;

import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.internal.SecCounterCrudService;
import com.kafka.demodb.service.internal.UserCrudService;
import com.kafka.demodb.service.internal.UserSecCrudService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
public class CheckPinService {

    private final UserCrudService userCrudService;

    private final UserSecCrudService userSecCrudService;

    private final SecCounterCrudService secCounterCrudService;

    public CheckPinService(UserCrudService userCrudService, UserSecCrudService userSecCrudService, SecCounterCrudService secCounterCrudService) {
        this.userCrudService = userCrudService;
        this.userSecCrudService = userSecCrudService;
        this.secCounterCrudService = secCounterCrudService;
    }


    public BaseDbResponse checkPinUser(UserRequest request){

        var userDto = userCrudService.getUser(request.getEmail(),request.getUsername());

        if(ObjectUtils.isEmpty(userDto.getUser()))
            return  new BaseDbResponse("ERKO-03", "User not found", "User_NotFoubd");

        var counter = secCounterCrudService.getCounter(userDto.getUser().getUserKey());

        if(counter.getPswCounter() >= 3 && counter.getLastPswBlock().plusHours(24).isBefore(LocalDateTime.now()))
            return  new BaseDbResponse("ERKO-04", "Pin blocked, retry in 24h", "Pin_Blocked");


        if(request.getPsw().equals(userDto.getUser().getPsw())){
            secCounterCrudService.resetCounterPsw(userDto.getUser().getUserKey());
            return new BaseDbResponse("OK-00");
        }
        else{
            secCounterCrudService.updateCounterPsw(userDto.getUser().getUserKey());
            return new BaseDbResponse("ERKO-05", "Invalid_Psw","Invalid_Data");
        }
    }
}
