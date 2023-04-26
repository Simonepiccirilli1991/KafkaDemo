package com.kafka.demodb.service.internal;

import com.kafka.demodb.model.entity.UserSecurity;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.repo.UserSecRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserSecCrudService {

    private final UserSecRepo userSecRepo;

    public UserSecCrudService(UserSecRepo userSecRepo) {
        this.userSecRepo = userSecRepo;
    }

    public void createUserSec(String userKey, String psw){

        UserSecurity userSec = new UserSecurity();
        userSec.setUserKey(userKey);
        userSec.setLastPsw(psw);
        userSec.setEmailCertified(false);
        userSecRepo.save(userSec);
    }

    public UserSecurity getUserSec(String userKey){
        return userSecRepo.findByUserKey(userKey);
    }
    public void updatePswUserSec(String userKey, String psw){

        var userSec = userSecRepo.findByUserKey(userKey);

        if(ObjectUtils.isEmpty(userSec))
            return;

        userSec.setLastPsw(psw);
        userSecRepo.save(userSec);
    }

    public void updateEmailCert(String userKey){

        var userSec = userSecRepo.findByUserKey(userKey);
        if(ObjectUtils.isEmpty(userSec))
            return;

        userSec.setEmailCertified(true);
        userSecRepo.save(userSec);
    }
}
